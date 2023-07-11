package com.xueyu.search.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * 帖子异常类
 *
 * @author durance
 */
public class PostSearchException extends BusinessException {

	public PostSearchException() {
		super("帖子服务异常");
	}

	public PostSearchException(String errMessage) {
		super(errMessage);
	}

}
