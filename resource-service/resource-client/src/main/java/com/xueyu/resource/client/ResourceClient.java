package com.xueyu.resource.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.sdk.bo.Mail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author durance
 */
@FeignClient(value = "resource-server", fallback = ResourceClientResolver.class)
public interface ResourceClient {

	/**
	 * 上传图片接口
	 *
	 * @param file 图片
	 * @return 图片链接
	 */
	@PostMapping(value = "/resource/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<Map<String, String>> uploadImageFile(@RequestPart MultipartFile file);

	/**
	 * 上传多个图片接口
	 *
	 * @param files 图片
	 * @return 图片链接
	 */
	@PostMapping(value = "/resource/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<Map<String, String>> uploadImageFiles(@RequestPart MultipartFile[] files);

	/**
	 * 删除文件接口
	 *
	 * @param fileName 文件名称
	 * @return 删除结果
	 */
	@PostMapping(value = "/resource/delete")
	RestResult<?> deleteFileByFileName(@RequestBody String fileName);

	/**
	 * 批量删除文件接口
	 *
	 * @param fileNames 文件列表
	 * @return 删除结果
	 */
	@PostMapping(value = "/resource/delete/list")
	RestResult<?> deleteFilesListByFileName(@RequestBody String[] fileNames);

	/**
	 * 发送系统邮件
	 *
	 * @param mail 邮件信息
	 * @return 发送结果
	 */
	@PostMapping(value = "/resource/mail/send")
	RestResult<?> sendSystemMail(Mail mail);

}
