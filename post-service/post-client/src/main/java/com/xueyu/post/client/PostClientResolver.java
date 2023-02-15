package com.xueyu.post.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.sdk.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author durance
 */
@Slf4j
@Component
public class PostClientResolver implements PostClient {

	@Override
	public RestResult<PostDTO> getPostInfo(Integer postId) {
		log.error("post 服务异常：getPostInfo 请求失败");
		return new RestResult<>(503, "fail");
	}

}
