package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.LikePostMapper;
import com.xueyu.post.mapper.PostGeneralMapper;
import com.xueyu.post.mapper.PostMapper;
import com.xueyu.post.pojo.domain.LikePost;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.PostOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

import static com.xueyu.post.sdk.constant.PostMqContants.*;

/**
 * @author durance
 */
@Service
@Slf4j
public class PostOperateServiceImpl implements PostOperateService {

	@Resource
	LikePostMapper likePostMapper;

	@Resource
	PostMapper postMapper;

	@Resource
	PostGeneralMapper postGeneralMapper;

	@Resource
	RabbitTemplate rabbitTemplate;

	@Override
	public Boolean likeUserPost(Integer postId, Integer userId) {
		Post post = postMapper.selectById(postId);
		if (post == null) {
			throw new PostException("不存在此帖子");
		}
		LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
		LikePost like = likePostMapper.selectOne(wrapper);
		// 封装mq消息体，减少代码冗余
		PostOperateDTO postOperateDTO = new PostOperateDTO();
		postOperateDTO.setAuthorId(post.getUserId());
		postOperateDTO.setPostId(postId);
		postOperateDTO.setUserId(userId);
		// 如果不存在则没有点赞，否则取消点赞
		if (like == null) {
			// 数据库添加点赞信息
			LikePost likePost = new LikePost();
			likePost.setPostId(postId);
			likePost.setUserId(userId);
			likePost.setTime(new Timestamp(System.currentTimeMillis()));
			likePostMapper.insert(likePost);
			postGeneralMapper.updateLikeNumByPostId(postId, 1);
			log.info("用户 id -> {} 点赞了 帖子 postId ->{}", userId, postId);
			// 发送点赞帖子事件消息
			rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_OPERATE_LIKE_KEY, postOperateDTO);
			return true;
		}
		// 取消点赞
		likePostMapper.delete(wrapper);
		postGeneralMapper.updateLikeNumByPostId(postId, -1);
		log.info("用户 id -> {} 取消点赞了 帖子 postId ->{}", userId, postId);
		// 发送取消点赞帖子事件消息
		rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_OPERATE_LIKE_CANCEL_KEY, postOperateDTO);
		return false;
	}

	@Override
	public Boolean checkLiked(Integer postId, Integer userId) {
		LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
		LikePost likePost = likePostMapper.selectOne(wrapper);
		// 不存在返回false，反之
		return likePost != null;
	}

}
