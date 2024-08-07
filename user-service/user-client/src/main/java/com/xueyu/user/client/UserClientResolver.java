package com.xueyu.user.client;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.xueyu.common.core.enums.ResultTypeEnum.SERVICE_EXCEPTION;

/**
 * @author durance
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	@Override
	public RestResult<UserSimpleVO> getUserInfo(Integer userId) {
		log.error("user 服务异常：getUserInfo 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<List<UserSimpleVO>> getUserDeatilInfoList(List<Integer> userIds) {
		log.error("user 服务异常：getUserDeatilInfoList 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<Map<Integer, UserSimpleVO>> getUserDeatilInfoMap(List<Integer> userIds) {
		log.error("user 服务异常：getUserDeatilInfoMap 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<Map<Integer, List<UserSimpleVO>>> getUserInfoByGroup(Map<String, List<Integer>> userGroupIds) {
		log.error("user 服务异常：getUserInfoByGroup 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

}
