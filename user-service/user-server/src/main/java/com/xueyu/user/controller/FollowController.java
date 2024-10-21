package com.xueyu.user.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.request.FollowGetRequest;
import com.xueyu.user.request.FollowRequest;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.ActivityService;
import com.xueyu.user.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 关注控制层
 *
 * @author durance
 */
@RestController
@RequestMapping("user/follow")
public class FollowController {

	@Resource
	FollowService followService;

	/**
	 * 关注 | 取消关注用户
	 *
	 * @param userId 用户id
	 * @return 抢送结果
	 */
	@PostMapping
	public RestResult<Boolean> getPostTrafficVoucher(@RequestHeader Integer userId, FollowRequest request) {
		request.setUserId(userId);
		Boolean res = followService.followUser(request);
		if (res) {
			return RestResult.ok(true, "操作成功");
		}
		return RestResult.ok(false, "操作失败");
	}

	/**
	 * 获取关注列表
	 *
	 * @param request req
	 * @return 抢送结果
	 */
	@GetMapping("list")
	public RestResult<ListVO<UserSimpleVO>> getFollowList(FollowGetRequest request) {
		return RestResult.ok(followService.getFollowList(request));
	}

	/**
	 * 获取粉丝列表
	 *
	 * @param request req
	 * @return 抢送结果
	 */
	@GetMapping("fans/list")
	public RestResult<ListVO<UserSimpleVO>> getFansList(FollowGetRequest request) {
		return RestResult.ok(followService.getFansList(request));
	}

}
