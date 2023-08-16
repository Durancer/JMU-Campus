package com.xueyu.user.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.PersonCenterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用户个人中心相关接口
 *
 * @author durance
 */
@RestController
@RequestMapping("user/person")
public class PersonCenterController {

	@Resource
	PersonCenterService personCenterService;

	/**
	 * 更新用户头像接口
	 *
	 * @param userId 用户id
	 * @param file   头像文件
	 * @return 新头像路径
	 */
	@PostMapping("update/avatar")
	public RestResult<String> updateUserAvatar(@RequestHeader Integer userId, MultipartFile file) {
		if (file == null) {
			return RestResult.fail("上传头像不能为空");
		}
		// 存入并获取新头像路径
		String newAvatar = personCenterService.updateUserAvatar(userId, file);
		return RestResult.ok(newAvatar, "上传成功");
	}

	/**
	 * 更新用户信息
	 *
	 * @param user   用户信息
	 * @param userId 用户id
	 * @return 更新结果
	 */
	@PostMapping("update/userInfo")
	public RestResult<UserView> updateUserInfo(User user, @RequestHeader Integer userId) {
		user.setId(userId);
		UserView userView = personCenterService.updateUserInfo(user);
		return RestResult.ok(userView, "更新成功");
	}

}
