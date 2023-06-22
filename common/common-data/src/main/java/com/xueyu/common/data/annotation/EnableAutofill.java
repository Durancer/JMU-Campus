package com.xueyu.common.data.annotation;

import com.xueyu.common.data.config.MyMetaObjectHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动填充属性字段
 *
 * @Author：mofan
 * @Date：2023/4/26 22:13
 * @version：1.0
 * @Description：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyMetaObjectHandler.class)
public @interface EnableAutofill
{
}
