package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.domain.Mail;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.service.UserService;
import com.xueyu.user.service.impl.MailServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import java.util.Map;

/**
 * @author durance
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Resource
	UserService userService;

	@Resource
	MailServiceImpl mailService;

	/**
	 * 注册用户
	 *
	 * @param user 用户信息
	 * @return 注册结果
	 */
	@PostMapping("register")
	public RestResult<?> registerUser(User user, @NotNull Integer idencode) {
		if (userService.registerUser(user, idencode)) {
			return RestResult.ok(null, "注册成功");
		}
		return RestResult.fail("已存在相同的的用户名或电话");
	}

	/**
	 * 用户登录
	 *
	 * @param user 用户信息
	 * @return 登录结果
	 */
	@GetMapping("login")
	public RestResult<Map<String, Object>> loginUser(User user) {
		Map<String, Object> result = userService.loginUser(user);
		if (result == null) {
			return new RestResult<>(false, "账号或密码错误");
		}
		return RestResult.ok(result, "登录成功");
	}

	/**
	 * 使用邮箱验证码登录
	 *
	 * @param email    用户邮箱
	 * @param idencode 验证码
	 * @return 登录结果
	 */
	@GetMapping("login/email")
	public RestResult<Map<String, Object>> loginUserByEmail(@Email String email, @NotNull Integer idencode) {
		Map<String, Object> result = userService.loginUserByCode(email, idencode);
		return RestResult.ok(result, "登录成功");
	}

	/**
	 * 小程序授权登录用户
	 *
	 * @param user 用户信息
	 * @param code 前端接口code
	 * @return token和用户信息
	 */
	@PostMapping("auth")
	public RestResult<Map<String, Object>> authUserByminApp(User user, String code) {
		return RestResult.ok(userService.authUserByMinApp(user, code));
	}

	/**
	 * 发送邮箱验证码
	 *
	 * @param email 用户邮箱
	 * @return 发送结果
	 */
	@PostMapping("send/code")
	public RestResult<?> sendUserMail(String email) {
		Mail mail = new Mail();
		mail.setTo(email);
		mailService.sendVerificationCode(mail);
		return RestResult.ok(null, "发送成功");
	}

}
