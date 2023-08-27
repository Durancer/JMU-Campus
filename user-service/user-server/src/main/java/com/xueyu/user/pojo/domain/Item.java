package com.xueyu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资源实体类
 *
 * @author durance
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("item")
public class Item {

	/**
	 * 自增id
	 */
	@TableId
	Integer id;

	/**
	 * 资源名称
	 */
	String name;

	/**
	 * 资源描述
	 */
	String detail;

	/**
	 * 资源图片
	 */
	String image;

	/**
	 * 发送数量
	 */
	Integer sendNum;

	/**
	 * 使用数量
	 */
	Integer useNum;

}
