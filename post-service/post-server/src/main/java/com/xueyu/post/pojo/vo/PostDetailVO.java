package com.xueyu.post.pojo.vo;

import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 帖子详情页面显示实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailVO {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 用户信息
	 */
	UserSimpleVO userInfo;

	/**
	 * 帖子标题
	 */
	String title;

	/**
	 * 发布文本内容
	 */
	String content;

	/**
	 * 浏览量
	 */
	Integer viewNum;

	/**
	 * 评论量
	 */
	Integer commentNum;

	/**
	 * 点赞量
	 */
	Integer likeNum;

	/**
	 * 投票信息
	 */
	VoteVO voteMessage;

	/**
	 * 评论列表
	 */
	Object commentList;

	/**
	 * 是否点赞了该帖子 true 为已点赞，未登录或未点赞为 false
	 */
	Boolean isLike;

	/**
	 * 帖子创建时间
	 */
	Timestamp createTime;

}
