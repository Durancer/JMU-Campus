package com.xueyu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户关注关系do
 *
 * @author durance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_follow")
public class UserFollow {

	/**
	 * 主键id
	 */
	@TableId
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 关注者id
	 */
	Integer followId;

	/**
	 * 是否相互关注 0 否 1是
	 */
	Integer isMutual;

	/**
	 * 创建时间
	 */
	Date createTime;

}
