package com.xueyu.post.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子是否私有枚举类
 *
 * @author durance
 */
@Getter
@AllArgsConstructor
public enum PostIsPrivateEnum {

	/**
	 * 公开
	 */
	NO(0, "公开"),

	/**
	 * 私密
	 */
	YES(1, "私密"),
	;

	/**
	 * 值
	 */
	Integer value;

	/**
	 * 描述
	 */
	String desc;


}
