package com.xueyu.comment.controller;

import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.vo.CommentAnswerVO;
import com.xueyu.comment.service.CommentService;
import com.xueyu.common.core.result.RestResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author durance
 */
@RestController
@RequestMapping("comment")
public class CommentController {

	@Resource
	CommentService commentService;

	/**
	 * 获取帖子评论集合
	 *
	 * @param postId 帖子id
	 * @return 评论集合
	 */
	@GetMapping("post/list")
	public RestResult<Object> getPostCommentList(@RequestParam Integer postId) {
		return RestResult.ok(commentService.getPostComments(postId));
	}

	/**
	 * 获取用户发送的评论列表
	 *
	 * @param userId 用户id
	 * @return 用户发送的评论列表
	 */
	@GetMapping("user/list")
	public RestResult<List<CommentAnswerVO>> getUserCommentList(@RequestHeader Integer userId) {
		return RestResult.ok(commentService.getUserComments(userId));
	}

	/**
	 * 获取用户收到的回复评论列表（不含自己回复自己的）
	 *
	 * @param userId 用户id
	 * @return 用户收到的回复评论列表
	 */
	@GetMapping("user/answer")
	public RestResult<List<CommentAnswerVO>> getUserAnsweredList(@RequestHeader Integer userId) {
		return RestResult.ok(commentService.getUserAnsweredComments(userId));
	}

	/**
	 * 添加用户评论
	 *
	 * @param comment 评论信息
	 * @param userId  用户id
	 * @return 评论发送结果
	 */
	@PostMapping("add")
	public RestResult<?> publishUserComment(Comment comment, @RequestHeader Integer userId) {
		comment.setUserId(userId);
		if (commentService.sendUserComment(comment)) {
			return RestResult.ok(null, "发送成功");
		}
		return RestResult.fail("发送失败");
	}

	/**
	 * 删除用户评论
	 *
	 * @param commentId 评论id
	 * @param userId    用户id
	 * @return 删除结果
	 */
	@PostMapping("delete")
	public RestResult<?> deleteUserComment(@NotNull Integer commentId, @RequestHeader Integer userId) {
		if (commentService.deleteUserComment(userId, commentId)) {
			return RestResult.ok(null, "删除成功");
		}
		return RestResult.fail("删除失败");
	}

}
