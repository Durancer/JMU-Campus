package com.xueyu.user.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 用户服务客户端
 *
 * @author durance
 */
@FeignClient(value = "user-server", fallback = UserClientResolver.class)
public interface UserClient {

	/**
	 * 获取单个用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("private/user/detail")
	RestResult<UserSimpleVO> getUserInfo(@RequestParam Integer userId);

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息
	 */
	@GetMapping("private/user/detail/list")
	RestResult<List<UserSimpleVO>> getUserDeatilInfoList(@RequestParam List<Integer> userIds);

	/**
	 * 按照用户id获取用户列表
	 *
	 * @param userIds 用户id列表
	 * @return 用户 id | 用户信息
	 */
	@GetMapping("private/user/detail/map")
	RestResult<Map<Integer, UserSimpleVO>> getUserDeatilInfoMap(@RequestParam List<Integer> userIds);

	/**
	 * 根据传入id进行分组查询用户信息
	 * 由于feign调用是通过http请求的，所以 map的key都需要为 String类型，否则相干服务启动会报错
	 *
	 * @param userGroupIds 分组id，key为分组id | value为该key所以对应的需要查询的用户id列表
	 * @return 分组用户信息
	 */
	@GetMapping("private/user/list/group")
	RestResult<Map<Integer, List<UserSimpleVO>>> getUserInfoByGroup(@RequestParam Map<String, List<Integer>> userGroupIds);

}
