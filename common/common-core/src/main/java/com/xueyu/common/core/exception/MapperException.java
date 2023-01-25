package com.xueyu.common.core.exception;

/**
 * 持久层异常
 *
 * @author durance
 */
public class MapperException extends RuntimeException {

	public MapperException(String message) {
		super(message);
	}

	public MapperException() {
		super("持久层出现异常");
	}

}
