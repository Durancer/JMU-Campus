package com.xueyu.user.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 用户显示层实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_view")
public class UserView {

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
	 * 头像路径
	 */
	String avatarUrl;

	/**
	 * 个人介绍
	 */
	String introduce;

	/**
	 * 用户邮箱
	 */
	String email;

	/**
	 * 性别 0 匿 | 1 boy | 2 girl
	 */
	Integer sex;

	/**
	 * 电话
	 */
	String phone;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
