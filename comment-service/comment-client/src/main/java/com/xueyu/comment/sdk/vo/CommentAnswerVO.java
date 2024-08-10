package com.xueyu.comment.sdk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 帖子评论列表实体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAnswerVO {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 帖子id
	 */
	Integer postId;

	/**
	 * 评论用户信息
	 */
	Object userInfo;

	/**
	 * 回复的用户信息，为根评论时此项为null
	 */
	Object answerUserInfo;

	/**
	 * 根评论id，本身为根评论则为 id
	 */
	Integer rootId;

	/**
	 * 评论内容
	 */
	String content;

	/**
	 * 评论热度
	 */
	Integer hot;

	/**
	 * 评论类型 枚举值 root | answer
	 */
	String type;

	/**
	 * 是否点赞
	 */
	Boolean isLike = false;

	/**
	 * 创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

}
