package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.service.ActivityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动控制层
 *
 * @author durance
 */
@RestController
@RequestMapping("user/activity")
public class ActivityController {

	@Resource
	ActivityService activityService;

	/**
	 * 抢送流量券
	 *
	 * @param userId 用户id
	 * @return 抢送结果
	 */
	@PostMapping("traffic/voucher")
	public RestResult<Boolean> getPostTrafficVoucher(@RequestHeader Integer userId) {
		Boolean res = activityService.getTrafficVoucher(userId);
		if (res) {
			return RestResult.ok(true, "成功抢到");
		}
		return RestResult.ok(false, "已经抢完或已经抢过");
	}

}
