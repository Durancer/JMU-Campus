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

	/**
	 * 向用户发送验证码邮件
	 *
	 * @param email 用户邮箱
	 */
	void sendUserVerifyCode(String email);

	/**
	 * 效验 验证码
	 *
	 * @param idencode 用户传入验证码
	 * @param key 系统生成验证码
	 */
	void verifyIdencode(Integer idencode, String key);

	/**
	 * 拉黑用户
	 *
	 * @param userId 用户id
	 * @param time 拉黑时长，分钟
	 * @return 拉黑结果
	 */
	boolean blackUser(Integer userId, Integer time);

}
