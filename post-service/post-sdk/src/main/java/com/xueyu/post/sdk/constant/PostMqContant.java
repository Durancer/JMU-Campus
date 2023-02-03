package com.xueyu.post.sdk.constant;

/**
 * post 帖子服务 mq 常量类
 *
 * @author durance
 */
public class PostMqContant {

	/**
	 * 帖子服务交换机
	 */
	public static final String POST_EXCHANGE = "post";

	/**
	 * 帖子插入
	 */
	public static final String POST_INSERT_KEY = "post.insert";

	/**
	 * 帖子更新
	 */
	public static final String POST_UPDATE_KEY = "post.update";

	/**
	 * 帖子删除
	 */
	public static final String POST_DELETE_KEY = "post.delete";

	/**
	 * 用户浏览帖子
	 */
	public static final String POST_OPERATE_VIEW_KEY = "post.view";

	/**
	 * 用户点赞帖子
	 */
	public static final String POST_OPERATE_LIKE_KEY = "post.like";

	/**
	 * 用户取消点赞帖子
	 */
	public static final String POST_OPERATE_LIKE_CANCEL_KEY = "post.like.cancel";

	/**
	 * 用户转发帖子
	 */
	public static final String POST_OPERATE_RELAY_KEY = "post.relay";

}
