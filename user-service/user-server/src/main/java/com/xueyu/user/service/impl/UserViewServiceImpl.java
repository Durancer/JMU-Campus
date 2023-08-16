package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.mapper.UserItemViewMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.UserGeneral;
import com.xueyu.user.pojo.vo.UserGeneralVO;
import com.xueyu.user.pojo.vo.UserItemList;
import com.xueyu.user.pojo.vo.UserItemView;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.UserViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author durance
 */
@Service
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements UserViewService {

	@Resource
	UserItemViewMapper userItemViewMapper;

	@Resource
	UserViewMapper userViewMapper;

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
	public UserItemList getUserStuff(Integer userId) {
		LambdaQueryWrapper<UserItemView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserItemView::getUserId, userId);
		List<UserItemView> userItemViews = userItemViewMapper.selectList(wrapper);
		UserView userView = userViewMapper.selectById(userId);
		// 创建返回对象，封装属性
		UserItemList userItemList = new UserItemList();
		BeanUtils.copyProperties(userItemList, userView);
		userItemList.setUserItemList(userItemViews);
		return userItemList;
	}

	@Override
	public List<UserItemList> getUserListStuff(List<Integer> userIds) {
		// 查出所有用户信息
		LambdaQueryWrapper<UserView> userWrapper = new LambdaQueryWrapper<>();
		userWrapper.in(UserView::getId, userIds);
		List<UserView> userViews = userViewMapper.selectList(userWrapper);
		// 查出所有用户所拥有的物品信息
		LambdaQueryWrapper<UserItemView> itemWrapper = new LambdaQueryWrapper<>();
		itemWrapper.in(UserItemView::getUserId, userIds);
		List<UserItemView> userItemViews = userItemViewMapper.selectList(itemWrapper);
		// 将查出来的商品进行分组
		Map<Integer, List<UserItemView>>  itemsMap = new HashMap<>();
		for (UserItemView userItemView : userItemViews) {
			List<UserItemView> originItems = itemsMap.getOrDefault(userItemView.getUserId(), new ArrayList<>());
			originItems.add(userItemView);
			itemsMap.put(userItemView.getUserId(), originItems);
		}
		// 创建总响应体
		List<UserItemList> res = new ArrayList<>();
		for(Integer userId : userIds){
			// 创建单个用户的物品响应体
			UserItemList userItemList = new UserItemList();
			// 查找用户信息并赋值
			for(UserView userView : userViews){
				if(userView.getId().equals(userId)){
					BeanUtils.copyProperties(userItemList, userView);
					break;
				}
			}
			// 赋值商品信息
			userItemList.setUserItemList(itemsMap.get(userId));
			res.add(userItemList);
		}
		return res;
	}

	@Override
	public UserGeneralVO getUserGeneralInfo(Integer userId) {
		// 查询除对应内容
		UserView userInfo = getUserInfo(userId);
		UserGeneral userGeneral = userGeneralMapper.selectById(userId);
		UserGeneralVO userGeneralVO = new UserGeneralVO();
		userGeneralVO.setUserGeneral(userGeneral);
		userGeneralVO.setUserView(userInfo);
		return userGeneralVO;
	}
}
