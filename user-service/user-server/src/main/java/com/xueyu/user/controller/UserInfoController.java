package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.UserViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public RestResult<UserView> getUserDetailInfo(@RequestParam Integer userId) {
		return RestResult.ok(userViewService.getUserInfo(userId));
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息
	 */
	@GetMapping("detail/list")
	public RestResult<List<UserSimpleVO>> getUserDeatilInfoList(@RequestParam List<Integer> userIds) {
		return RestResult.ok(userViewService.getUserInfoList(userIds));
	}

	/**
	 * 批量获取用户信息响应map
	 *
	 * @param userIds 用户id列表
	 * @return 用户id | 用户信息
	 */
	@GetMapping("detail/map")
	public RestResult<Map<Integer, UserSimpleVO>> getUserDeatilInfoMap(@RequestParam List<Integer> userIds) {
		return RestResult.ok(userViewService.getUserInfoListById(userIds));
	}

	/**
	 * 根据传入id进行分组查询用户信息
	 *
	 * @param userGroupIds 分组id，key为分组id | value为该key所以对应的需要查询的用户id列表
	 * @return 分组用户信息
	 */
	@GetMapping("list/group")
	public RestResult<Map<Integer, List<UserSimpleVO>>> getUserInfoByGroup(@RequestParam HashMap<String, List<Integer>> userGroupIds) {
		return RestResult.ok(userViewService.getUserInfoListByGroup(userGroupIds));
	}

}
