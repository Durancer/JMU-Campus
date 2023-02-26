package com.xueyu.user.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 活动相关定时任务
 *
 * @author durance
 */
@Configuration
public class ActivityScheduleTask {

	@Scheduled(cron = "0 0 13 * * *")
	public void updateTrafficVoucherTime() {

	}

}
