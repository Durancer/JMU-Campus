package com.xueyu.resource.client;

import com.xueyu.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author durance
 */
@Slf4j
@Component
public class ResourceClientResolver implements ResourceClient {

	@Override
	public RestResult<Map<String, String>> uploadFile(MultipartFile file) {
		log.error("Resource 服务异常：uploadFile 请求失败");
		return new RestResult<>(503, "fail");
	}

	@Override
	public RestResult<?> deleteFileByFileName(String fileName) {
		log.error("Resource 服务异常：uploadFile 请求失败");
		return new RestResult<>(503, "fail");
	}

}
