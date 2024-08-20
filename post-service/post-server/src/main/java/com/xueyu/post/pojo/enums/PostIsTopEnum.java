package com.xueyu.post.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子是否置顶枚举类
 *
 * @author durance
 */
@Getter
@AllArgsConstructor
public enum PostIsTopEnum {

	/**
	 * 未置顶
	 */
	NO(0, "未置顶"),

	/**
	 * 置顶
	 */
	YES(1, "置顶"),
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
