package com.xueyu.user.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户资源显示实体类
 *
 * @author durance
 */
@Data
@TableName("user_item_view")
public class UserItemView {

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 资源id
	 */
	Integer itemId;

	/**
	 * 资源数量
	 */
	Integer num;

	/**
	 * 资源名称
	 */
	String name;

	/**
	 * 资源描述
	 */
	String detail;

	/**
	 * 资源图片路径
	 */
	String imageUrl;

}
