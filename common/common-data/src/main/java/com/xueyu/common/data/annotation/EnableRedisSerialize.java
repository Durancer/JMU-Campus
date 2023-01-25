package com.xueyu.common.data.annotation;

import com.xueyu.common.data.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 实现 redis 自动序列化
 *
 * @author durance
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfig.class)
public @interface EnableRedisSerialize {

}
