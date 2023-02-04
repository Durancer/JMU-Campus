package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.LikePostMapper;
import com.xueyu.post.mapper.PostGeneralMapper;
import com.xueyu.post.mapper.PostMapper;
import com.xueyu.post.pojo.domain.ImageAnnex;
import com.xueyu.post.pojo.domain.LikePost;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.domain.PostGeneral;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.PostService;
import com.xueyu.resource.client.ResourceClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.xueyu.post.sdk.constant.PostMqContant.*;

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
	LikePostMapper likePostMapper;

	@Resource
	RabbitTemplate rabbitTemplate;

	@Override
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
	public Boolean likeUserPost(Integer postId, Integer userId) {
		Post post = query().getBaseMapper().selectById(postId);
		if (post == null) {
			throw new PostException("不存在此帖子");
		}
		LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
		LikePost like = likePostMapper.selectOne(wrapper);
		// 如果不存在则没有点赞，否则取消点赞
		if (like == null) {
			// 数据库添加点赞信息
			LikePost likePost = new LikePost();
			likePost.setPostId(postId);
			likePost.setUserId(userId);
			likePost.setTime(new Timestamp(System.currentTimeMillis()));
			likePostMapper.insert(likePost);
			postGeneralMapper.updateLikeNumByPostId(postId, 1);
			// 发送点赞帖子事件消息
			PostOperateDTO postOperateDTO = new PostOperateDTO();
			postOperateDTO.setAuthorId(post.getUserId());
			postOperateDTO.setPostId(postId);
			postOperateDTO.setUserId(userId);
			rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_OPERATE_LIKE_KEY, postOperateDTO);
			return true;
		} else {
			likePostMapper.delete(wrapper);
			postGeneralMapper.updateLikeNumByPostId(postId, -1);
			// 发送取消点赞帖子事件消息
			PostOperateDTO postOperateDTO = new PostOperateDTO();
			postOperateDTO.setAuthorId(post.getUserId());
			postOperateDTO.setPostId(postId);
			postOperateDTO.setUserId(userId);
			rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_OPERATE_LIKE_CANCEL_KEY, postOperateDTO);
			return false;
		}

	}

}
