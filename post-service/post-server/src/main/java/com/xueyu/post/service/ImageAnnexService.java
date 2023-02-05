package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.ImageAnnex;

import java.util.List;
import java.util.Map;

/**
 * 图片附件表业务层
 *
 * @author durance
 */
public interface ImageAnnexService extends IService<ImageAnnex> {

	/**
	 * 获取用户帖子列表图片集合
	 *
	 * @param postIds 帖子id列表
	 * @return 列表图片 如果为空的项会返回空列表
	 */
	Map<Integer, List<ImageAnnexView>> getPostListImgs(List<Integer> postIds);

}
