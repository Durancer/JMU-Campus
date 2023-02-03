package com.xueyu.post.exception;

/**
 * 帖子异常类
 *
 * @author durance
 */
public class PostException extends RuntimeException {

	public PostException() {
		super("帖子服务异常");
	}

	public PostException(String errMessage) {
		super(errMessage);
	}

}
