package com.xueyu.post.sdk.constant;

/**
 * post 帖子服务 mq 常量类
 *
 * @author durance
 */
public class PostMqContants {

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

	/**
	 * 帖子审核通过
	 */
	public static final String POST_PASS_KEY = "post.pass";

	public static final String HOT_POST_KEY = "hot_post_recommend";

	/**
	 * 帖子点赞权重
	 */
	public static final Integer HOT_POST_LIKE_WEIGHT = 3;

	/**
	 * 帖子浏览权重
	 */
	public static final Integer HOT_POST_VIEW_WEIGHT = 1;

	/**
	 * 帖子评论权重
	 */
	public static final Integer HOT_POST_COMMENT_WEIGHT = 5;

}
