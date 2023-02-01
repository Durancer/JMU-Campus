package com.xueyu.resource.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图片类型枚举类
 *
 * @author durance
 */
@Getter
@AllArgsConstructor
public enum ImageType {

	/**
	 * jpg类型
	 */
	JPG(".jpg"),

	/**
	 * jpeg类型
	 */
	JPEG(".jpeg"),

	/**
	 * png类型
	 */
	PNG(".png"),

	/**
	 * webp类型
	 */
	WEBP(".webp");

	private final String suffix;

}
