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

	/**
	 * 用户点赞（取消）帖子
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return true 点赞 or  false 取消
	 */
	Boolean likeUserPost(Integer postId, Integer userId);

}
