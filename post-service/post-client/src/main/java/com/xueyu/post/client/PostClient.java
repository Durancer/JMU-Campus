package com.xueyu.post.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.sdk.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author durance
 */
@FeignClient(value = "post-server", fallback = PostClientResolver.class)
public interface PostClient {

	/**
	 * 获取帖子基本信息
	 *
	 * @param postId 帖子id
	 * @return 帖子基本信息
	 */
	@GetMapping("post/one")
	RestResult<PostDTO> getPostInfo(@RequestParam Integer postId);

}
