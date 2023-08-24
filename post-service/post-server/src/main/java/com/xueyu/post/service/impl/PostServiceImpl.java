package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.facade.ConvertPostDetailFacade;
import com.xueyu.post.facade.ConvertPostListFacade;
import com.xueyu.post.facade.request.ConvertDetailReq;
import com.xueyu.post.facade.request.ConvertPostReq;
import com.xueyu.post.mapper.*;
import com.xueyu.post.pojo.domain.*;
import com.xueyu.post.pojo.enums.PostStatus;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.PostService;
import com.xueyu.post.service.TopicService;
import com.xueyu.post.service.VoteService;
import com.xueyu.resource.client.ResourceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	RabbitTemplate rabbitTemplate;

	@Resource
	PostViewMapper postViewMapper;

	@Resource
	PostMapper postMapper;

	@Resource
	TopicMapper topicMapper;

	@Resource
	TopicService topicService;

	@Resource
	PostTopicMapper postTopicMapper;

	@Resource
	ConvertPostListFacade convertPostListFacade;

	@Resource
	ConvertPostDetailFacade convertPostDetailFacade;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean publishPost(Post post, MultipartFile[] files, Vote vote, String[] options, List<String> names) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		post.setCreateTime(now);
		//html标签转码
		post.setContent(HtmlUtils.htmlEscapeHex(post.getContent()));
		// 存入帖子数据，获得主键值
		query().getBaseMapper().insert(post);
		// 添加数据统计表行数据
		PostGeneral postGeneral = new PostGeneral();
		postGeneral.setPostId(post.getId());
		postGeneralMapper.insert(postGeneral);
		// 添加投票
		if(vote.getType()!=null & vote.getCycle()!=null & vote.getTopic()!=null){
			vote.setPostId(post.getId());
			voteService.launchVote(vote,options);
		}
		// 绑定帖子和话题
		if(!CollectionUtils.isEmpty(names)){
			// 添加话题数据
			topicService.createTopic(names);
			// 插入关联数据
			ArrayList<HashMap> maps = new ArrayList<>();
			LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
			wrapper.in(Topic::getName,names);
			List<Topic> topics = topicMapper.selectList(wrapper);
			if(!topics.isEmpty()){
				for (Topic topic : topics) {
					HashMap<String, Integer> map = new HashMap<>();
					map.put("topicId",topic.getId());
					map.put("postId",post.getId());
					maps.add(map);
				}
			}
			topicMapper.insertPostTopics(maps);
		}
		// 图片是分布式存储的，放最后执行，确保前面业务有异常能够正常回滚
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
		log.info("用户 id -> {}, 上传了帖子到审核列表, 包含 {} 张图片， {} 个话题", post.getUserId(), files == null ? 0 : files.length, names.size());
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
		if (!CollectionUtils.isEmpty(imgList)) {
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
		// 查完将自动赋值记录到 page中
		postViewMapper.selectPage(page, wrapper);
		return queryPostListByPage(page, userId);
	}

	@Override
	public ListVO<PostListVO> getStatusPostListByPage(Integer current, Integer size, Integer userId) {
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PostView::getStatus, PostStatus.EXAMINE.getValue());
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, wrapper);
		return queryPostListByPage(page, userId);
	}

	@Override
	public ListVO<PostListVO> getPostListByTopicIds(String topicName, Integer userId, Integer current, Integer size) {
		LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(Topic::getName, topicName);
		List<Topic> topicList = topicService.list(wrapper);
		List<Integer> topicIds = new ArrayList<>();
		// 统计模糊的话题 相关id
		for (Topic topic : topicList) {
			topicIds.add(topic.getId());
		}
		LambdaQueryWrapper<PostTopic> linkWrapper = new LambdaQueryWrapper<>();
		linkWrapper.in(PostTopic::getTopicId, topicIds);
		// 查询所有相关的 帖子id
		List<PostTopic> postTopics = postTopicMapper.selectList(linkWrapper);
		List<Integer> postIds = new ArrayList<>();
		for (PostTopic postTopic : postTopics) {
			postIds.add(postTopic.getPostId());
		}
		// 分页查找帖子相关记录
		LambdaQueryWrapper<PostView> postWrapper = new LambdaQueryWrapper<>();
		postWrapper.in(PostView::getId, postIds);
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, postWrapper);
		ListVO<PostListVO> res = new ListVO<>();
		BeanUtils.copyProperties(page, res);
		// 请求facade进行处理
		ConvertPostReq convertPostReq = new ConvertPostReq();
		convertPostReq.setPostIds(postIds);
		convertPostReq.setRecords(page.getRecords());
		List<PostListVO> postViewList = convertPostListFacade.execBusiness(convertPostReq);
		res.setRecords(postViewList);
		return res;
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
		ConvertDetailReq convertDetailReq = new ConvertDetailReq();
		convertDetailReq.setPostView(postView);
		convertDetailReq.setUserId(userId);
		return convertPostDetailFacade.execBusiness(convertDetailReq);
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
		if (PostStatus.PUBLIC.getValue().equals(desicion)) {
			log.info("帖子 id -> {}, 审核通过", postId);
		} else {
			log.info("帖子 id -> {}, 审核未通过", postId);
		}
	}

	@Override
	public ListVO<PostListVO> getAllPostListByPage(Integer current, Integer size, Integer userId) {
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PostView::getStatus, PostStatus.PUBLIC.getValue());
		IPage<PostView> page = new Page<>(current, size);
		postViewMapper.selectPage(page, wrapper);
		return queryPostListByPage(page, userId);
	}

	/**
	 * 处理分页查询 帖子列表
	 *
	 * @param page 查询记录之后的 page 对象
	 * @param userId 用户id
	 * @return 分页帖子列表信息
	 */
	public ListVO<PostListVO> queryPostListByPage(IPage<PostView> page, Integer userId){
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(page, result);
		List<PostView> records = page.getRecords();
		result.setRecords(queryPostList(records, userId));
		return result;
	}

	/**
	 * 封装 帖子列表信息，如 用户信息设置等
	 *
	 * @param records 需要处理的帖子列表
	 * @param userId 用户id
	 * @return 帖子列表信息
	 */
	@Override
	public List<PostListVO> queryPostList(List<PostView> records, Integer userId) {
		// 统计postId, userId
		List<Integer> postIds = new ArrayList<>();
		List<Integer> authors = new ArrayList<>();
		for (PostView postView : records) {
			postIds.add(postView.getId());
			authors.add(postView.getUserId());
		}
		// 请求facade进行处理
		ConvertPostReq convertPostReq = new ConvertPostReq();
		convertPostReq.setPostIds(postIds);
		convertPostReq.setRecords(records);
		convertPostReq.setAuthors(authors);
		return convertPostListFacade.execBusiness(convertPostReq);
	}

}
