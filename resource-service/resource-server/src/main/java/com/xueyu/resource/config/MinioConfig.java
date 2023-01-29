package com.xueyu.resource.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置类
 */
@Configuration
public class MinioConfig {

	@Autowired
	MinioPerproties minioPerproties;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder().endpoint(minioPerproties.getUrl()).credentials(minioPerproties.getUsername(), minioPerproties.getPassword()).build();
	}

}
