package com.xueyu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户数据统计
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_general")
public class UserGeneral {

	/**
	 * 用户id
	 */
	@TableId
	Integer userId;

	/**
	 * 阅读量
	 */
	Integer viewNum;

	/**
	 * 点赞量
	 */
	Integer likeNum;

	/**
	 * 评论量
	 */
	Integer commentNum;

}
