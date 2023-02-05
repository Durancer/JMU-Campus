package com.xueyu.post.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * 帖子异常类
 *
 * @author durance
 */
public class PostException extends BusinessException {

	public PostException() {
		super("帖子服务异常");
	}

	public PostException(String errMessage) {
		super(errMessage);
	}

}
