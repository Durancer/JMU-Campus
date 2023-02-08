package com.xueyu.user.listener;

import com.xueyu.comment.sdk.dto.CommentDTO;
import com.xueyu.user.mapper.UserGeneralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xueyu.comment.sdk.constant.CommentMqContants.*;

/**
 * 评论服务mq监听
 * 服务队列命名准则：
 * 服务 . 消息发送服务 . 操作行为
 *
 * @author durance
 */
@Slf4j
@Component
public class CommentMqListener {

	public static final String COMMENT_QUEUE = "user.comment.add";

	public static final String COMMENT_CANCEL_QUEUE = "user.comment.cancel";

	@Resource
	UserGeneralMapper userGeneralMapper;

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_QUEUE),
			key = COMMENT_INSERT_KEY
	))
	public void addPostComment(CommentDTO commentDTO) {
		log.info("用户 id ->{}, 收到评论增加", commentDTO.getAuthorId());
		userGeneralMapper.updateCommentNumByUserId(commentDTO.getAuthorId(), 1);
	}

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_CANCEL_QUEUE),
			key = COMMENT_DELETE_KEY
	))
	public void deletePostComment(CommentDTO commentDTO) {
		log.info("用户 id ->{},减少收到 {} 条评论", commentDTO.getAuthorId(), commentDTO.getEffectNum());
		userGeneralMapper.updateCommentNumByUserId(commentDTO.getAuthorId(), -commentDTO.getEffectNum());
	}

}
