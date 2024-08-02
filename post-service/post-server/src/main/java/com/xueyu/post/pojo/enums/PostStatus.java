package com.xueyu.post.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子状态枚举类
 *
 * @author durance
 */
@Getter
@AllArgsConstructor
public enum PostStatus {

	/**
	 * 审核中
	 */
	EXAMINE(0),

	/**
	 * 公开
	 */
	PUBLIC(1),

	/**
	 * 审核失败
	 */
	FAIL(2);

	final Integer value;

	/**
	 * 是否在枚举中
	 * @param code code
	 * @return
	 */
	public static boolean isInEnums(Integer code){
		for(PostStatus postStatus : PostStatus.values()){
			if (postStatus.value.equals(code)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过code取枚举
	 * @param code code
	 * @return
	 */
	public static PostStatus getEnumByCode(Integer code){
		for(PostStatus postStatus : PostStatus.values()){
			if (postStatus.value.equals(code)){
				return postStatus;
			}
		}
		return null;
	}

}
