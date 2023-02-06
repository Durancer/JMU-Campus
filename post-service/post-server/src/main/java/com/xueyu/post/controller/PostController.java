package com.xueyu.post.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.service.PostService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * 发布用户帖子
	 *
	 * @param post  帖子信息
	 * @param files 图片文件
	 * @return 发布结果
	 */
	@PostMapping("add")
	public RestResult<?> pushlishPost(Post post, MultipartFile[] files) {
		Boolean sendStatus = postService.publishPost(post, files);
		if (!sendStatus) {
			return RestResult.fail("发布失败");
		}
		return RestResult.ok(null, "发布成功");
	}

	/**
	 * 删除用户帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 删除结果
	 */
	@PostMapping("delete")
	public RestResult<?> deletePost(@NonNull Integer postId, @RequestHeader Integer userId) {
		if (!postService.deletePost(postId, userId)) {
			return RestResult.fail("删除失败");
		}
		return RestResult.ok(null, "删除成功");
	}

	/**
	 * 分页获取所有帖子
	 *
	 * @param current 当前页
	 * @param size    每页数量
	 * @return 帖子信息
	 */
	@GetMapping("list/all")
	public RestResult<ListVO<PostListVO>> getAllPost(Integer current, Integer size) {
		ListVO<PostListVO> postListByPage = postService.getPostListByPage(current, size, null);
		return RestResult.ok(postListByPage);
	}

	/**
	 * 分页获取用户帖子
	 *
	 * @param current 当前页
	 * @param size    每页数量
	 * @return 帖子信息
	 */
	@GetMapping("list/user")
	public RestResult<ListVO<PostListVO>> getUserPost(Integer current, Integer size, @RequestHeader Integer userId) {
		ListVO<PostListVO> postListByPage = postService.getPostListByPage(current, size, userId);
		return RestResult.ok(postListByPage);
	}

}
