package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 图片存储附件表实体类
 *
 * @author durance
 */
@Data
@TableName("image_annex")
public class ImageAnnex {

	/**
	 * 自增id
	 */
	@TableId
	Integer id;

	/**
	 * 图片项目源行id
	 */
	Integer parentId;

	/**
	 * 图片名称
	 */
	String fileName;

}
