package com.xueyu.user.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.enums.ResultTypeEnum;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.client.ResourceClient;
import com.xueyu.resource.sdk.bo.Mail;
import com.xueyu.resource.sdk.constant.MailConstant;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.mapper.UserMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.domain.UserGeneral;
import com.xueyu.user.pojo.enums.UserGenderEnum;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.UserService;
import com.xueyu.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.xueyu.resource.sdk.constant.MailConstant.CODE_KEY_PREFIX;

/**
 * @author durance
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	UserMapper userMapper;

	@Resource
	UserGeneralMapper userGeneralMapper;

	@Resource
	UserViewMapper userViewMapper;

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	@Resource
	ResourceClient resourceClient;

	@Override
	public Boolean registerUser(User user, Integer idencode) {
		log.info("【用户注册入参】:{} ", user);
		// 判断验证码是否相同
		String key = CODE_KEY_PREFIX + user.getEmail();
		verifyIdencode(idencode, key);
		// 检查用户注册参数
		verifyUserInfo(user);
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getPhone, user.getPhone())
				.or().eq(User::getUsername, user.getUsername())
				.or().eq(User::getEmail, user.getEmail());
		User check = userMapper.selectOne(wrapper);
		// 不为null说明已经创建改用户
		if (check != null) {
			return false;
		}
		Timestamp time = new Timestamp(System.currentTimeMillis());
		user.setCreateTime(time);
		String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpw);
		// 判断头像
		if (user.getSex() == null) {
			user.setSex(UserGenderEnum.HIDE.getCode());
		}
		if (user.getSex().equals(UserGenderEnum.BOY.getCode())) {
			user.setAvatar("default_boy.png");
		} else if (user.getSex().equals(UserGenderEnum.GIRL.getCode())) {
			user.setAvatar("default_girl.png");
		}
		userMapper.insert(user);
		redisTemplate.delete(key);
		log.info("用户id -> {}, 邮箱 -> {}, 注册成功", user.getId(), user.getEmail());
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
		if (Objects.isNull(check)){
			throw new UserException("改账号不存在");
		}
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
		log.info("用户 id -> {}, username -> {} 进行了登录操作", check.getId(), check.getUsername());
		return res;
	}

	@Override
	public void sendUserVerifyCode(String email) {
		// 判断当前待发送邮箱是否已经有验证码
		String key = MailConstant.CODE_KEY_PREFIX + email;
		Integer code = redisTemplate.opsForValue().get(key);
		if (code != null) {
			throw new UserException("当前邮箱已经发送验证码");
		}
		// 生成随机 6位验证码
		int idenCode = (int) ((Math.random() * 9 + 1) * 100000);
		// 封装邮件内容
		String subject = "欢迎进入 i集大校园，快来遇见校友吧！";
		String content = "【i集大校园】您正在注册/登录 或操作 i集大校园，验证码：" + idenCode + ", 该验证码一分钟内有效，如非本人操作请勿将验证码给与他人。";
		redisTemplate.opsForValue().set(key, idenCode, 60, TimeUnit.SECONDS);
		// 发送邮件
		Mail mail = new Mail();
		mail.setTo(email);
		mail.setSubject(subject);
		mail.setText(content);
		RestResult<?> restResult = resourceClient.sendSystemMail(mail);
		if(!restResult.getCode().equals(ResultTypeEnum.SUCCESS.getCode())){
			// 如果发送失败删除redis验证码数据
			redisTemplate.delete(key);
			throw new UserException(restResult.getMessage());
		}
	}

	@Override
	public Map<String, Object> loginUserByCode(String email, Integer idencode) {
		// 查询验证码是否正确
		String key = CODE_KEY_PREFIX + email;
		// 效验验证码正确性
		verifyIdencode(idencode, key);
		// 查询用户信息
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserView::getEmail, email);
		UserView userView = userViewMapper.selectOne(wrapper);
		if (userView == null) {
			throw new UserException("不存在该邮箱账号");
		}
		// 签发jwt
		String token = JwtUtil.createJwt("userId", userView.getId());
		log.info("用户 id -> {}, email -> {} 使用邮箱进行了登录操作", userView.getId(), userView.getEmail());
		// 封装响应体
		Map<String, Object> res = new HashMap<>(2);
		res.put("token", token);
		res.put("userInfo", userView);
		redisTemplate.delete(key);
		return res;
	}

	/**
	 * 效验 验证码
	 *
	 * @param idencode 用户传入验证码
	 * @param key 系统生成验证码
	 */
	public void verifyIdencode(Integer idencode, String key){
		// 系统生成的验证码
		Integer checkCode = redisTemplate.opsForValue().get(key);
		if (checkCode == null) {
			throw new UserException("验证码已过期，请重新发送");
		}
		if (!checkCode.equals(idencode)) {
			throw new UserException("验证码错误");
		}
	}

	/**
	 * 检查用户参数是否合规
	 * @param user 用户信息
	 */
	public void verifyUserInfo(User user){
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new UserException("账号和密码不能为空");
        }
        if (user.getSex() != null && !(UserGenderEnum.HIDE.getCode().equals(user.getSex()) ||
                UserGenderEnum.BOY.getCode().equals(user.getSex()) ||
                UserGenderEnum.GIRL.getCode().equals(user.getSex()))) {
            throw new UserException("不合规的用户性别参数");
        }
    }

}
