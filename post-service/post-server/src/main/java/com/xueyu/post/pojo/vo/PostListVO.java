package com.xueyu.post.pojo.vo;

import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.Data;

import java.sql.Timestamp;
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
	 * 是否投票 若集合为空则为未投票或未登录，投票则返回投票选项id的集合
	 */
	List<Integer> isVote;

	/**
	 * 点赞用户列表
	 */
	List<UserSimpleVO> userLikeBOList;

	/**
	 * 帖子创建时间
	 */
	Timestamp createTime;

}
