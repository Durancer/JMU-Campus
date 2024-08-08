package com.xueyu.comment.pojo.enums;

/**
 * 评论类型枚举类
 *
 * @author durance
 */
public enum CommentType {

	/**
	 * 根评论
	 */
	ROOT("root"),

	/**
	 * 回复类型评论
	 */
	ANSWER("answer");

	private String value;

	CommentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
