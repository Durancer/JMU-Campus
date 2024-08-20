package com.xueyu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.comment.sdk.vo.CommentPostVO;
import com.xueyu.comment.request.CommentQueryRequest;
import com.xueyu.comment.request.PostCommentQueryRequest;
import com.xueyu.common.core.request.PageRequest;
import com.xueyu.common.core.result.ListVO;

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
	 * @param request req
	 * @return 评论信息
	 */
	ListVO<CommentPostVO> getPostComments(PostCommentQueryRequest request);

	/**
	 * 获取用户自己发送的评论信息
	 *
	 * @param userId 用户id
	 *               	 * @param request req
	 * @return 评论信息
	 */
	ListVO<CommentAnswerVO> getUserSelfComments(Integer userId, PageRequest request);

	/**
	 * 获取其他用户自己发送的评论信息
	 *
	 * @param userId 用户id
	 * @param request req
	 * @return 评论信息
	 */
	ListVO<CommentAnswerVO> getOtherUserComments(Integer userId, PageRequest request);

	/**
	 * 获取用户收到的回复评论
	 *
	 * @param toUserId 回复的用户id
	 *                 	 * @param request req
	 * @return 评论信息
	 */
	ListVO<CommentAnswerVO> getUserAnsweredComments(Integer toUserId, PageRequest request);

	/**
	 * 将评论对象转化为 answerVO对象
	 *
	 * @param commentList 评论集合
	 * @return answerVO对象
	 */
	List<CommentAnswerVO> commentConvertAnswerVO(List<Comment> commentList);

	/**
	 * 更新评论
	 *
	 * @param comment 更新评论内容
	 */
	void updateComment(Comment comment);

	/**
	 * 获取各个帖子热度最高的评论
	 *
	 * @param postIds 帖子id 集合
	 * @return answerVO对象
	 */
	List<CommentAnswerVO> postsMaxHotComment(List<Integer> postIds);

	/**
	 * 管理站获取评论列表
	 *
	 * @param request req
	 * @return
	 */
	ListVO<Comment> getManageCommentListPage(CommentQueryRequest request);

	/**
	 * 审核评论内容
	 *
	 * @param  commentId  评论id
	 * @param decision 审核选择0 审核中 1 通过，2 未通过
	 * @param reason 如审核未通过，给个未通过理由，反馈给用户
	 */
	void passCommentContent(Integer commentId, Integer decision, String reason);

}
