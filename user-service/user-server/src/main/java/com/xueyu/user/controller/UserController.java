package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author durance
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Resource
	UserService userService;

	@PostMapping("register")
	public RestResult<?> registerUser(User user) {
		if (userService.registerUser(user)) {
			return RestResult.ok(null, "注册成功");
		}
		return RestResult.fail("已存在相同的的用户名或电话");
	}

	@GetMapping("login")
	public RestResult<Map<String, Object>> loginUser(User user) {
		Map<String, Object> result = userService.loginUser(user);
		if (result == null) {
			return new RestResult<>(false, "账号或密码错误");
		}
		return RestResult.ok(result, "登录成功");
	}

}
