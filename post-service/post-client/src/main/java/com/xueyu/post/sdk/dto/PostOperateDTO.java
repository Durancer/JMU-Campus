package com.xueyu.post.sdk.dto;

import lombok.Data;

/**
 * 帖子mq数据传输对象
 *
 * @author durance
 */
@Data
public class PostOperateDTO {

	/**
	 * 执行的用户id
	 */
	Integer userId;

	/**
	 * 发布者的id
	 */
	Integer authorId;

	/**
	 * 帖子id
	 */
	Integer postId;

}
