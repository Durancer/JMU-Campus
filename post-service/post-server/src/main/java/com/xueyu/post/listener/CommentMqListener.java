package com.xueyu.post.listener;

import com.xueyu.comment.sdk.dto.CommentDTO;
import com.xueyu.post.mapper.PostGeneralMapper;
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

	public static final String COMMENT_QUEUE = "post.comment.add";

	public static final String COMMENT_CANCEL_QUEUE = "post.comment.cancel";

	@Resource
	PostGeneralMapper postGeneralMapper;

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_QUEUE),
			key = COMMENT_INSERT_KEY
	))
	public void addPostComment(CommentDTO commentDTO) {
		log.info("帖子 id ->{},增加评论", commentDTO.getPostId());
		postGeneralMapper.updateCommentNumByPostId(commentDTO.getPostId(), 1);
	}

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_CANCEL_QUEUE),
			key = COMMENT_DELETE_KEY
	))
	public void deletePostComment(CommentDTO commentDTO) {
		log.info("帖子 id ->{},删除 {} 条评论", commentDTO.getPostId(), commentDTO.getEffectNum());
		postGeneralMapper.updateCommentNumByPostId(commentDTO.getPostId(), -commentDTO.getEffectNum());
	}

}
