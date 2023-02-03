package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.service.UserService;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("register")
	public RestResult<?> registerUser(User user) {
		if (userService.registerUser(user)) {
			return RestResult.ok(null, "注册成功");
		}
		return RestResult.fail("已存在相同的的用户名或电话");
	}

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

	@GetMapping("test")
	public void test(@RequestHeader int userId) {
		System.out.println("用户id为：" + userId);
	}

}
