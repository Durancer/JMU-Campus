package com.xueyu.resource.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.sdk.bo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.xueyu.common.core.enums.ResultTypeEnum.SERVICE_EXCEPTION;

/**
 * @author durance
 */
@Slf4j
@Component
public class ResourceClientResolver implements ResourceClient {

	@Override
	public RestResult<Map<String, String>> uploadImageFile(MultipartFile file) {
		log.error("Resource 服务异常：uploadImageFile 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<Map<String, String>> uploadImageFiles(MultipartFile[] files) {
		log.error("Resource 服务异常：uploadImageFiles 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<?> deleteFileByFileName(String fileName) {
		log.error("Resource 服务异常：deleteFileByFileName 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<?> deleteFilesListByFileName(String[] fileNames) {
		log.error("Resource 服务异常：deleteFilesListByFileName 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<?> sendSystemMail(Mail mail) {
		log.error("Resource 服务异常：deleteFilesListByFileName 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

}
