package com.xueyu.comment.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xueyu.comment.mapper.CommentMapper;
import com.xueyu.comment.mapper.LikeMapper;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.sdk.dto.CommentDTO;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static com.xueyu.comment.sdk.constant.CommentMqContants.COMMENT_DELETE_KEY;
import static com.xueyu.comment.sdk.constant.CommentMqContants.COMMENT_EXCHANGE;
import static com.xueyu.post.sdk.constant.PostMqContants.POST_DELETE_KEY;
import static com.xueyu.post.sdk.constant.PostMqContants.POST_EXCHANGE;

/**
 * 评论服务mq监听
 * 服务队列命名准则：
 * 服务 . 消息发送服务 . 操作行为
 *
 * @author durance
 */
@Slf4j
@Component
public class PostMqListener {

	public static final String POST_DELETE_QUEUE = "comment.post.delete";

	@Resource
	CommentMapper commentMapper;

	@Resource
	LikeMapper likeMapper;

	@Resource
	RabbitTemplate rabbitTemplate;

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = POST_DELETE_QUEUE),
			key = POST_DELETE_KEY
	))
	public void deletePostComment(PostOperateDTO postOperateDTO) {
		log.info("帖子 id ->{},被删除", postOperateDTO.getPostId());
		// 删除对应的评论
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getPostId, postOperateDTO.getPostId());
		int deleteNum = commentMapper.delete(wrapper);
		List<Comment> list = commentMapper.selectList(wrapper);
		// 拿到所有评论的id
		List<Integer> commentId = list.stream().map(Comment::getId).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(commentId) ){
			LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.in(Like::getCommentId, commentId);
			likeMapper.delete(queryWrapper);
		}
		// 删除的数量，发送mq
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setAuthorId(postOperateDTO.getAuthorId());
		commentDTO.setPostId(postOperateDTO.getPostId());
		commentDTO.setUserId(postOperateDTO.getUserId());
		commentDTO.setEffectNum(deleteNum);
		rabbitTemplate.convertAndSend(COMMENT_EXCHANGE, COMMENT_DELETE_KEY, commentDTO);
	}

}
