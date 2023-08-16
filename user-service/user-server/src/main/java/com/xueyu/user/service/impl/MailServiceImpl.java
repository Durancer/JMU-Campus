package com.xueyu.user.service.impl;

import com.xueyu.user.exception.UserException;
import com.xueyu.user.pojo.bo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.xueyu.user.constant.MailConstant.CODE_KEY_PREFIX;

/**
 * @author durance
 */
@Slf4j
@Service
public class MailServiceImpl {

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	@Resource
	private JavaMailSenderImpl mailSender;

	@Async
	public void sendMail(Mail mail) throws MailSendException {
		checkMail(mail);
		try {
			sendMimeMail(mail);
		} catch (Exception e) {
			log.warn("邮件发送失败：{}", e.getMessage());
			throw new MailSendException("邮件发送失败:" + e.getMessage());
		}
	}

	/**
	 * 检测邮件信息方法
	 *
	 * @param mail 邮件信息
	 * @throws RuntimeException 检查失败，信息异常
	 */
	private void checkMail(Mail mail) throws RuntimeException {
		if (isEmpty(mail.getTo())) {
			throw new RuntimeException("邮件收信人不能为空");
		}
		if (isEmpty(mail.getSubject())) {
			throw new RuntimeException("邮件主题不能为空");
		}
		if (isEmpty(mail.getText())) {
			throw new RuntimeException("邮件内容不能为空");
		}
	}

	/**
	 * 构建复杂邮件信息类
	 *
	 * @param mail 邮件内容
	 * @throws Exception 发送失败
	 */
	private void sendMimeMail(Mail mail) throws Exception {
		//true表示支持复杂类型
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
		if (mail.getFrom() == null || mail.getFrom().isEmpty()) {
			mail.setFrom("i集大校园");
		}
		//邮件发信人
		messageHelper.setFrom(mailSender.getUsername() + '(' + mail.getFrom() + ')');
		//邮件收信人
		messageHelper.setTo(mail.getTo().split(","));
		//邮件主题
		messageHelper.setSubject(mail.getSubject());
		//邮件内容
		messageHelper.setText(mail.getText());
		//抄送
		if (!isEmpty(mail.getCc())) {
			messageHelper.setCc(mail.getCc().split(","));
		}
		//密送
		if (!isEmpty(mail.getBcc())) {
			messageHelper.setCc(mail.getBcc().split(","));
		}
		//添加邮件附件
		if (mail.getMultipartFiles() != null) {
			for (MultipartFile multipartFile : mail.getMultipartFiles()) {
				messageHelper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
			}
		}
		//发送时间
		if (isEmpty(mail.getSentDate())) {
			mail.setSentDate(new Date());
		}
		messageHelper.setSentDate(mail.getSentDate());
		//正式发送邮件
		mailSender.send(messageHelper.getMimeMessage());
	}

	private boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	/**
	 * 发送验证码邮件
	 *
	 * @param mail 邮件信息
	 */
	public void sendVerificationCode(Mail mail) {
		// todo 限制单ip邮箱发送量
		// 判断当前待发送邮箱是否已经有验证码
		String key = CODE_KEY_PREFIX + mail.getTo();
		Integer code = redisTemplate.opsForValue().get(key);
		if (code != null) {
			throw new UserException("当前邮箱已经发送验证码");
		}
		// 生成随机 6位验证码
		int idenCode = (int) ((Math.random() * 9 + 1) * 100000);
		mail.setSubject("欢迎进入 i集大校园，快来开启校园生活吧！");
		mail.setText("【i集大校园】您正在注册/登录 i集大校园，验证码：" + idenCode + ", 该验证码一分钟内有效，如非本人操作请勿将验证码给与他人。");
		redisTemplate.opsForValue().set(key, idenCode, 60, TimeUnit.SECONDS);
		log.info("用户邮箱 -> {}, 发送邮箱验证码", mail.getTo());
		sendMail(mail);
	}

}
