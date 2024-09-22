package com.xueyu.post.pojo.vo;

import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 帖子列表显示实体类
 *
 * @author durance
 */
@Data
public class PostListVO {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 帖子作者信息
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
	 * 图片列表
	 */
	List<ImageAnnexView> imgList;

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
	 * 帖子最热评论
	 */
	CommentAnswerVO postHotComment;

	/**
	 * 帖子创建时间
	 */
	Date createTime;

	/**
	 * 创建时间
	 */
	Date updateTime;

	/**
	 * 帖子状态 0 审核中 | 1 公开
	 */
	Integer status;

	/**
	 * 审核失败原因
	 */
	String reason;

	/**
	 * 是否点赞
	 */
	Boolean isLike = false;

}
