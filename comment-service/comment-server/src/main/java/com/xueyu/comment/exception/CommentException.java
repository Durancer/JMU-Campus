package com.xueyu.comment.exception;

import com.xueyu.common.core.exception.BusinessException;

/**
 * 评论服务异常
 *
 * @author durance
 */
public class CommentException extends BusinessException {

	public CommentException(String errorMessage) {
		super(errorMessage);
	}

	public CommentException() {
		super("评论服务异常");
	}

}
