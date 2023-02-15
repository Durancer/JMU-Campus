package com.xueyu.comment.sdk.constant;

/**
 * 评论服务mq常量
 *
 * @author durance
 */
public class CommentMqContants {

	/**
	 * 评论服务交换机
	 */
	public static final String COMMENT_EXCHANGE = "post";

	/**
	 * 评论插入
	 */
	public static final String COMMENT_INSERT_KEY = "comment.insert";

	/**
	 * 评论更新
	 */
	public static final String COMMENT_UPDATE_KEY = "comment.update";

	/**
	 * 评论删除
	 */
	public static final String COMMENT_DELETE_KEY = "comment.delete";

}
