package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 帖子服务接口
 *
 * @author durance
 */
@RestController
@RequestMapping("post")
public class PostController {

	@Resource
	PostService postService;

	@PostMapping("addPost")
	public RestResult<?> pushlishPost(Post post, MultipartFile[] files) {
		Boolean sendStatus = postService.publishPost(post, files);
		if (!sendStatus) {
			return RestResult.fail("发布失败");
		}
		return RestResult.ok("发布成功");
	}

}
