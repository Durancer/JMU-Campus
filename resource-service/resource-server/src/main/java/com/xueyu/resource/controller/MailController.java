package com.xueyu.resource.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.resource.sdk.bo.Mail;
import com.xueyu.resource.service.impl.MailServiceImpl;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("resource/mail")
@RestController
public class MailController {

    @Resource
    MailServiceImpl mailService;

    /**
     * 发送邮件
     *
     * @param mail 邮件信息
     * @return 发送结果
     */
    @PostMapping("send")
    public RestResult<?> sendUserMail(@RequestBody Mail mail) {
        mailService.sendMail(mail);
        return RestResult.ok(null, "发送成功");
    }

}
