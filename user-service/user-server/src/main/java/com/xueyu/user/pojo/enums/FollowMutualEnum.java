package com.xueyu.user.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相互关注枚举
 *
 * @author durance
 */
@AllArgsConstructor
@Getter
public enum FollowMutualEnum {

	/**
	 * 审核中
	 */
	NO(0, "未相互关注"),

	/**
	 * 审核成功
	 */
	YES(1, "相互关注");

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
		for(FollowMutualEnum followMutualEnum : FollowMutualEnum.values()){
			if (followMutualEnum.code.equals(code)){
				return true;
			}
		}
		return false;
	}

}
