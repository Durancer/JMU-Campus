package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.ImageAnnexMapper;
import com.xueyu.post.mapper.ImageAnnexViewMapper;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.ImageAnnex;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.resource.client.ResourceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author durance
 */
@Service
@Slf4j
public class ImageAnnexServiceImpl extends ServiceImpl<ImageAnnexMapper, ImageAnnex> implements ImageAnnexService {

	@Resource
	ImageAnnexViewMapper imageAnnexViewMapper;

	@Resource
	ResourceClient resourceClient;

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

	@Override
	public Boolean savePostImage(MultipartFile[] files, Integer postId) {
		try{
			List<ImageAnnex> images = new ArrayList<>();
			for (MultipartFile file : files) {
				// 将存入的图片名称存入集合
				ImageAnnex imageAnnex = new ImageAnnex();
				// todo 一次性上传所有图片，减少服务调用次数
				imageAnnex.setFileName(resourceClient.uploadImageFile(file).getData().get("fileName"));
				imageAnnex.setParentId(postId);
				images.add(imageAnnex);
			}
			// 将文件名存入帖子服务的图片附件表
			saveBatch(images);
		}catch (Exception e){
			log.error("存储帖子照片失败，{}", e.getMessage());
			throw new PostException("存储帖子照片失败");
		}
		return true;
	}

	@Override
	public Boolean deletePostImage(Integer postId) {
		// 删除帖子图片
		LambdaQueryWrapper<ImageAnnex> imgWrapper = new LambdaQueryWrapper<>();
		imgWrapper.eq(ImageAnnex::getParentId, postId);
		List<ImageAnnex> imgList = list(imgWrapper);
		// 如果帖子有图片则进行图片删除
		if (!CollectionUtils.isEmpty(imgList)) {
			String[] fileList = new String[imgList.size()];
			for (int i = 0; i < fileList.length; i++) {
				fileList[i] = imgList.get(i).getFileName();
			}
			resourceClient.deleteFilesListByFileName(fileList);
		}
		return true;
	}
}
