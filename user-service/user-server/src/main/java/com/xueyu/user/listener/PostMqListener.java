package com.xueyu.user.listener;

import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.UserGeneralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xueyu.post.sdk.constant.PostMqContant.*;

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
		log.info("用户id为：{}，帖子被浏览", postOperateDTO.getAuthorId());
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
		log.info("用户id为：{}，帖子收到点赞", postOperateDTO.getAuthorId());
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
		log.info("用户id为：{}，帖子被取消点赞", postOperateDTO.getAuthorId());
		// 修改数值
		Integer integer = userGeneralMapper.updateLikeNumByUserId(postOperateDTO.getAuthorId(), -1);
		if (integer != 1) {
			throw new UserException("user_general表修改异常");
		}
	}

}
