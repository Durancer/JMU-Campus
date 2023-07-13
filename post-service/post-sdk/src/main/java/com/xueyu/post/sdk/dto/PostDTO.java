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
	 * 标题
	 */
	String title;

	/**
	 * 昵称
	 */
	String nickname;

	/**
	 * 发布文本内容
	 */
	String content;

	/**
	 * 帖子创建时间
	 */
	Timestamp createTime;

}
