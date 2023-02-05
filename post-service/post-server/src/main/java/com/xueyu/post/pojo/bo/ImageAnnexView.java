package com.xueyu.post.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("image_annex_view")
public class ImageAnnexView {

	/**
	 * 自增id
	 */
	Integer id;

	/**
	 * 源数据行id
	 */
	Integer parentId;

	/**
	 * 图片地址
	 */
	String imgUrl;

}
