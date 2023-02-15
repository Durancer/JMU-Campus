package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.mapper.ImageAnnexMapper;
import com.xueyu.post.mapper.ImageAnnexViewMapper;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.ImageAnnex;
import com.xueyu.post.service.ImageAnnexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author durance
 */
@Service
public class ImageAnnexServiceImpl extends ServiceImpl<ImageAnnexMapper, ImageAnnex> implements ImageAnnexService {

	@Resource
	ImageAnnexViewMapper imageAnnexViewMapper;

	@Override
	public Map<Integer, List<ImageAnnexView>> getPostListImgs(List<Integer> postIds) {
		if (postIds.size() == 0) {
			return new HashMap<>(0);
		}
		LambdaQueryWrapper<ImageAnnexView> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(ImageAnnexView::getParentId, postIds);
		List<ImageAnnexView> imageAnnexes = imageAnnexViewMapper.selectList(wrapper);
		Map<Integer, List<ImageAnnexView>> result = new HashMap<>(postIds.size());
		for (ImageAnnexView imageAnnexView : imageAnnexes) {
			if (result.containsKey(imageAnnexView.getParentId())) {
				List<ImageAnnexView> imageAnnexViews = result.get(imageAnnexView.getParentId());
				imageAnnexViews.add(imageAnnexView);
			} else {
				List<ImageAnnexView> imageAnnexViews = new ArrayList<>();
				imageAnnexViews.add(imageAnnexView);
				result.put(imageAnnexView.getParentId(), imageAnnexViews);
			}

		}
		return result;
	}

}
