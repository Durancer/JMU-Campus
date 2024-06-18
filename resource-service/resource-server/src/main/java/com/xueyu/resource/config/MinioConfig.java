package com.xueyu.resource.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio配置类
 *
 * @author durance
 */
@Configuration
public class MinioConfig {

	@Resource
    MinioProperties minioProperties;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder().endpoint(minioProperties.getUrl()).credentials(minioProperties.getUsername(), minioProperties.getPassword()).build();
	}

}
