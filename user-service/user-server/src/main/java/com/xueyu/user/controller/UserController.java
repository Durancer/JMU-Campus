package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author durance
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Resource
	UserService userService;

	/**
	 * 注册用户
	 *
	 * @param user 用户信息
	 * @return 注册结果
	 */
	@PostMapping("register")
	public RestResult<?> registerUser(User user) {
		if (userService.registerUser(user)) {
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
	public RestResult<Map<String, String>> loginUser(User user) {
		String token = userService.loginUser(user);
		if (token == null) {
			return new RestResult<>(false, "账号或密码错误");
		}
		Map<String, String> resMap = new HashMap<>(1);
		resMap.put("token", token);
		return RestResult.ok(resMap, "登录成功");
	}

}
