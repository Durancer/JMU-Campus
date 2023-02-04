package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.service.PostService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	PostService postService;

	@PostMapping("like")
	public RestResult<?> likeUserPost(@NonNull Integer postId, @NonNull Integer userId) {
		if (postService.likeUserPost(postId, userId)) {
			return RestResult.ok(null, "点赞成功");
		}
		return RestResult.ok(null, "取消点赞");
	}

}
