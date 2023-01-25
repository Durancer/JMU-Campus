package com.xueyu.common.core.exception;

/**
 * 业务逻辑层异常
 *
 * @author durance
 */
public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException() {
		super("业务逻辑层出现异常");
	}

}
