package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.sdk.pojo.vo.UserDetail;

import java.util.List;

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
	List<UserDetail> getUserInfoList(List<Integer> userIdList);

	/**
	 * 获取单个用户信息
	 *
	 * @param userId 用户id
	 * @return 用户数据
	 */
	UserDetail getUserInfo(Integer userId);

}
