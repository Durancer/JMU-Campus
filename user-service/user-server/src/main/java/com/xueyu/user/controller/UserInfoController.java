package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.sdk.pojo.vo.UserDetail;
import com.xueyu.user.service.UserViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务内部接口层
 *
 * @author durance
 */
@RequestMapping("private/user")
@RestController
public class UserInfoController {

	@Resource
	UserViewService userViewService;

	/**
	 * 获取单个用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("detail")
	public RestResult<UserDetail> getUserDetailInfo(Integer userId) {
		return RestResult.ok(userViewService.getUserInfo(userId));
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息
	 */
	@GetMapping("detail/list")
	public RestResult<List<UserDetail>> getUserDeatilInfoList(List<Integer> userIds) {
		return RestResult.ok(userViewService.getUserInfoList(userIds));
	}

}
