package com.xueyu.resource.service.impl;

import com.xueyu.resource.service.ImageService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 阿杆
 */
@Component
public class ImageServiceImpl implements ImageService {

	@Resource
	private MinioClient minioClient;

	@Override
	public boolean existBucket(String name) throws MinioException {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
		} catch (Exception e) {
			throw new MinioException();
		}
	}

	@Override
	public void upload(String filePath, String bucketName, InputStream inputStream, long objectSize, long partSize, String contentType)
			throws MinioException {
		try {
			minioClient.putObject(PutObjectArgs
					.builder()
					.bucket(bucketName)
					.object(filePath)
					// 文件大小和分片大小，填-1默认为5Mib
					.stream(inputStream, objectSize, partSize)
					.contentType(contentType)
					.build());
			// 需要手动关闭输入流
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MinioException("文件上传异常");
		}
	}

	@Override
	public String upload(MultipartFile multipartFile, String bucketName) throws MinioException, IOException {
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			String fileName = createFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			this.upload(
					fileName,
					bucketName,
					inputStream,
					multipartFile.getSize(),
					-1,
					multipartFile.getContentType()
			);
			return fileName;
		}
	}

	@Override
	public void upload(MultipartFile multipartFile, String filePath, String bucketName) throws MinioException, IOException {
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			this.upload(
					filePath,
					bucketName,
					inputStream,
					multipartFile.getSize(),
					-1,
					multipartFile.getContentType()
			);
		}
	}

	/**
	 * 下载文件,使用response
	 *
	 * @param filePath   文件路径
	 * @param bucketName 桶名称
	 * @param response   输出的响应体
	 */
	@Override
	public void download(String filePath, String bucketName, HttpServletResponse response) throws MinioException, IOException {
		try (InputStream inputStream = this.download(filePath, bucketName)) {
			try (ServletOutputStream outputStream = response.getOutputStream()) {
				IOUtils.copy(inputStream, outputStream);
			}
		}
	}

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param filePath 文件名(包括路径)
	 */
	@Override
	public InputStream download(String filePath, String bucketName) throws MinioException {
		try {
			return minioClient.getObject(GetObjectArgs
					.builder()
					.bucket(bucketName)
					.object(filePath)
					.build());
		} catch (Exception e) {
			throw new MinioException("文件下载异常," + e.getMessage());
		}
	}

	@Override
	public void removeFile(String fileName, String bucketName) throws MinioException {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build()
			);
		} catch (Exception e) {
			throw new MinioException("文件删除异常," + e.getMessage());
		}
	}

	@Override
	public void getObjectList(String bucketName) throws Exception {
		Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
		for (Result<Item> result : results) {
			Item item = result.get();
			System.out.println(item.objectName());
		}
	}

	/**
	 * 生成随机文件名
	 *
	 * @param fileName 原文件名
	 * @return 新文件名
	 */
	public String createFileName(String fileName) {
		int idx = fileName.lastIndexOf(".");
		String extention = fileName.substring(idx);
		return UUID.randomUUID().toString().replace("-", "") + extention;
	}

}

