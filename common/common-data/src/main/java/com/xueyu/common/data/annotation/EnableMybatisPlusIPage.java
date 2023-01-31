package com.xueyu.common.data.annotation;

import com.xueyu.common.data.config.MybatisPlusPageConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * mybatis-plus 分页功能实现
 *
 * @author durance
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisPlusPageConfig.class)
public @interface EnableMybatisPlusIPage {

}
