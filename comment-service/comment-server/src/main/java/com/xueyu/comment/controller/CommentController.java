package com.xueyu.comment.controller;

import com.xueyu.comment.pojo.vo.CommentPostVO;
import com.xueyu.comment.service.CommentService;
import com.xueyu.common.core.result.RestResult;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("post/list")
	public RestResult<List<CommentPostVO>> getPostCommentList(@NonNull Integer postId) {
		return RestResult.ok(commentService.getPostComments(postId));
	}

}
