package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.mapper.UserMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.domain.UserGeneral;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.UserService;
import com.xueyu.user.util.JwtUtil;
import com.xueyu.user.util.WxOpenUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.xueyu.user.constant.MailConstant.CODE_KEY_PREFIX;

/**
 * @author durance
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	UserMapper userMapper;

	@Resource
	UserGeneralMapper userGeneralMapper;

	@Resource
	UserViewMapper userViewMapper;

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	@Override
	public Boolean registerUser(User user, Integer idencode) {
		// 判断验证码是否相同
		String key = CODE_KEY_PREFIX + user.getEmail();
		Integer authcode = redisTemplate.opsForValue().get(key);
		if (authcode == null) {
			throw new UserException("验证码已过期，请重新发送");
		}
		if (!authcode.equals(idencode)) {
			throw new UserException("验证码错误");
		}
		if (user.getUsername() == null || user.getPassword() == null) {
			throw new UserException("账号和密码不能为空");
		}
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getPhone, user.getPhone()).or().eq(User::getUsername, user.getUsername());
		User check = userMapper.selectOne(wrapper);
		// 不为null说明已经创建改用户
		if (check != null) {
			return false;
		}
		Timestamp time = new Timestamp(System.currentTimeMillis());
		user.setCreateTime(time);
		String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpw);
		userMapper.insert(user);
		// 创建数据统计表行数据
		UserGeneral userGeneral = new UserGeneral();
		userGeneral.setUserId(user.getId());
		userGeneralMapper.insert(userGeneral);
		return true;
	}

	@Override
	public Map<String, Object> loginUser(User user) {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getUsername, user.getUsername());
		User check = userMapper.selectOne(wrapper);
		// 核对密码是否正确
		boolean checkpw = BCrypt.checkpw(user.getPassword(), check.getPassword());
		if (!checkpw) {
			return null;
		}
		// 签发jwt，设置用户id
		Map<String, Object> res = new HashMap<>(2);
		String token = JwtUtil.createJwt("userId", check.getId());
		res.put("token", token);
		UserView userView = userViewMapper.selectById(check.getId());
		res.put("userInfo", userView);
		return res;
	}

	@Override
	public Map<String, Object> loginUserByCode(String email, Integer idencode) {
		// 查询用户信息
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserView::getEmail, email);
		UserView userView = userViewMapper.selectOne(wrapper);
		if (userView == null) {
			throw new UserException("不存在该邮箱账号");
		}
		// 查询验证码是否正确
		String key = CODE_KEY_PREFIX + email;
		Integer checkCode = redisTemplate.opsForValue().get(key);
		if (checkCode == null || !checkCode.equals(idencode)) {
			throw new UserException("验证码错误");
		}
		// 签发jwt
		String token = JwtUtil.createJwt("userId", userView.getId());
		// 封装响应体
		Map<String, Object> res = new HashMap<>(2);
		res.put("token", token);
		res.put("userInfo", userView);
		redisTemplate.delete(key);
		return res;
	}

	@Override
	public Map<String, Object> authUserByMinApp(User user, String code) {
		if (code == null) {
			throw new UserException("code码不能为空");
		}
		// 获取用户openid
		String openid = WxOpenUtil.getOpenid(code);
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserView::getOpenid, openid);
		UserView userView = userViewMapper.selectOne(wrapper);
		Map<String, Object> res = new HashMap<>(2);
		if (userView != null) {
			// 不为空则已经注册过，签发jwt
			String token = JwtUtil.createJwt("userId", userView.getId());
			res.put("token", token);
			res.put("userInfo", userView);
			return res;
		}
		// 否则，执行注册操作
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userMapper.insert(user);
		UserView userInfo = userViewMapper.selectById(user.getId());
		// 签发jwt，设置用户id
		String token = JwtUtil.createJwt("userId", userView.getId());
		res.put("token", token);
		res.put("userInfo", userInfo);
		return res;
	}

}
