package com.xueyu.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.comment.exception.CommentException;
import com.xueyu.comment.mapper.CommentMapper;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.domain.CommentType;
import com.xueyu.comment.pojo.vo.CommentAnswerVO;
import com.xueyu.comment.pojo.vo.CommentPostVO;
import com.xueyu.comment.sdk.dto.CommentDTO;
import com.xueyu.comment.service.CommentService;
import com.xueyu.post.client.PostClient;
import com.xueyu.post.sdk.dto.PostDTO;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

import static com.xueyu.comment.sdk.constant.CommentMqContants.*;

/**
 * @author durance
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Resource
	RabbitTemplate rabbitTemplate;

	@Resource
	UserClient userClient;

	@Resource
	PostClient postClient;

	@Override
	public Boolean sendUserComment(Comment comment) {
		PostDTO postInfo = postClient.getPostInfo(comment.getPostId()).getData();
		if (postInfo == null) {
			throw new CommentException("不存在该帖子信息");
		}
		if (!(comment.getType().equals(CommentType.ROOT.getValue()) || comment.getType().equals(CommentType.ANSWER.getValue()))) {
			throw new CommentException("错误的评论类型");
		}
		// 如果为根评论则设置rootId为本身id
		if (comment.getType().equals(CommentType.ROOT.getValue())) {
			comment.setRootId(comment.getId());
		}
		Timestamp time = new Timestamp(System.currentTimeMillis());
		comment.setCreateTime(time);
		query().getBaseMapper().insert(comment);
		// 发送mq消息
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setCommentId(comment.getId());
		commentDTO.setPostId(comment.getPostId());
		commentDTO.setUserId(comment.getUserId());
		commentDTO.setAuthorId(postInfo.getUserId());
		rabbitTemplate.convertAndSend(COMMENT_EXCHANGE, COMMENT_INSERT_KEY, commentDTO);
		return true;
	}

	@Override
	public Boolean deleteUserComment(Integer userId, Integer commentId) {
		Comment comment = lambdaQuery().eq(Comment::getId, commentId).eq(Comment::getUserId, userId).one();
		if (comment == null) {
			throw new CommentException("评论与用户不匹配");
		}
		// 如果为根评论，需要连同回复评论一起删除，否则仅删除该条评论即可
		int deleteNum = 0;
		if (comment.getType().equals(CommentType.ROOT.getValue())) {
			LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Comment::getRootId, commentId);
			deleteNum = query().getBaseMapper().delete(wrapper);
		} else {
			deleteNum = query().getBaseMapper().deleteById(commentId);
		}
		// 发送mq消息
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setEffectNum(deleteNum);
		commentDTO.setUserId(userId);
		commentDTO.setPostId(comment.getPostId());
		commentDTO.setCommentId(commentId);
		rabbitTemplate.convertAndSend(COMMENT_EXCHANGE, COMMENT_DELETE_KEY, commentDTO);
		// todo 添加作者id传输
		return true;
	}

	@Override
	public List<CommentPostVO> getPostComments(Integer postId) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getPostId, postId).orderByAsc(Comment::getCreateTime);
		// 查询出属于该帖子的所有评论
		List<Comment> comments = query().getBaseMapper().selectList(wrapper);
		// 如果为空返回空列表
		if (comments.size() == 1) {
			return new ArrayList<>();
		}
		// 统计用户id，并进行去重
		Set<Integer> userIdSet = new HashSet<>();
		for (Comment comment : comments) {
			userIdSet.add(comment.getUserId());
		}
		// 查询出所有有关的用户信息
		List<Integer> userIds = new ArrayList<>(userIdSet);
		Map<Integer, UserSimpleVO> userInfo = userClient.getUserDeatilInfoMap(userIds).getData();
		List<CommentPostVO> rootComment = new ArrayList<>();
		// 创建关联map，key为根id，值为 子评论集合
		Map<Integer, List<CommentAnswerVO>> answerCommentMap = new HashMap<>(10);
		Map<CommentPostVO, Comment> linkMap = new HashMap<>(10);
		for (Comment comment : comments) {
			// 倒序存入根评论，正序存入回复
			if (comment.getType().equals(CommentType.ROOT.getValue())) {
				CommentPostVO commentPostVO = new CommentPostVO();
				BeanUtils.copyProperties(comment, commentPostVO);
				// 设置用户信息
				commentPostVO.setUserInfo(userInfo.get(comment.getUserId()));
				// 从头部插入根评论，并创建map
				rootComment.add(0, commentPostVO);
				answerCommentMap.put(comment.getRootId(), new ArrayList<>());
				linkMap.put(commentPostVO, comment);
			} else {
				// 时间排序的集合可以保证已经创建了根评论的map
				List<CommentAnswerVO> commentAnswerVOList = answerCommentMap.get(comment.getRootId());
				CommentAnswerVO commentAnswerVO = new CommentAnswerVO();
				BeanUtils.copyProperties(comment, commentAnswerVO);
				// 设置用户信息和回复用户信息
				commentAnswerVO.setUserInfo(userInfo.get(comment.getUserId()));
				commentAnswerVO.setAnswerUserInfo(userInfo.get(comment.getToUserId()));
				commentAnswerVOList.add(commentAnswerVO);
			}
		}
		// 第二次遍历将子评论赋值到根评论
		for (Map.Entry<CommentPostVO, Comment> entry : linkMap.entrySet()) {
			entry.getKey().setAnswerCommentList(answerCommentMap.get(entry.getValue().getRootId()));
		}
		return rootComment;
	}

	@Override
	public List<CommentAnswerVO> getUserComments(Integer userId) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getUserId, userId);
		List<Comment> comments = query().getBaseMapper().selectList(wrapper);
		return commentConvertAnswerVO(comments);
	}

	@Override
	public List<CommentAnswerVO> getUserAnsweredComments(Integer toUserId) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		// 查询收到得到信息，且不为自己发送的
		wrapper.eq(Comment::getToUserId, toUserId).ne(Comment::getUserId, toUserId);
		List<Comment> comments = query().getBaseMapper().selectList(wrapper);
		return commentConvertAnswerVO(comments);
	}

	@Override
	public List<CommentAnswerVO> commentConvertAnswerVO(List<Comment> commentList) {
		// 统计关联的用户id
		Set<Integer> userIdSet = new HashSet<>();
		for (Comment comment : commentList) {
			userIdSet.add(comment.getUserId());
			userIdSet.add(comment.getToUserId());
		}
		Map<Integer, UserSimpleVO> userData = userClient.getUserDeatilInfoMap(new ArrayList<>(userIdSet)).getData();
		List<CommentAnswerVO> answerVOList = new ArrayList<>();
		for (Comment comment : commentList) {
			CommentAnswerVO commentAnswerVO = new CommentAnswerVO();
			BeanUtils.copyProperties(comment, commentAnswerVO);
			commentAnswerVO.setUserInfo(userData.get(comment.getUserId()));
			commentAnswerVO.setAnswerUserInfo(userData.get(comment.getToUserId()));
			answerVOList.add(commentAnswerVO);
		}
		return answerVOList;
	}

}
