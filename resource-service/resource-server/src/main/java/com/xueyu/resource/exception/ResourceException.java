package com.xueyu.resource.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * @author durance
 */
public class ResourceException extends BusinessException {

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException() {
        super("资源服务异常");
    }

}
