package com.xueyu.post.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 帖子列表显示实体类
 *
 * @author durance
 */
@Data
public class PostView {

	/**
	 * 自增id
	 */
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
	 * 发布文本内容
	 */
	String content;

	/**
	 * 浏览量
	 */
	Integer viewNum;

	/**
	 * 评论量
	 */
	Integer commentNum;

	/**
	 * 点赞量
	 */
	Integer likeNum;

	/**
	 * 帖子创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

	/**
	 * 帖子状态 0 审核中 | 1 公开
	 */
	Integer status;

	/**
	 * 帖子状态 0 公开 | 1 私密
	 */
	Integer isPrivate;

}
