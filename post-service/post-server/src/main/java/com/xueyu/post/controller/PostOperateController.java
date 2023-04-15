package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.service.PostOperateService;
import org.springframework.lang.NonNull;
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
	public RestResult<?> likeUserPost(@NonNull Integer postId, @RequestHeader Integer userId) {
		if (postOperateService.likeUserPost(postId, userId)) {
			return RestResult.ok(null, "点赞成功");
		}
		return RestResult.ok(null, "取消点赞");
	}

	/**
	 * 查看用户是否点赞了该帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 点赞结果
	 */
	@GetMapping("like/check")
	public RestResult<Boolean> checkUserLikePost(@NonNull Integer postId, @NonNull Integer userId) {
		if (postOperateService.checkLiked(postId, userId)) {
			return RestResult.ok(true, "已点赞");
		}
		return RestResult.ok(false, "未点赞");
	}

}
