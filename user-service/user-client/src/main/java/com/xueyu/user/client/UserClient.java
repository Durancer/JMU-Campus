package com.xueyu.user.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.sdk.pojo.vo.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
	public RestResult<UserDetail> getUserInfo(Integer userId);

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息
	 */
	@GetMapping("private/user/detail/list")
	public RestResult<List<UserDetail>> getUserDeatilInfoList(List<Integer> userIds);

}
