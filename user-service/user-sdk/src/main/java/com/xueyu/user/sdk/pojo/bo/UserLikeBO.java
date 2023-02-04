package com.xueyu.user.sdk.pojo.bo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 点赞用户显示对象
 *
 * @author durance
 */
@Data
public class UserLikeBO {

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 用户头像地址
	 */
	String avatarUrl;

	/**
	 * 点赞时间
	 */
	Timestamp time;

}
