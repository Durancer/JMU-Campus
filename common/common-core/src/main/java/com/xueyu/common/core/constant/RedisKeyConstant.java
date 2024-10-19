package com.xueyu.common.core.constant;

/**
 * @author durance
 */
public class RedisKeyConstant {

	/**
	 * 限流 redis key
	 */
	public static final String LIMIT_KEY = "limit:";

	/**
	 * 拉黑用户key
	 */
	public static final String BLACK_USER_KEY = "black:user:";

	/**
	 * 评论审核开关, redis存在，则评论需要审核
	 */
	public static final String COMMENT_APPROVAL_KEY = "comment.approval.isOpen";

}
