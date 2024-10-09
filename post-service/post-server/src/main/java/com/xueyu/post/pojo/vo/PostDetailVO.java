package com.xueyu.post.pojo.vo;

import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
	 * 帖子话题
	 */
	List<Topic> topics;

	/**
	 * 点赞用户列表
	 */
	List<UserSimpleVO> userLikeList;

	/**
	 * 是否点赞了该帖子 true 为已点赞，未登录或未点赞为 false
	 */
	Boolean isLike;

	/**
	 * 帖子创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

	/**
	 * 帖子状态 0 公开 | 1 私密
	 */
	Integer isPrivate;

}
