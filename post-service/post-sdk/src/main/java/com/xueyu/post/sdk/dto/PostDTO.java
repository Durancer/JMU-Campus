package com.xueyu.post.sdk.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 博客数据传输
 *
 * @author durance
 */
@Data
public class PostDTO {

	/**
	 * 帖子id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 发布文本内容
	 */
	String content;

	/**
	 * 帖子创建时间
	 */
	Timestamp createTime;

	/**
	 * 帖子状态 0为审核中 | 1为公开 | 2审核失败
	 */
	Integer status;

}
