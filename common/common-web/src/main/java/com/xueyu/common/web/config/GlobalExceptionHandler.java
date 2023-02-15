package com.xueyu.common.web.config;

import com.xueyu.common.core.exception.BusinessException;
import com.xueyu.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author durance
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 拦截以下不包括的异常信息
	 *
	 * @param e 异常信息
	 * @return 异常响应
	 */
	@ExceptionHandler(Exception.class)
	public RestResult<Object> doException(Exception e) {
		e.printStackTrace();
		return new RestResult<>(500, "服务器内部错误", null, false);
	}

	/**
	 * 业务异常响应
	 *
	 * @param e 异常信息
	 * @return 异常响应
	 */
	@ExceptionHandler(BusinessException.class)
	public RestResult<Object> doBusinessException(BusinessException e) {
		log.warn(e.getMessage());
		return new RestResult<>(401, e.getMessage(), null, false);
	}

	@ExceptionHandler(ServletException.class)
	public RestResult<Object> doServletException(Exception e) {
		log.warn("请求异常,{}", e.getMessage());
		return new RestResult<>(402, "请求异常", null, false);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
	public RestResult<Object> doHttpRequestMethodNotSupportedException(Exception e) {
		log.warn("请求方式异常,{}", e.getMessage());
		return new RestResult<>(402, "请求方式异常", null, false);
	}

	@ExceptionHandler({MissingRequestValueException.class, IllegalArgumentException.class, TypeMismatchException.class})
	public RestResult<Object> doIllegalArgumentException(Exception e) {
		log.warn("参数异常,{}", e.getMessage());
		return new RestResult<>(402, "参数异常", null, false);
	}

	@ExceptionHandler(BindException.class)
	public RestResult<Object> doBindException(BindException e) {
		List<FieldError> allErrors = e.getFieldErrors();
		StringBuilder info = new StringBuilder();
		for (FieldError errorMessage : allErrors) {
			info.append(errorMessage.getDefaultMessage()).append("; ");
		}
		log.info("数据异常,{}", info);
		return new RestResult<>(402, "数据异常:" + info, null, false);
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public RestResult<Object> doHttpMessageConversionException(HttpMessageConversionException e) {
		log.warn("数据异常,{}", e.getMessage());
		return new RestResult<>(403, "数据异常", null, false);
	}

	/**
	 * 访问不存在的页面
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public RestResult<Object> doNoHandlerFoundException(Exception e) {
		log.warn("页面不存在,{}", e.getMessage());
		return new RestResult<>(404, "操作异常", null, false);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public RestResult<Object> doMaxUploadSizeExceededException(Exception e) {
		log.warn("上传文件过大,{}", e.getMessage());
		return new RestResult<>(403, "上传文件过大", null, false);
	}

}
