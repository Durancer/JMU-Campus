package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帖子数据统计实体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("post_general")
public class PostGeneral {

	/**
	 * 帖子id
	 */
	@TableId
	Integer postId;

	/**
	 * 阅读量
	 */
	Integer viewNum;

	/**
	 * 点赞数量
	 */
	Integer likeNum;

	/**
	 * 评论数量
	 */
	Integer commentNum;

}
