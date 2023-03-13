package com.xueyu.common.web.exception;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/10/14 22:19
 */
public class FrequentVisitsException extends RuntimeException {

	public FrequentVisitsException(String errorMessage) {
		super(errorMessage);
	}

	public FrequentVisitsException() {
		super("访问频繁");
	}

}
