package com.xueyu.resource.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.service.MinioService;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传文件相关接口
 *
 * @author durance
 */
@Slf4j
@RestController
@RequestMapping("/resource")
public class FileController {

	@Autowired
	MinioService minioService;

	/**
	 * 上传图片
	 *
	 * @param file 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/image")
	public RestResult<String> uploadBlogImage(MultipartFile file) throws MinioException, IOException {
		log.debug("uploadFile, fileName->{}", file.getOriginalFilename());
		String imageName = minioService.upload(file, "image");
		return RestResult.ok(imageName);
	}

}
