package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Post;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author durance
 */
public interface PostService extends IService<Post> {

	/**
	 * 上传帖子信息
	 *
	 * @param post  帖子信息
	 * @param files 图片信息
	 * @return 发布结果
	 */
	Boolean publishPost(Post post, MultipartFile[] files);

}
