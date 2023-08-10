package com.xueyu.user.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * 用户服务异常
 *
 * @author durance
 */
public class UserException extends BusinessException {

	public UserException() {
		super("用户服务异常");
	}

	public UserException(String message) {
		super(message);
	}

}
