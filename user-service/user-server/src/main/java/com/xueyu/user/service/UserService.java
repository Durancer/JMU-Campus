package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.user.pojo.domain.User;

import java.util.Map;

/**
 * @author durance
 */
public interface UserService extends IService<User> {

	/**
	 * 注册用户
	 *
	 * @param user 用户信息
	 * @return 注解结果
	 */
	Boolean registerUser(User user);

	/**
	 * 登录用户
	 *
	 * @param user 用户信息
	 * @return token和用户信息 登录失败返回 null
	 */
	Map<String, Object> loginUser(User user);

	/**
	 * 小程序授权登录用户
	 *
	 * @param user 用户信息
	 * @param code 前端接口code
	 * @return token和用户信息
	 */
	Map<String, Object> authUserByMinApp(User user, String code);

}
