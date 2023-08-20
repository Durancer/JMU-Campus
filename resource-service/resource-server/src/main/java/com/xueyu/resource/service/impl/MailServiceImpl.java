package com.xueyu.resource.service.impl;


import com.xueyu.resource.exception.ResourceException;
import com.xueyu.resource.sdk.bo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;


/**
 * @author durance
 */
@Slf4j
@Service
public class MailServiceImpl {

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
	 * @throws ResourceException 检查失败，信息异常
	 */
	private void checkMail(Mail mail) throws ResourceException {
		if (isEmpty(mail.getTo())) {
			throw new ResourceException("邮件收信人不能为空");
		}
		if (isEmpty(mail.getSubject())) {
			throw new ResourceException("邮件主题不能为空");
		}
		if (isEmpty(mail.getText())) {
			throw new ResourceException("邮件内容不能为空");
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
		log.info("用户邮箱 -> {}, 发送邮件", mail.getTo());
	}

	private boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	
}
