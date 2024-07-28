package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("like_post")
public class LikePost {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 帖子id
	 */
	Integer postId;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 点赞时间
	 */
	Date time;

}
