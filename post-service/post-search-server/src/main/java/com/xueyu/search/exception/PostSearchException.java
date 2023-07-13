package com.xueyu.search.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * 帖子搜索异常类
 *
 * @author fsj0591
 */
public class PostSearchException extends BusinessException {

	public PostSearchException() {
		super("帖子服务异常");
	}

	public PostSearchException(String errMessage) {
		super(errMessage);
	}

}
