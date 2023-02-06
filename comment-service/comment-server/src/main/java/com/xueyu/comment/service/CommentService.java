package com.xueyu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.vo.CommentPostVO;

import java.util.List;

/**
 * @author durance
 */
public interface CommentService extends IService<Comment> {

	/**
	 * 发送评论
	 *
	 * @param comment 评论信息
	 * @return 评论结果
	 */
	Boolean sendUserComment(Comment comment);

	/**
	 * 删除评论
	 *
	 * @param userId    用户id
	 * @param commentId 评论id
	 * @return 删除结果
	 */
	Boolean deleteUserComment(Integer userId, Integer commentId);

	/**
	 * 获取帖子评论信息
	 *
	 * @param postId 帖子id
	 * @return 评论信息
	 */
	List<CommentPostVO> getPostComments(Integer postId);

}
