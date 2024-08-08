package com.xueyu.comment.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论类型枚举类
 *
 * @author durance
 */
@AllArgsConstructor
@Getter
public enum CommentStatusEnum {

	/**
	 * 审核中
	 */
	EXAMINE(0, "审核中"),

	/**
	 * 审核成功
	 */
	PUBLIC(1, "审核成功"),

	/**
	 * 审核失败
	 */
	FAIL(2, "审核失败");

	/**
	 * 编码
	 */
	private Integer code;

	/**
	 * 描述
	 */
	private String text;

	/**
	 * 是否在枚举中
	 * @param code code
	 * @return
	 */
	public static boolean isInEnums(Integer code){
		for(CommentStatusEnum commentStatusEnum : CommentStatusEnum.values()){
			if (commentStatusEnum.code.equals(code)){
				return true;
			}
		}
		return false;
	}

}
