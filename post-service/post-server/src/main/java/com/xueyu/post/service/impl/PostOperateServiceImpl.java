package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.LikePostMapper;
import com.xueyu.post.mapper.PostGeneralMapper;
import com.xueyu.post.mapper.PostMapper;
import com.xueyu.post.pojo.domain.LikePost;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.enums.PostIsPrivateEnum;
import com.xueyu.post.pojo.enums.PostIsTopEnum;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.PostOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

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
			likePost.setTime(new Date());
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
		return true;
	}

	@Override
	public Boolean checkLiked(Integer postId, Integer userId) {
		LambdaQueryWrapper<LikePost> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikePost::getPostId, postId).eq(LikePost::getUserId, userId);
		LikePost likePost = likePostMapper.selectOne(wrapper);
		// 不存在返回false，反之
		return likePost != null;
	}

	@Override
	public Boolean hideOrOpenPost(Integer postId, Integer userId) {
		Post post = postMapper.selectById(postId);
		if (Objects.isNull(post)){
			log.error("帖子信息查询为空");
			throw new PostException("帖子信息查询为空");
		}
		if (!post.getUserId().equals(userId)){
			log.error("非该用户的帖子，无法私密或公开");
			throw new PostException("非该用户的帖子，无法私密或公开");
		}
		Post update = new Post();
		update.setId(postId);
		update.setIsPrivate(post.getIsPrivate().equals(PostIsPrivateEnum.NO.getValue()) ?
				PostIsPrivateEnum.YES.getValue() : PostIsPrivateEnum.NO.getValue());
		int i = postMapper.updateById(update);
		if(i != 1){
			throw new PostException("操作异常");
		}
		if (update.getIsPrivate().equals(PostIsPrivateEnum.YES.getValue())){
			log.info("用户id -> {}, 帖子 -> {}, 私密成功", userId, postId);
		}else {
			log.info("用户id -> {}, 帖子 -> {}, 公开成功", userId, postId);
		}
		return true;
	}

	@Override
	public Boolean topOrCancelPost(Integer postId, Integer userId) {
		Post post = postMapper.selectById(postId);
		if (Objects.isNull(post)){
			log.error("帖子信息查询为空");
			throw new PostException("帖子信息查询为空");
		}
		if (!post.getUserId().equals(userId)){
			log.error("非该用户的帖子，无法私密或公开");
			throw new PostException("非该用户的帖子，无法置顶或取消置顶");
		}
		Post update = new Post();
		update.setId(postId);
		update.setIsTop(post.getIsTop().equals(PostIsTopEnum.NO.getValue()) ?
				PostIsTopEnum.YES.getValue() : PostIsTopEnum.NO.getValue());
		// 如果要执行置顶，检查数量是否超出
		final int maxTop = 3;
		if (update.getIsTop().equals(PostIsTopEnum.YES.getValue())){
			LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Post::getUserId, userId);
			wrapper.eq(Post::getIsTop, PostIsTopEnum.YES.getValue());
			Long num = postMapper.selectCount(wrapper);
			// 如该用户现在已经有3个了，则驳回
			if (num >= maxTop){
				throw new PostException("已达最高置顶帖子数量");
			}
		}
		int i = postMapper.updateById(update);
		if(i != 1){
			throw new PostException("操作异常");
		}
		if (update.getIsTop().equals(PostIsTopEnum.YES.getValue())){
			log.info("用户id -> {}, 帖子 -> {}, 置顶成功", userId, postId);
		}else {
			log.info("用户id -> {}, 帖子 -> {}, 取消置顶成功", userId, postId);
		}
		return true;
	}

}
