package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.comment.client.CommentClient;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.LikePostMapper;
import com.xueyu.post.mapper.PostGeneralMapper;
import com.xueyu.post.mapper.PostMapper;
import com.xueyu.post.mapper.PostViewMapper;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.*;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.PostService;
import com.xueyu.resource.client.ResourceClient;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

	@Resource
	ImageAnnexService imageAnnexService;

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
	CommentClient commentClient;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean publishPost(Post post, MultipartFile[] files) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		post.setCreateTime(now);
		// 存入帖子数据，获得主键值
		query().getBaseMapper().insert(post);
		// 添加数据统计表行数据
		PostGeneral postGeneral = new PostGeneral();
		postGeneral.setPostId(post.getId());
		postGeneralMapper.insert(postGeneral);
		if (files != null) {
			List<ImageAnnex> images = new ArrayList<>();
			for (MultipartFile file : files) {
				// 将存入的图片名称存入集合
				ImageAnnex imageAnnex = new ImageAnnex();
				imageAnnex.setFileName(resourceClient.uploadImageFile(file).getData().get("fileName"));
				imageAnnex.setParentId(post.getId());
				images.add(imageAnnex);
			}
			// 将文件名存入帖子服务的图片附件表
			imageAnnexService.saveBatch(images);
		}
		return true;
	}

	@Override
	public Boolean deletePost(Integer postId, Integer userId) {
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
		// 删除帖子
		int delete = query().getBaseMapper().delete(wrapper);
		if (delete != 1) {
			throw new PostException("帖子删除失败");
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
		IPage<PostView> page = new Page<>(current, size);
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		// userId不为空则查找用户帖子列表
		if (userId != null) {
			wrapper.eq(PostView::getUserId, userId);
		}
		postViewMapper.selectPage(page, wrapper);
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(page, result);
		List<PostView> records = page.getRecords();
		// 统计postId, userId
		List<Integer> postIds = new ArrayList<>();
		List<Integer> authors = new ArrayList<>();
		// 创建map postId | 点赞用户id列表数据，进行批量查询出用户id数据
		Map<String, List<Integer>> likeUserIdsMap = new HashMap<>(records.size());
		for (PostView postView : records) {
			postIds.add(postView.getId());
			authors.add(postView.getUserId());
		}
		// 查询所有帖子的点赞信息，并按照帖子id进行分配
		LambdaQueryWrapper<LikePost> likeWrapper = new LambdaQueryWrapper<>();
		likeWrapper.in(LikePost::getPostId, postIds);
		List<LikePost> likePosts = likePostMapper.selectList(likeWrapper);
		for (LikePost likePost : likePosts) {
			if (likeUserIdsMap.containsKey(likePost.getPostId().toString())) {
				likeUserIdsMap.get(likePost.getPostId().toString()).add(likePost.getUserId());
			} else {
				List<Integer> userIds = new ArrayList<>();
				userIds.add(likePost.getUserId());
				likeUserIdsMap.put(likePost.getPostId().toString(), userIds);
			}
		}
		// 查询并设置帖子用户信息
		Map<Integer, UserSimpleVO> userInfos = userClient.getUserDeatilInfoMap(authors).getData();
		// 查询所有帖子的点赞用户信息
		Map<Integer, List<UserSimpleVO>> userLikeInfo = userClient.getUserInfoByGroup(likeUserIdsMap).getData();
		// 查询所有图片信息
		Map<Integer, List<ImageAnnexView>> postListImgs = imageAnnexService.getPostListImgs(postIds);
		List<PostListVO> postData = new ArrayList<>();
		for (PostView record : records) {
			PostListVO postListVO = new PostListVO();
			BeanUtils.copyProperties(record, postListVO);
			// 设置帖子用户信息
			postListVO.setUserInfo(userInfos.get(record.getUserId()));
			// 设置该帖子图片信息
			postListVO.setImgList(postListImgs.get(record.getId()));
			// 设置点赞用户信息
			postListVO.setUserLikeBOList(userLikeInfo.get(record.getId()));
			postData.add(postListVO);
		}
		result.setRecords(postData);
		return result;
	}

	@Override
	public PostDetailVO getPostDetailInfo(Integer postId, Integer userId) {
		// 查询数据，创建数据响应体
		PostView postView = postViewMapper.selectById(postId);
		// 核对该帖子是否已通过审核，如果未通过审核且不为自己的帖子，拒绝访问
		if (!postView.getStatus().equals(PostStatus.PUBLIC.getValue())) {
			if (userId == null || !userId.equals(postView.getUserId())) {
				throw new PostException("未审核的帖子信息");
			}
		}
		PostDetailVO postDetailVO = new PostDetailVO();
		// 拷贝相同属性项
		BeanUtils.copyProperties(postView, postDetailVO);
		// 查询并设置是否点赞
		if (userId != null) {
			LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
			LikePost likePost = likePostMapper.selectOne(wrapper);
			if (likePost != null) {
				postDetailVO.setIsLike(true);
			} else {
				postDetailVO.setIsLike(false);
			}
		} else {
			postDetailVO.setIsLike(false);
		}
		// 查询并设置作者信息
		postDetailVO.setUserInfo(userClient.getUserInfo(postView.getUserId()).getData());
		// 查询评论信息
		postDetailVO.setCommentList(commentClient.getPostCommentList(postId).getData());
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
		lambdaUpdate().update(post);
	}

}
