package com.xueyu.post.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子状态枚举类
 *
 * @author durance
 */
@Getter
@AllArgsConstructor
public enum PostStatus {

	/**
	 * 审核中
	 */
	EXAMINE(0),

	/**
	 * 公开
	 */
	PUBLIC(1),

	/**
	 * 审核失败
	 */
	FAIL(2);

	final Integer value;

}
