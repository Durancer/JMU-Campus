package com.xueyu.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import static com.xueyu.common.core.enums.ResultTypeEnum.*;

/**
 * 接口响应体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
public class RestResult<T> implements Serializable {

	/**
	 * 状态码
	 */
	Integer code;

	/**
	 * 响应信息
	 */
	String message;

	/**
	 * 响应数据
	 */
	T data;

	/**
	 * 请求状态，操作成功为true，否则为false
	 */
	Boolean status;

	public RestResult() {
		this.code = SUCCESS.getCode();
		this.message = "success";
		this.data = null;
		this.status = true;
	}

	public RestResult(T data) {
		this.code = SUCCESS.getCode();
		this.message = "success";
		this.data = data;
		this.status = true;
	}

	public RestResult(Boolean status) {
		this.code = SUCCESS.getCode();
		this.message = SUCCESS.getDesc();
		this.data = null;
		this.status = status;
	}

	public RestResult(Boolean status, String message) {
		this.code = SUCCESS.getCode();
		this.message = SUCCESS.getDesc();
		this.data = null;
		this.status = status;
	}

	public RestResult(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
		this.status = false;
	}

	public static <T> RestResult<T> ok() {
		return new RestResult<>();
	}

	public static <T> RestResult<T> ok(T data) {
		return new RestResult<>(SUCCESS.getCode(), SUCCESS.getDesc(), data, true);
	}

	public static <T> RestResult<T> ok(T data, String message) {
		return new RestResult<>(SUCCESS.getCode(), message, data, true);
	}

	public static <T> RestResult<T> fail() {
		return new RestResult<>(FAIL.getCode(), FAIL.getDesc(), null, false);
	}

	public static <T> RestResult<T> fail(String errorMessage) {
		return new RestResult<>(FAIL.getCode(), errorMessage, null, false);
	}

	public static <T> RestResult<T> limit() {
		return new RestResult<>(FREQUENT.getCode(), FREQUENT.getDesc());
	}

	public static <T> RestResult<T> notFound() {
		return new RestResult<>(NO_FOUND.getCode(), NO_FOUND.getDesc());
	}

}
