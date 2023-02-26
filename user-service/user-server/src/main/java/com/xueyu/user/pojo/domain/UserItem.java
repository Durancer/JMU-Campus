package com.xueyu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资源实体类
 *
 * @author durance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_item")
public class UserItem {

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

}
