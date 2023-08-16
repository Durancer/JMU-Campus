package com.xueyu.user.listener;

import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.pojo.domain.UserGeneral;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xueyu.post.sdk.constant.PostMqContants.*;

/**
 * 帖子服务 mq 监听器
 * 服务队列命名准则：
 * 服务 . 消息发送服务 . 操作行为
 *
 * @author durance
 */
@Slf4j
@Component
public class PostMqListener {

	public static final String VIEW_QUEUE = "user.post.view";

	public static final String LIKE_QUEUE = "user.post.like";

	public static final String LIKE_CANCEL_QUEUE = "user.post.like.cancel";

	public static final String USER_PUBLISH_POST_QUEUE = "user.post.publish";

	public static final String USER_DELETE_POST_QUEUE = "user.post.delete.post";

	@Resource
	UserGeneralMapper userGeneralMapper;

	/**
	 * 用户浏览帖子事件
	 *
	 * @param postOperateDTO 事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = VIEW_QUEUE),
			key = POST_OPERATE_VIEW_KEY
	))
	public void userViewPost(PostOperateDTO postOperateDTO) {
		log.info("用户id ->{}，帖子id -> {} 被浏览", postOperateDTO.getAuthorId(), postOperateDTO.getPostId());
		// 修改数值
		Integer integer = userGeneralMapper.updateViewNumByUserId(postOperateDTO.getAuthorId());
		if (integer != 1) {
			throw new UserException("user_general表修改异常");
		}
	}

	/**
	 * 用户点赞帖子事件
	 *
	 * @param postOperateDTO 事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = LIKE_QUEUE),
			key = POST_OPERATE_LIKE_KEY
	))
	public void userLikePost(PostOperateDTO postOperateDTO) {
		log.info("用户id ->{}，帖子 -> {} 收到点赞", postOperateDTO.getAuthorId(), postOperateDTO.getPostId());
		// 修改数值
		Integer integer = userGeneralMapper.updateLikeNumByUserId(postOperateDTO.getAuthorId(), 1);
		if (integer != 1) {
			throw new UserException("user_general表修改异常");
		}
	}

	/**
	 * 用户取消点赞帖子事件
	 *
	 * @param postOperateDTO 事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = LIKE_CANCEL_QUEUE),
			key = POST_OPERATE_LIKE_CANCEL_KEY
	))
	public void userCancelLikePost(PostOperateDTO postOperateDTO) {
		log.info("用户id-> {}，帖子id -> {} 被取消点赞", postOperateDTO.getAuthorId(), postOperateDTO.getPostId());
		// 修改数值
		Integer integer = userGeneralMapper.updateLikeNumByUserId(postOperateDTO.getAuthorId(), -1);
		if (integer != 1) {
			throw new UserException("user_general表修改异常");
		}
	}

	/**
	 * 用户发布帖子事件
	 *
	 * @param postOperateDTO 事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_PUBLISH_POST_QUEUE),
			key = POST_INSERT_KEY
	))
	public void userPublishPost(PostOperateDTO postOperateDTO){
		log.info("用户 id -> {}, 发布了帖子 id -> {}", postOperateDTO.getUserId(), postOperateDTO.getPostId());
		userGeneralMapper.updatePostNumByUserId(postOperateDTO.getUserId(), 1);
	}

	/**
	 * 用户删除帖子事件
	 *
	 * @param postOperateDTO 事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_DELETE_POST_QUEUE),
			key = POST_DELETE_KEY
	))
	public void userDeletePost(PostOperateDTO postOperateDTO){
		log.info("用户 id -> {}, 删除了帖子 id ->{}", postOperateDTO.getUserId(), postOperateDTO.getPostId());
		userGeneralMapper.updatePostNumByUserId(postOperateDTO.getUserId(), -1);
	}

}
