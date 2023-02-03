package com.xueyu.user.service;

import com.xueyu.user.pojo.domain.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人中心业务层
 *
 * @author durance
 */
public interface PersonCenterService {

	/**
	 * 修改用户各个信息
	 *
	 * @param user 用户信息
	 * @return 影响行数
	 */
	Boolean updateUserInfo(User user);

	/**
	 * 修改用户头像
	 *
	 * @param userId 用户id
	 * @param file   头像文件
	 * @return 新头像路径
	 */
	String updateUserAvatar(Integer userId, MultipartFile file);

}
