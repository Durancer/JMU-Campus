package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.UserViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author durance
 */
@Service
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements UserViewService {

	@Override
	public List<UserSimpleVO> getUserInfoList(List<Integer> userIdList) {
		List<UserView> userViews = query().getBaseMapper().selectBatchIds(userIdList);
		List<UserSimpleVO> userSimpleVOList = new ArrayList<>();
		for (UserView userView : userViews) {
			UserSimpleVO userSimpleVO = new UserSimpleVO();
			BeanUtils.copyProperties(userView, userSimpleVO);
			userSimpleVOList.add(userSimpleVO);
		}
		return userSimpleVOList;
	}

	@Override
	public Map<Integer, UserSimpleVO> getUserInfoListById(List<Integer> userIdList) {
		List<UserSimpleVO> userInfoList = getUserInfoList(userIdList);
		Map<Integer, UserSimpleVO> userInfo = new HashMap<>(userIdList.size());
		for (UserSimpleVO userSimpleVO : userInfoList) {
			userInfo.put(userSimpleVO.getId(), userSimpleVO);
		}
		return userInfo;
	}

	@Override
	public UserView getUserInfo(Integer userId) {
		return query().getBaseMapper().selectById(userId);
	}

	@Override
	public Map<Integer, List<UserSimpleVO>> getUserInfoListByGroup(Map<Integer, List<Integer>> userGroupIds) {
		// 创建响应体
		Map<Integer, List<UserSimpleVO>> result = new HashMap<>(userGroupIds.size());
		for (Map.Entry<Integer, List<Integer>> next : userGroupIds.entrySet()) {
			List<UserSimpleVO> userInfoList = getUserInfoList(next.getValue());
			result.put(next.getKey(), userInfoList);
		}
		return result;
	}

}
