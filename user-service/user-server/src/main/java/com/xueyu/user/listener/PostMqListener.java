package com.xueyu.user.listener;

import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.user.mapper.UserGeneralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xueyu.post.sdk.constant.PostMqContant.POST_EXCHANGE;
import static com.xueyu.post.sdk.constant.PostMqContant.POST_OPERATE_LIKE_KEY;

/**
 * 帖子服务 mq 监听器
 *
 * @author durance
 */
@Slf4j
@Component
public class PostMqListener {

	public static final String LIKE_QUEUE = "post.like";

	@Resource
	UserGeneralMapper userGeneralMapper;

	/**
	 * 用户点赞帖子事件
	 *
	 * @param postOperateDTO 点赞事件信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = LIKE_QUEUE),
			key = POST_OPERATE_LIKE_KEY
	))
	public void userLikePost(PostOperateDTO postOperateDTO) {
		log.info("用户id为：{}，帖子收到点赞", postOperateDTO.getAuthorId());
		// 修改数值
		userGeneralMapper.updateLikeNumByUserId(postOperateDTO.getAuthorId(), 1);
	}

}
