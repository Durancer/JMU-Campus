package com.xueyu.post.service;

/**
 * 帖子操作业务层
 *
 * @author durance
 */
public interface PostOperateService {

	/**
	 * 用户点赞（取消）帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return true 点赞 or  false 取消
	 */
	Boolean likeUserPost(Integer postId, Integer userId);

	/**
	 * 检查用户是否点赞了该帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 已点赞 true | 未点赞 false
	 */
	Boolean checkLiked(Integer postId, Integer userId);

	/**
	 * 检查用户是否点赞了该帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 已点赞 true | 未点赞 false
	 */
	Boolean hideOrOpenPost(Integer postId, Integer userId);

}
