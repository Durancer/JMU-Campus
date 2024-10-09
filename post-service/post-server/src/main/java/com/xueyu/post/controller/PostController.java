package com.xueyu.post.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.enums.PostStatus;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.sdk.dto.PostDTO;
import com.xueyu.post.service.PostService;
import com.xueyu.post.service.TopicService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

	@Resource
	TopicService topicService;

	/**
	 * 发布用户帖子
	 *
	 * @param post   帖子信息
	 * @param files  图片文件
	 * @param userId 用户id
	 * @param names  话题名称集合
	 * @return 发布结果
	 */
	@PostMapping("add")
	public RestResult<?> publishPost(Post post, MultipartFile[] files, @RequestHeader Integer userId, Vote vote, String[] options, ArrayList<String> names) {
		int MAX_FILES = 9;
		if (files != null && files.length >= MAX_FILES) {
			throw new PostException("最多上传 9 张图");
		}
		if (CollectionUtils.isNotEmpty(names)) {
			// 核对话题数量及长度是否合规
			int MAX_TOPICS = 3;
			if (names.size() > MAX_TOPICS) {
				throw new PostException("最多携带 3 个话题");
			}
			for (String name : names) {
				topicService.checkTopicLength(name);
			}
		}
		post.setUserId(userId);
		Boolean sendStatus = postService.publishPost(post, files, vote, options, names);
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
	public RestResult<?> deletePost(@NotNull Integer postId, @RequestHeader Integer userId) {
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
	public RestResult<ListVO<PostListVO>> getAllPost(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, @RequestHeader(required = false) Integer userId) {
		ListVO<PostListVO> postListByPage = postService.getAllPostListByPage(current, size, userId);
		return RestResult.ok(postListByPage);
	}

	/**
	 * 分页获取我的帖子
	 *
	 * @param current 当前页
	 * @param size    每页数量
	 * @return 帖子信息
	 */
	@GetMapping("list/user/self")
	public RestResult<ListVO<PostListVO>> getUserSelfPost(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, @RequestHeader Integer userId) {
		ListVO<PostListVO> postListByPage = postService.getUserPostListByPage(current, size, userId);
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
	public RestResult<ListVO<PostListVO>> getUserPost(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, @NotNull Integer userId) {
		ListVO<PostListVO> postListByPage = postService.getUserSelfPostListByPage(current, size, userId);
		return RestResult.ok(postListByPage);
	}

	/**
	 * 获取帖子详细信息
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 帖子详细信息
	 */
	@GetMapping("detail")
	public RestResult<PostDetailVO> getPostDetailInfo(@NotNull Integer postId, @RequestHeader(required = false) Integer userId) {
		return RestResult.ok(postService.getPostDetailInfo(postId, userId));
	}

	/**
	 * 审核用户帖子
	 *
	 * @param postId   帖子id
	 * @param decision 帖子审核选项 0 审核中 | 1 审核通过 | 2 审核不通过
	 * @param reason 如审核未通过，给个未通过理由，反馈给用户
	 * @return 审核结果
	 */
	@PostMapping("check")
	public RestResult<?> checkUserPost(@NotNull Integer postId, @NotNull Integer decision, String reason) {
		if (decision.equals(PostStatus.FAIL.getValue()) && StringUtils.isEmpty(reason)){
			throw new PostException("失败原因不能为空");
		}
		postService.passPostContent(postId, decision, reason);
		return RestResult.ok(null, "提交成功");
	}

	/**
	 * 获取帖子基本信息
	 *
	 * @param postId 帖子id
	 * @return 帖子基本信息
	 */
	@GetMapping("one")
	public RestResult<PostDTO> getPostInfo(@RequestParam Integer postId) {
		PostDTO postDTO = new PostDTO();
		BeanUtils.copyProperties(postService.getById(postId), postDTO);
		return RestResult.ok(postDTO);
	}

	/**
	 * 分页获取未审核的帖子
	 *
	 * @param current 当前页
	 * @param size 页大小
	 * @param userId 用户id
	 * @return 帖子信息
	 */
	@GetMapping("status/list")
	public RestResult<ListVO<PostListVO>> getStatusPost(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, @RequestHeader(required = false) Integer userId) {
		ListVO<PostListVO> postListByPage = postService.getStatusPostListByPage(current, size, userId);
		return RestResult.ok(postListByPage);
	}

	/**
	 * 更新匿名状态，原本为是，则更新为否
	 *
	 * @param postId 页大小
	 * @param userId 用户id
	 * @return 帖子信息
	 */
	@PostMapping("update/anonymous")
	public RestResult<ListVO<PostListVO>> updatePostAnonymous(@RequestHeader Integer userId, @Validated @NotNull Integer postId) {
		postService.updatePostAnonymous(userId, postId);
		return RestResult.ok();
	}

}
