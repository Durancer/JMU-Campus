package com.xueyu.comment.pojo.vo;

import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * 帖子评论列表实体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostVO {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 帖子id
	 */
	Integer postId;

	/**
	 * 评论用户信息
	 */
	UserSimpleVO userInfo;

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
	 * 评论类型 枚举值 root | answer
	 */
	String type;

	/**
	 * 是否点赞
	 */
	Boolean isLike = false;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

	/**
	 * 该评论下的子评论
	 */
	List<CommentAnswerVO> answerCommentList;

}
