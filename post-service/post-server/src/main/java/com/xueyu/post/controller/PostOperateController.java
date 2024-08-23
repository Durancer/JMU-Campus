package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.service.PostOperateService;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 帖子行为接口层
 *
 * @author durance
 */
@RequestMapping("post/operate")
@RestController
public class PostOperateController {

	@Resource
	PostOperateService postOperateService;

	/**
	 * 点赞（取消）用户帖子
	 * 未点赞则进行点赞，反之
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 执行结果
	 */
	@PostMapping("like")
	public RestResult<?> likeUserPost(@Validated @NonNull Integer postId, @RequestHeader Integer userId) {
		if (postOperateService.likeUserPost(postId, userId)) {
			return RestResult.ok(null, "操作成功");
		}
		return RestResult.fail("操作失败");
	}

	/**
	 * 查看用户是否点赞了该帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 点赞结果
	 */
	@GetMapping("like/check")
	public RestResult<Boolean> checkUserLikePost(@Validated @NonNull Integer postId, @NonNull Integer userId) {
		if (postOperateService.checkLiked(postId, userId)) {
			return RestResult.ok(true, "点赞成功");
		}
		return RestResult.fail("点赞失败");
	}

	/**
	 * 用户私密/公开帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 点赞结果
	 */
	@PostMapping("private")
	public RestResult<Boolean> hideOrOpenPost(@Validated @NonNull Integer postId, @RequestHeader Integer userId) {
		if (postOperateService.hideOrOpenPost(postId, userId)) {
			return RestResult.ok(true, "操作成功");
		}
		return RestResult.fail("操作失败");
	}

	/**
	 * 用户置顶/取消置顶帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 点赞结果
	 */
	@PostMapping("top")
	public RestResult<Boolean> topOrCancelPost(@Validated @NonNull Integer postId, @RequestHeader Integer userId) {
		if (postOperateService.topOrCancelPost(postId, userId)) {
			return RestResult.ok(true, "操作成功");
		}
		return RestResult.fail("操作失败");
	}

}
