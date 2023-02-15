package com.xueyu.comment.client;

import com.xueyu.common.core.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author durance
 */
@FeignClient(value = "comment-server", fallback = CommentClientResolver.class)
public interface CommentClient {

	/**
	 * 获取帖子评论集合
	 *
	 * @param postId 帖子id
	 * @return 帖子评论集合
	 */
	@GetMapping("comment/post/list")
	RestResult<Object> getPostCommentList(@RequestParam Integer postId);

}
