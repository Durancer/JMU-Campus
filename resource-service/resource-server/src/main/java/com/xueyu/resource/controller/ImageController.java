package com.xueyu.resource.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.service.ImageService;
import com.xueyu.resource.util.FileCheckUtil;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 上传文件相关接口
 *
 * @author durance
 */
@Slf4j
@RestController
@RequestMapping("/resource")
public class ImageController {

	@Resource
	ImageService imageService;

	/**
	 * 上传图片
	 *
	 * @param file 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/image")
	public RestResult<Map<String, String>> uploadImageFile(MultipartFile file) throws MinioException, IOException {
		log.info("uploadFile, fileName->{}", file.getOriginalFilename());
		// 判断图片后缀是否符合规范
		if (!FileCheckUtil.checkImageSuffix(Objects.requireNonNull(file.getOriginalFilename()))) {
			return RestResult.fail("不支持的图片格式");
		}
		// todo 将不同模块的文件/图片存储资源使用不同的桶存储，并抽离出常量载入
		String imageName = imageService.upload(file, "image");
		Map<String, String> restMap = new HashMap<>(2);
		restMap.put("fileName", imageName);
		return RestResult.ok(restMap);
	}

	/**
	 * 上传多张图片
	 *
	 * @param files 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/images")
	public RestResult<Map<String, List<String>>> uploadImageFile(MultipartFile[] files) throws MinioException, IOException {
		log.info("uploadFile, 存储 {} 张图片", files.length);
		// 判断图片后缀是否符合规范
		for (MultipartFile file : files) {
			if (!FileCheckUtil.checkImageSuffix(Objects.requireNonNull(file.getOriginalFilename()))) {
				return RestResult.fail("包含不支持的图片格式");
			}
		}
		List<String> images = imageService.uploadFiles(files, "image");
		Map<String, List<String>> restMap = new HashMap<>(2);
		restMap.put("fileNames", images);
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
	public RestResult<?> deleteFile(@RequestBody String fileName) throws MinioException {
		log.debug("deleteFile, fileName->{}", fileName);
		imageService.removeFile(fileName, "image");
		return RestResult.ok();
	}

	/**
	 * 根据文件名批量删除文件
	 *
	 * @param fileNames 文件名
	 * @return 删除结果
	 * @throws MinioException
	 */
	@PostMapping("/delete/list")
	public RestResult<?> deleteFile(@RequestBody String[] fileNames) throws MinioException {
		log.debug("deleteFiles, file nums->{}", fileNames.length);
		imageService.removeFileList(fileNames, "image");
		return RestResult.ok();
	}

}
