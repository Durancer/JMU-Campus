package com.xueyu.post.pojo.vo;

import com.xueyu.user.sdk.pojo.bo.UserLikeBO;
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
	 * 用户id
	 */
	Integer userId;

	/**
	 * 发布文本内容
	 */
	String content;

	/**
	 * 浏览量
	 */
	String viewNum;

	/**
	 * 图片列表
	 */
	String[] imgList;

	/**
	 * 点赞用户列表
	 */
	List<UserLikeBO> userLikeBOList;

	/**
	 * 帖子创建时间
	 */
	Timestamp createTime;

}
