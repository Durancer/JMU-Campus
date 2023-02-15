package com.xueyu.comment.sdk.dto;

import lombok.Data;

/**
 * 评论消息传输体
 *
 * @author durance
 */
@Data
public class CommentDTO {

	/**
	 * 评论信息
	 */
	Integer commentId;

	/**
	 * 执行用户id
	 */
	Integer userId;

	/**
	 * 帖子id
	 */
	Integer postId;

	/**
	 * 帖子作者id
	 */
	Integer authorId;

	/**
	 * 影响数量
	 */
	Integer effectNum;

}
