package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.comment.client.CommentClient;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.*;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.*;
import com.xueyu.post.pojo.enums.PostStatus;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.sdk.dto.PostDTO;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.PostService;
import com.xueyu.post.service.VoteService;
import com.xueyu.resource.client.ResourceClient;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xueyu.post.sdk.constant.PostMqContants.*;

/**
 * @author durance
 */
@Slf4j
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

	@Resource
	ImageAnnexService imageAnnexService;

	@Resource
	VoteService voteService;

	@Resource
	VoteMapper voteMapper;

	@Resource
	PostGeneralMapper postGeneralMapper;

	@Resource
	ResourceClient resourceClient;

	@Resource
	UserClient userClient;

	@Resource
	LikePostMapper likePostMapper;

	@Resource
	RabbitTemplate rabbitTemplate;

	@Resource
	PostViewMapper postViewMapper;

	@Resource
	PostMapper postMapper;

	@Resource
	CommentClient commentClient;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean publishPost(Post post, MultipartFile[] files, Vote vote, String[] options) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		post.setCreateTime(now);
		//html标签转码
		post.setContent(HtmlUtils.htmlEscapeHex(post.getContent()));
		// 存入帖子数据，获得主键值
		query().getBaseMapper().insert(post);
		log.info("用户 id -> {}, 上传了帖子到审核列表", post.getUserId());
		// 添加数据统计表行数据
		PostGeneral postGeneral = new PostGeneral();
		postGeneral.setPostId(post.getId());
		postGeneralMapper.insert(postGeneral);
		if (files != null) {
			List<ImageAnnex> images = new ArrayList<>();
			for (MultipartFile file : files) {
				// 将存入的图片名称存入集合
				ImageAnnex imageAnnex = new ImageAnnex();
				// todo 一次性上传所有图片，减少服务调用次数
				imageAnnex.setFileName(resourceClient.uploadImageFile(file).getData().get("fileName"));
				imageAnnex.setParentId(post.getId());
				images.add(imageAnnex);
			}
			// 将文件名存入帖子服务的图片附件表
			imageAnnexService.saveBatch(images);
		}
		//添加投票
		if(vote.getType()!=null & vote.getCycle()!=null & vote.getTopic()!=null){
			vote.setPostId(post.getId());
			voteService.launchVote(vote,options);
		}
		// 发送mq消息
		PostOperateDTO postOperateDTO = new PostOperateDTO();
		postOperateDTO.setUserId(post.getUserId());
		postOperateDTO.setPostId(post.getId());
		postOperateDTO.setAuthorId(post.getUserId());
		rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_INSERT_KEY, postOperateDTO);
		return true;
	}

	@Override
	public Boolean deletePost(Integer postId, Integer userId) {
		log.info("用户 id -> {}, 删除 帖子 id -> {}", userId, postId);
		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Post::getId, postId).eq(Post::getUserId, userId);
		Post post = query().getBaseMapper().selectOne(wrapper);
		// 如果未查出数据说明帖子为另外的用户或不存在
		if (post == null) {
			throw new PostException("用户id与帖子id不匹配");
		}
		// 删除帖子图片
		LambdaQueryWrapper<ImageAnnex> imgWrapper = new LambdaQueryWrapper<>();
		imgWrapper.eq(ImageAnnex::getParentId, postId);
		List<ImageAnnex> imgList = imageAnnexService.list(imgWrapper);
		// 如果帖子有图片则进行图片删除
		if (imgList.size() != 0) {
			String[] fileList = new String[imgList.size()];
			for (int i = 0; i < fileList.length; i++) {
				fileList[i] = imgList.get(i).getFileName();
			}
			resourceClient.deleteFilesListByFileName(fileList);
		}
		// 删除投票
		LambdaQueryWrapper<Vote> voteQueryWrapper = new LambdaQueryWrapper<>();
		voteQueryWrapper.eq(Vote::getPostId,postId);
		Vote vote = voteMapper.selectOne(voteQueryWrapper);
		if(vote!=null){
			voteService.deleteVote(vote.getVoteId());
		}
		// 删除帖子
		int delete = query().getBaseMapper().delete(wrapper);
		if (delete != 1) {
			throw new PostException("帖子删除异常");
		}
		// 发送mq消息
		PostOperateDTO postOperateDTO = new PostOperateDTO();
		postOperateDTO.setUserId(userId);
		postOperateDTO.setPostId(postId);
		postOperateDTO.setAuthorId(post.getUserId());
		rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_DELETE_KEY, postOperateDTO);
		return true;
	}

	@Override
	public ListVO<PostListVO> getPostListByPage(Integer current, Integer size, Integer userId) {
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		// userId不为空则查找用户帖子列表
		if (userId != null) {
			wrapper.eq(PostView::getUserId, userId);
		}
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, wrapper);
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(page, result);
		List<PostView> records = page.getRecords();
		result.setRecords(queryByList(records, userId));
		return result;
	}

	public List<PostListVO> queryByList(List<PostView> records, Integer userId){
		// 统计postId, userId
		List<Integer> postIds = new ArrayList<>();
		List<Integer> authors = new ArrayList<>();
		// 创建map postId | 点赞用户id列表数据，进行批量查询出用户id数据
		Map<Integer, List<Integer>> likeUserIdsMap = new HashMap<>(records.size());
		for (PostView postView : records) {
			postIds.add(postView.getId());
			authors.add(postView.getUserId());
		}
		// 查询所有帖子的点赞信息，并按照帖子id进行分配
		LambdaQueryWrapper<LikePost> likeWrapper = new LambdaQueryWrapper<>();
		likeWrapper.in(LikePost::getPostId, postIds);
		List<LikePost> likePosts = likePostMapper.selectList(likeWrapper);
		for (LikePost likePost : likePosts) {
			if (likeUserIdsMap.containsKey(likePost.getPostId())) {
				likeUserIdsMap.get(likePost.getPostId()).add(likePost.getUserId());
			} else {
				List<Integer> userIds = new ArrayList<>();
				userIds.add(likePost.getUserId());
				likeUserIdsMap.put(likePost.getPostId(), userIds);
			}
		}
		// 查询并设置帖子用户信息
		Map<Integer, UserSimpleVO> userInfos = userClient.getUserDeatilInfoMap(authors).getData();
		// 查询所有图片信息
		Map<Integer, List<ImageAnnexView>> postListImgs = imageAnnexService.getPostListImgs(postIds);
		List<PostListVO> postData = new ArrayList<>();
		// todo 一次查询所有帖子的点赞用户信息，在循环中赋值
		for (PostView record : records) {
			PostListVO postListVO = new PostListVO();
			BeanUtils.copyProperties(record, postListVO);
			// 设置帖子用户信息
			postListVO.setUserInfo(userInfos.get(record.getUserId()));
			// 设置该帖子图片信息
			postListVO.setImgList(postListImgs.get(record.getId()));
			// 设置点赞用户信息
			if (likeUserIdsMap.get(record.getId()) != null) {
				List<UserSimpleVO> userLikeInfos = userClient.getUserDeatilInfoList(likeUserIdsMap.get(record.getId())).getData();
				postListVO.setUserLikeBOList(userLikeInfos);
			}
			//html转码
			postListVO.setContent(HtmlUtils.htmlUnescape(record.getContent()));
			// 设置投票信息
			VoteVO voteVO;
			if(userId!=null){
				voteVO = voteService.getVoteDetail(record.getId(), userId);
			}else {
				voteVO = voteService.getVoteDetail(record.getId(), null);
			}
			postListVO.setVoteMessage(voteVO);
			postData.add(postListVO);
		}
		return postData;
	}

	@Override
	public ListVO<PostListVO> getStatusPostListByPage(Integer current, Integer size, Integer userId) {
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PostView::getStatus, PostStatus.EXAMINE.getValue());
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, wrapper);
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(page, result);
		List<PostView> records = page.getRecords();
		result.setRecords(queryByList(records, userId));
		return result;
	}


	@Override
	public PostDetailVO getPostDetailInfo(Integer postId, Integer userId) {
		// 查询数据，创建数据响应体
		PostView postView = postViewMapper.selectById(postId);
		if (postView == null) {
			throw new PostException("不存在该帖子");
		}
		// 核对该帖子是否已通过审核，如果未通过审核且不为自己的帖子，拒绝访问
		if (!postView.getStatus().equals(PostStatus.PUBLIC.getValue())) {
			if (userId == null || !userId.equals(postView.getUserId())) {
				throw new PostException("未审核的帖子信息");
			}
		}
		PostDetailVO postDetailVO = new PostDetailVO();
		// 拷贝相同属性项
		BeanUtils.copyProperties(postView, postDetailVO);
		// html标签转码
		postDetailVO.setContent(HtmlUtils.htmlUnescape(postView.getContent()));
		// 查询并设置是否点赞
		if (userId != null) {
			LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
			LikePost likePost = likePostMapper.selectOne(wrapper);
			postDetailVO.setIsLike(likePost != null);
		} else {
			postDetailVO.setIsLike(false);
		}
		// 查询并设置作者信息
		postDetailVO.setUserInfo(userClient.getUserInfo(postView.getUserId()).getData());
		// 查询评论信息
		postDetailVO.setCommentList(commentClient.getPostCommentList(postId).getData());
		// 设置投票信息
		VoteVO voteVO;
		if(userId!=null){
			voteVO = voteService.getVoteDetail(postId, userId);
		}else {
			voteVO = voteService.getVoteDetail(postId, null);
		}
		postDetailVO.setVoteMessage(voteVO);
		// 发送mq信息
		PostOperateDTO postOperateDTO = new PostOperateDTO();
		postOperateDTO.setUserId(userId);
		postOperateDTO.setPostId(postId);
		postOperateDTO.setAuthorId(postView.getUserId());
		rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_OPERATE_VIEW_KEY, postOperateDTO);
		return postDetailVO;
	}

	@Override
	public void passPostContent(Integer postId, Integer desicion) {
		if (!(desicion.equals(PostStatus.PUBLIC.getValue()) || desicion.equals(PostStatus.FAIL.getValue()))) {
			throw new PostException("不合法的审核参数");
		}
		// 参数合法修改帖子状态 todo 如果审核失败，返回失败原因
		Post post = new Post();
		post.setId(postId);
		post.setStatus(desicion);
		postMapper.updateById(post);
	}

	@Override
	public ListVO<PostListVO> getAllPostListByPage(Integer current, Integer size, Integer userId) {
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PostView::getStatus, PostStatus.PUBLIC.getValue());
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, wrapper);
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(page, result);
		List<PostView> records = page.getRecords();
		result.setRecords(queryByList(records, userId));
		return result;
	}

}
