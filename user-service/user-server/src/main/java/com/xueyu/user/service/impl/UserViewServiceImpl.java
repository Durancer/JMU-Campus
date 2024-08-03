package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.UserGeneral;
import com.xueyu.user.pojo.vo.UserGeneralVO;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.request.UserQueryRequest;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.UserViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author durance
 */
@Service
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements UserViewService {

	@Resource
	UserGeneralMapper userGeneralMapper;

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
		if(CollectionUtils.isEmpty(userIdList)){
			return null;
		}
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
	public Map<Integer, List<UserSimpleVO>> getUserInfoListByGroup(Map<String, List<Integer>> userGroupIds) {
		// 创建响应体
		Map<Integer, List<UserSimpleVO>> result = new HashMap<>(userGroupIds.size());
		for (Map.Entry<String, List<Integer>> next : userGroupIds.entrySet()) {
			List<UserSimpleVO> userInfoList = getUserInfoList(next.getValue());
			// 由于网络 Feign 网络传输只能传送 String类型，所以进行一次类型转化
			System.out.println(next.getKey());
			result.put(Integer.parseInt(next.getKey()), userInfoList);
		}
		return result;
	}

	@Override
	public UserGeneralVO getUserGeneralInfo(Integer userId) {
		// 查询除对应内容
		UserView userInfo = getUserInfo(userId);
		UserGeneral userGeneral = userGeneralMapper.selectById(userId);
		UserGeneralVO userGeneralVO = new UserGeneralVO();
		userGeneralVO.setUserData(userGeneral);
		userGeneralVO.setUserInfo(userInfo);
		return userGeneralVO;
	}

	@Override
	public List<UserView> getUserListBySearch(String username) {
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(UserView::getUsername, username).or().like(UserView::getNickname, username);
		return query().getBaseMapper().selectList(wrapper);
	}

	@Override
	public ListVO<UserView> getUserListPage(UserQueryRequest request) {
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		if (Objects.nonNull(request.getSex())){
			wrapper.eq(UserView::getSex, request.getSex());
		}
		if (Objects.nonNull(request.getId())){
			wrapper.eq(UserView::getId, request.getId());
		}
		if (Objects.nonNull(request.getPhone())){
			wrapper.eq(UserView::getPhone, request.getPhone());
		}
		if (Objects.nonNull(request.getNickname())){
			wrapper.like(UserView::getNickname, request.getNickname());
		}
		if (Objects.nonNull(request.getUsername())){
			wrapper.like(UserView::getUsername, request.getUsername());
		}
		if (Objects.nonNull(request.getEmail())){
			wrapper.eq(UserView::getEmail, request.getEmail());
		}
		if (Objects.nonNull(request.getCreateTime())){
			wrapper.ge(UserView::getCreateTime, request.getCreateTime());
		}
		wrapper.ne(UserView::getId, 0);
		IPage<UserView> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		ListVO<UserView> result = new ListVO<>();
		BeanUtils.copyProperties(page, result);
		return result;
	}
}
