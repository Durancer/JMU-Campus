package com.xueyu.user.pojo.vo;

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
@NoArgsConstructor
@Data
@TableName("item_view")
public class ItemView {

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
	String desc;

	/**
	 * 资源图片
	 */
	String imageUrl;

    /**
	 * 发送数量
	 */
	Integer sendNum;

    /**
	 * 使用数量
	 */
	Integer useNum;

}
