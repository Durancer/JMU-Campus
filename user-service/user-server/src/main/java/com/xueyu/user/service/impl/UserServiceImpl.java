package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.user.mapper.UserMapper;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.service.UserService;
import com.xueyu.user.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author durance
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	UserMapper userMapper;

	@Override
	public Boolean registerUser(User user) {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getPhone, user.getPhone()).or().eq(User::getUsername, user.getUsername());
		User check = userMapper.selectOne(wrapper);
		if (check != null) {
			return false;
		}
		String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpw);
		userMapper.insert(user);
		return true;
	}

	@Override
	public String loginUser(User user) {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getUsername, user.getUsername());
		User check = userMapper.selectOne(wrapper);
		boolean checkpw = BCrypt.checkpw(user.getPassword(), check.getPassword());
		if (!checkpw) {
			return null;
		}
		return JwtUtil.createJwt();
	}

}
