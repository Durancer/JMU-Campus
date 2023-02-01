package com.xueyu.resource.util;

import com.xueyu.resource.pojo.ImageType;

/**
 * 文件核对工具类
 *
 * @author durance
 */
public class FileCheckUtil {

	/**
	 * 核对图片文件是否符合规范
	 *
	 * @param originName 图片后缀
	 * @return 核对结果
	 */
	public static boolean checkImageSuffix(String originName) {
		int idx = originName.lastIndexOf(".");
		String suffix = originName.substring(idx);
		ImageType[] values = ImageType.values();
		for (ImageType type : values) {
			if (type.getSuffix().equals(suffix)) {
				return true;
			}
		}
		return false;
	}

}
