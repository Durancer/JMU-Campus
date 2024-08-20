package com.xueyu.comment.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.comment.sdk.vo.CommentPostVO;
import com.xueyu.comment.request.PostCommentQueryRequest;
import com.xueyu.comment.exception.CommentException;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.enums.CommentStatusEnum;
import com.xueyu.comment.request.CommentQueryRequest;
import com.xueyu.comment.service.CommentService;
import com.xueyu.common.core.request.PageRequest;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author durance
 */
@RestController
@Slf4j
@RequestMapping("comment")
public class CommentController {

	@Resource
	CommentService commentService;

	/**
	 * 获取帖子评论集合
	 *
	 * @param request req
	 * @return 评论集合
	 */
	@GetMapping("post/list")
	public RestResult<ListVO<CommentPostVO>> getPostCommentList(@Validated PostCommentQueryRequest request) {
		return RestResult.ok(commentService.getPostComments(request));
	}

	/**
	 * 获取用户发送的评论列表
	 *
	 * @param userId 用户id
	 * @return 用户发送的评论列表
	 */
	@GetMapping("user/list")
	public RestResult<ListVO<CommentAnswerVO>> getUserCommentList(@RequestHeader(required = false) Integer userId, Integer otherUserId, PageRequest pageRequest) {
		if(otherUserId != null){
			return RestResult.ok(commentService.getOtherUserComments(otherUserId, pageRequest));
		}
		if(userId == null){
			throw new CommentException("参数异常");
		}
		return RestResult.ok(commentService.getUserSelfComments(userId, pageRequest));
	}

	/**
	 * 获取用户收到的回复评论列表（不含自己回复自己的）
	 *
	 * @param userId 用户id
	 * @return 用户收到的回复评论列表
	 */
	@GetMapping("user/answer")
	public RestResult<ListVO<CommentAnswerVO>> getUserAnsweredList(@RequestHeader Integer userId, PageRequest pageRequest) {
		return RestResult.ok(commentService.getUserAnsweredComments(userId, pageRequest));
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

	/**
	 * 更新用户评论
	 *
	 * @param userId 用户id
	 * @param commentId 评论id
	 * @param content 更新内容
	 * @return
	 */
	@PostMapping("update")
	public RestResult<?> updateUserComment(@RequestHeader Integer userId, @NotNull Integer commentId, String content){
		Comment comment = new Comment();
		comment.setId(commentId);
		comment.setContent(content);
		comment.setUserId(userId);
		commentService.updateComment(comment);
		return RestResult.ok(null, "更新成功");
	}

	/**
	 * 获取各个帖子热度最高的评论
	 *
	 * @param postIds 帖子id
	 * @return 帖子热度最高评论
	 */
	@GetMapping("post/hot")
	public RestResult<List<CommentAnswerVO>> getPostsMaxHotComment(Integer[] postIds){
		if(postIds == null || postIds.length == 0){
			return RestResult.fail("请求体为空");
		}
		List<Integer> postIdList = Arrays.asList(postIds);
		List<CommentAnswerVO> postHotComments = commentService.postsMaxHotComment(postIdList);
		return RestResult.ok(postHotComments);
	}

	/**
	 * 获取各个帖子热度最高的评论
	 *
	 * @param request req
	 * @return 帖子热度最高评论
	 */
	@GetMapping("manage/list")
	public RestResult<ListVO<Comment>> getManageCommentListPage(CommentQueryRequest request){
		return RestResult.ok(commentService.getManageCommentListPage(request));
	}


	/**
	 * 审核用户评论
	 *
	 * @param commentId   帖子id
	 * @param decision 帖子审核选项 0 审核中 | 1 审核通过 | 2 审核不通过
	 * @param reason 如审核未通过，给个未通过理由，反馈给用户
	 * @return 审核结果
	 */
	@PostMapping("check")
	public RestResult<?> checkUserPost(@Validated @NotNull Integer commentId, @Validated @NotNull Integer decision, String reason) {
		if (decision.equals(CommentStatusEnum.FAIL.getCode()) && StringUtils.isEmpty(reason)){
			throw new CommentException("失败原因不能为空");
		}
		commentService.passCommentContent(commentId, decision, reason);
		return RestResult.ok(null, "提交成功");
	}


}
