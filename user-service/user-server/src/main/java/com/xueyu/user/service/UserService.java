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
	 * @param user     用户信息
	 * @param idencode 注册验证码
	 * @return 注解结果
	 */
	Boolean registerUser(User user, Integer idencode);

	/**
	 * 登录用户
	 *
	 * @param user 用户信息
	 * @return token和用户信息 登录失败返回 null
	 */
	Map<String, Object> loginUser(User user);

	/**
	 * 使用邮箱验证码登录
	 *
	 * @param email    用户邮箱
	 * @param idencode 传入验证码
	 * @return token和用户信息
	 */
	Map<String, Object> loginUserByCode(String email, Integer idencode);

}
