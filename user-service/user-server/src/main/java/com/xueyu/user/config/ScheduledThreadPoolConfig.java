package com.xueyu.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 定时线程池配置类
 *
 * @author durance
 */
@Configuration
public class ScheduledThreadPoolConfig {

	/**
	 * 创建定时任务线程池 todo 根据阿里规约优化代码
	 *
	 * @return 线程池
	 */
	@Bean
	public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor() {
		return new ScheduledThreadPoolExecutor(10);
	}

}
