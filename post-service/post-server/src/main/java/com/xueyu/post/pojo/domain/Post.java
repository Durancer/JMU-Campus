package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 帖子实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("post")
public class Post {

	/**
	 * 自增id
	 */
	@TableId
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 帖子标题
	 */
	String title;

	/**
	 * 帖子文本内容
	 */
	String content;

	/**
	 * 帖子创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

	/**
	 * 帖子状态 0为审核中 | 1为公开
	 */
	Integer status;

	/**
	 * 帖子状态 0 公开 | 1 私密
	 */
	Integer isPrivate;

	/**
	 * 审核失败原因
	 */
	String reason;

}
