package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;

import java.util.List;
import java.util.Map;

/**
 * 用户数据业务层
 *
 * @author durance
 */
public interface UserViewService extends IService<UserView> {

	/**
	 * 获取用户信息列表
	 *
	 * @param userIdList 用户id集合
	 * @return 用户数据
	 */
	List<UserSimpleVO> getUserInfoList(List<Integer> userIdList);

	/**
	 * 按照用户id获取用户列表
	 *
	 * @param userIdList 用户id列表
	 * @return 用户 id | 用户信息
	 */
	Map<Integer, UserSimpleVO> getUserInfoListById(List<Integer> userIdList);

	/**
	 * 获取单个用户信息
	 *
	 * @param userId 用户id
	 * @return 用户数据
	 */
	UserView getUserInfo(Integer userId);

}
