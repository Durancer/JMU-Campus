package com.xueyu.comment.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author durance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment {

	/**
	 * 自增id
	 */
	@TableId
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
	 * 回复的用户id，为根评论时此项为null
	 */
	Integer toUserId;

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
	 * 评论类型
	 */
	String type;

	/**
	 * 创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

}
