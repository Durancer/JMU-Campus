package com.xueyu.comment.client;

import com.xueyu.common.core.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author durance
 */
@FeignClient(value = "comment-server", fallback = CommentClientResolver.class)
public interface CommentClient {


	/**
	 * 得到邮报评论列表
	 *
	 * @param userId 用户id
	 * @param postId post id
	 * @return {@link RestResult}<{@link Object}>
	 */
	@GetMapping("comment/post/list")
	RestResult<Object> getPostCommentList(@RequestParam Integer userId, @RequestParam Integer postId);

}
