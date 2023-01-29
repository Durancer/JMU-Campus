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
import java.util.HashMap;
import java.util.Map;

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
	public RestResult<Map<String,String>> uploadBlogImage(MultipartFile file) throws MinioException, IOException {
		log.debug("uploadFile, fileName->{}", file.getOriginalFilename());
		String imageName = minioService.upload(file, "image");
		Map<String, String> restMap = new HashMap<>();
		restMap.put("fileName",imageName);
		return RestResult.ok(restMap);
	}

	/**
	 * 根据文件名删除文件
	 *
	 * @param fileName 文件名
	 * @return 删除结果
	 * @throws MinioException
	 */
	@PostMapping("/delete")
	public RestResult<?> deleteFile(String fileName) throws MinioException{
		log.debug("deleteFile, fileName->{}", fileName);
		minioService.removeFile(fileName, "image");
		return RestResult.ok();
	}

}
