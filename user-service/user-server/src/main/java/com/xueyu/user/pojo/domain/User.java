package com.xueyu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

	/**
	 * 自增id
	 */
	@TableId
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
	 * 电话
	 */
	String phone;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
