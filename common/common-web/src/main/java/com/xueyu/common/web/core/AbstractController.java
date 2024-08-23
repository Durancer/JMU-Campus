package com.xueyu.common.web.core;

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
     * @param o
     * @param errMessage
     */
    public void nonNull(Object o, String errMessage){
        if (Objects.isNull(o)){
            throw new BusinessException(errMessage);
        }
    }

    /**
     * 验证参数是否正确
     *
     * @param b
     * @param errMessage
     */
    public void verifyParam(boolean b, String errMessage){
        if (!b){
            throw new BusinessException(errMessage);
        }
    }

}
