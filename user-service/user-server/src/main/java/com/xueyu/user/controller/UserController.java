package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.pojo.bo.Mail;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.vo.UserGeneralVO;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.UserService;
import com.xueyu.user.service.UserViewService;
import com.xueyu.user.service.impl.MailServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

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

	@Resource
	UserViewService userViewService;

	/**
	 * 获取单个用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("detail")
	public RestResult<UserView> getUserDetailInfo(@RequestParam Integer userId) {
		return RestResult.ok(userViewService.getUserInfo(userId));
	}

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

	/**
	 * 获取用户统计数据接口
	 *
	 * @param userId 用户id
	 * @param otherUserId 其它用户id
	 * @return 用户统计数据
	 */
	@GetMapping("general")
	public RestResult<UserGeneralVO> getUserGeneralInfo(@RequestHeader(required = false) Integer userId, Integer otherUserId){
		if(otherUserId != null){
			return RestResult.ok(userViewService.getUserGeneralInfo(otherUserId));
		}
		if(userId == null){
			throw new UserException("参数异常");
		}
		return RestResult.ok(userViewService.getUserGeneralInfo(userId));
	}

}
