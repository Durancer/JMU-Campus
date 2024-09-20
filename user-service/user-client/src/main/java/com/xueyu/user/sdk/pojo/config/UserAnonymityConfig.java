package com.xueyu.user.sdk.pojo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 匿名用户配置
 *
 * @author durance
 */
@Data
@Component
@ConfigurationProperties(prefix = "user.data")
public class UserAnonymityConfig {

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private Integer sex;

}
