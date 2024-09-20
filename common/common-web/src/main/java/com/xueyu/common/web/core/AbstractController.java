package com.xueyu.common.web.core;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xueyu.common.core.exception.BusinessException;

import java.util.Objects;

/**
 * controller 基类
 *
 * @author durance
 */
public abstract class AbstractController {

    /**
     * 参数非空校验
     *
     * @param str 非空字符串
     * @param errMessage 错误消息
     */
    public void nonNull(String str, String errMessage){
        if (StringUtils.isEmpty(str)){
            throw new BusinessException(errMessage);
        }
    }

    /**
     * 参数非空校验
     *
     * @param o 非空对象
     * @param errMessage 错误消息
     */
    public void nonNull(Object o, String errMessage){
        if (Objects.isNull(o)){
            throw new BusinessException(errMessage);
        }
    }

    /**
     * 验证参数是否正确
     *
     * @param expression 验证表达式
     * @param errMessage 错误消息
     */
    public void verifyParam(boolean expression, String errMessage){
        if (!expression){
            throw new BusinessException(errMessage);
        }
    }

}
