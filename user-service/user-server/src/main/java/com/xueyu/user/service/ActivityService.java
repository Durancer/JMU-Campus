package com.xueyu.user.service;

/**
 * 活动相关业务
 *
 * @author durance
 */
public interface ActivityService {

	/**
	 * 抢送流量券
	 *
	 * @param userId 用户id
	 * @return 抢送结果
	 */
	Boolean getTrafficVoucher(Integer userId);

}
