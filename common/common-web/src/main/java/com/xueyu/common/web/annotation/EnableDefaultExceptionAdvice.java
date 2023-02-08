package com.xueyu.common.web.annotation;

import com.xueyu.common.web.config.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Web程序异常捕获响应异常信息
 *
 * @author durance
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GlobalExceptionHandler.class)
public @interface EnableDefaultExceptionAdvice {

}
