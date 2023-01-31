package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.mapper.ImageAnnexMapper;
import com.xueyu.post.mapper.PostMapper;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.service.PostService;
import com.xueyu.resource.client.ResourceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author durance
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

	@Resource
	PostMapper postMapper;

	@Resource
	ImageAnnexMapper imageAnnexMapper;

	@Resource
	ResourceClient resourceClient;

	@Override
	public Boolean publishPost(Post post, MultipartFile[] files) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		post.setCreateTime(now);
		// 存入帖子数据，获得主键值
		postMapper.insert(post);
		if (files != null) {
			List<String> fileNames = new ArrayList<>();
			for (MultipartFile file : files) {
				fileNames.add(resourceClient.uploadFile(file).getData().get("fileName"));
			}
			// todo 将文件名存入帖子服务的图片附件表
		}
		return true;
	}

}
