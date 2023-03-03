package com.xueyu.user.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

import static com.xueyu.user.constant.TrafficVoucherContants.STOCK_KEY;
import static com.xueyu.user.constant.TrafficVoucherContants.VOUCHER_NUM;

/**
 * 活动相关定时任务
 *
 * @author durance
 */
@Configuration
@Slf4j
public class ActivityScheduleTask {

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	/**
	 * 每周日中午13点进行预热
	 * 在cron表达式中 1表示星期日 7表示星期六，位于倒数第二位
	 */
	@Scheduled(cron = "0 0 13 * 1 *")
	public void updateTrafficVoucherTime() {
		log.info("进行预热流量券抢送数据");
		// 进行缓存预热
		redisTemplate.opsForValue().set(STOCK_KEY, VOUCHER_NUM);
	}

	/**
	 * 每周日中午14点截止抢送时间
	 */
	@Scheduled(cron = "0 0 14 * 1 *")
	public void trafficVoucherEnd() {
		log.info("删除缓存截止抢送");
		// 进行缓存预热
		redisTemplate.delete(STOCK_KEY);
	}

}
