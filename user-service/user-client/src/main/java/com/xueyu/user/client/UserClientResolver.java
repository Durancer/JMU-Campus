package com.xueyu.user.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.sdk.pojo.vo.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author durance
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	@Override
	public RestResult<UserDetail> getUserInfo(Integer userId) {
		log.error("user 服务异常：getUserInfo 请求失败");
		return new RestResult<>(503, "fail");
	}

	@Override
	public RestResult<List<UserDetail>> getUserDeatilInfoList(List<Integer> userIds) {
		log.error("user 服务异常：getUserDeatilInfoList 请求失败");
		return new RestResult<>(503, "fail");
	}

}
