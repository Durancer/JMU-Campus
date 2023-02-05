package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserDetail;
import com.xueyu.user.service.UserViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durance
 */
@Service
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements UserViewService {

	@Override
	public List<UserDetail> getUserInfoList(List<Integer> userIdList) {
		List<UserView> userViews = query().getBaseMapper().selectBatchIds(userIdList);
		List<UserDetail> userDetails = new ArrayList<>();
		for (UserView userView : userViews) {
			UserDetail userDetail = new UserDetail();
			BeanUtils.copyProperties(userDetail, userView);
			userDetails.add(userDetail);
		}
		return userDetails;
	}

	@Override
	public UserDetail getUserInfo(Integer userId) {
		UserView userView = query().getBaseMapper().selectById(userId);
		UserDetail userDetail = new UserDetail();
		BeanUtils.copyProperties(userDetail, userView);
		return userDetail;
	}

}
