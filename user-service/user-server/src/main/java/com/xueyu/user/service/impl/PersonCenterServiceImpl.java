package com.xueyu.user.service.impl;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.client.ResourceClient;
import com.xueyu.resource.sdk.constant.MailConstant;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.UserMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.enums.UserGenderEnum;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.PersonCenterService;
import com.xueyu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.xueyu.resource.sdk.constant.MailConstant.CODE_KEY_PREFIX;

/**
 * @author durance
 */
@Service
@Slf4j
public class PersonCenterServiceImpl implements PersonCenterService {

	@Resource
	UserMapper userMapper;

	@Resource
	UserViewMapper userViewMapper;

	@Resource
	ResourceClient resourceClient;

	@Resource
	UserService userService;

	@Override
	public UserView updateUserInfo(User user) {
		log.info("用户id -> {} , 更新个人信息, 更新内容报文 -> {}", user.getId(), user);
		// 不合法参数拦截
		if (user.getPassword() != null ||
				user.getAvatar() != null ||
				user.getUsername() != null ||
				user.getCreateTime() != null ||
				user.getEmail() != null) {
			throw new UserException("不合法的参数传入");
		}
		// 如果性别参数不为空的情况下，做判断 todo 封装枚举判断
		if (user.getSex() != null && !(UserGenderEnum.HIDE.getCode().equals(user.getSex()) ||
				UserGenderEnum.BOY.getCode().equals(user.getSex()) ||
				UserGenderEnum.GIRL.getCode().equals(user.getSex()))) {
			throw new UserException("不合规的用户性别参数");
		}
		user.setUpdateTime(new Date());
		int i = userMapper.updateById(user);
		UserView userView = userViewMapper.selectById(user.getId());
		if (i != 1) {
			throw new UserException("不存在的用户id");
		}
		return userView;
	}

	@Override
	public String updateUserAvatar(Integer userId, MultipartFile file) {
		log.info("用户 id -> {} , 更新用户头像", userId);
		User check = userMapper.selectById(userId);
		if (check == null) {
			throw new UserException("不存在的用户id");
		}
		// 进行头像保存，获取文件名
		RestResult<Map<String, String>> uploadResult = resourceClient.uploadImageFile(file);
		if (!uploadResult.getStatus()){
			throw new UserException(uploadResult.getMessage());
		}
		Map<String, String> resDate = uploadResult.getData();
		String fileName = resDate.get("fileName");

		// 如果不为默认头像，删除原来的头像文件 todo 转为默认头像配置
		String originName = check.getAvatar();
		if (!("default.jpg".equals(originName) ||
				"default_boy.png".equals(originName) ||
				"default_girl.png".equals(originName)
		)) {
			resourceClient.deleteFileByFileName(originName);
		}
		User user = new User();
		user.setId(userId);
		user.setAvatar(fileName);
		user.setUpdateTime(new Date());
		userMapper.updateById(user);
		UserView userView = userViewMapper.selectById(userId);
		return userView.getAvatarUrl();
	}

	@Override
	public Boolean updateUserPasswordByEmail(User user, Integer idencode) {
		String key = CODE_KEY_PREFIX + user.getEmail();
		// 核对验证码是否正确
		userService.verifyIdencode(idencode, key);
		// 不合法参数拦截
		if (user.getAvatar() != null ||
				user.getUsername() != null ||
				user.getCreateTime() != null) {
			throw new UserException("不合法的参数传入");
		}
		User check = userMapper.selectById(user.getId());
		if(!check.getEmail().equals(user.getEmail())){
			throw new UserException("操作用户非邮箱拥有者用户");
		}
		String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpw);
		log.info("用户id ->{}, 邮箱 -> {} 修改了用户密码", user.getId(), user.getEmail());
		return true;
	}

}
