package com.xueyu.common.ampq.annotation;

import com.xueyu.common.ampq.config.AmqpMessageConverterConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * rabbitMQ 消息体自动转化
 *
 * @author durance
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AmqpMessageConverterConfig.class)
public @interface EnableAmqpMessageConverterConfig {

}
