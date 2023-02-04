package com.xueyu.user.sdk.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户信息实体类
 *
 * @author durance
 */
@Data
public class UserDetail {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 用户名
	 */
	String username;

	/**
	 * 名称
	 */
	String nickname;

	/**
	 * 密码
	 */
	String password;

	/**
	 * 头像路径
	 */
	String avatarUrl;

	/**
	 * 个人介绍
	 */
	String introduce;

	/**
	 * 电话
	 */
	String phone;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
