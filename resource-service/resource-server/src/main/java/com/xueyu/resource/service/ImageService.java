package com.xueyu.resource.service;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author durance
 */
public interface ImageService {

	/**
	 * 判断bucket是否存在
	 *
	 * @param name bucket名
	 * @return 是否存在
	 * @throws MinioException 连接失败
	 */
	boolean existBucket(String name) throws MinioException;

	/**
	 * 上传文件，请手动关闭inputStream
	 *
	 * @param filePath    文件名，包含上传路径
	 * @param bucketName  桶名称
	 * @param inputStream 输入流
	 * @param objectSize  文件大小
	 * @param partSize    分段大小
	 * @param contentType 文件类型
	 * @throws MinioException 上传失败
	 */
	void upload(String filePath, String bucketName, InputStream inputStream, long objectSize, long partSize, String contentType) throws MinioException;

	/**
	 * 上传文件
	 *
	 * @param multipartFile 文件
	 * @param bucketName    桶名称
	 * @return 上传生成的文件名
	 * @throws MinioException minio异常
	 * @throws IOException    字节流为空
	 */
	String upload(MultipartFile multipartFile, String bucketName) throws MinioException, IOException;

	/**
	 * 上传多个文件
	 *
	 * @param multipartFile 文件
	 * @param bucketName    桶名称
	 * @return 上传生成的文件名
	 * @throws MinioException minio异常
	 * @throws IOException    字节流为空
	 */
	List<String> uploadFiles(MultipartFile[] multipartFile, String bucketName) throws MinioException, IOException;

	/**
	 * 上传文件，请手动关闭inputStream
	 *
	 * @param multipartFile 文件
	 * @param filePath      文件名，包含上传路径
	 * @param bucketName    桶名称
	 * @throws MinioException 上传失败
	 * @throws IOException    文件为空
	 */
	void upload(MultipartFile multipartFile, String filePath, String bucketName) throws MinioException, IOException;

	/**
	 * 下载文件,使用response
	 *
	 * @param filePath   文件路径
	 * @param bucketName 桶名称
	 * @param response   输出的响应体
	 * @throws MinioException 文件不存在
	 * @throws IOException    response为空
	 */
	void download(String filePath, String bucketName, HttpServletResponse response) throws MinioException, IOException;

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param filePath   文件名(包括路径)
	 * @param bucketName 桶名称
	 * @return 输入流，直接复制给响应体就可以了
	 * @throws MinioException 获取失败，文件可能不存在
	 */
	InputStream download(String filePath, String bucketName) throws MinioException;

	/**
	 * 删除文件
	 *
	 * @param fileName   文件名
	 * @param bucketName 桶名称
	 * @throws MinioException 文件不存在
	 */
	void removeFile(String fileName, String bucketName) throws MinioException;

	/**
	 * 批量删除文件
	 *
	 * @param fileNames  文件名列表
	 * @param bucketName 桶名称
	 * @throws MinioException
	 */
	void removeFileList(String[] fileNames, String bucketName) throws MinioException;

	/**
	 * 获取桶内文件列表
	 *
	 * @param bucketName 桶名称
	 * @throws Exception 获取失败，可能是桶不存在
	 */
	void getObjectList(String bucketName) throws Exception;

}
