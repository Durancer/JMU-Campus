package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
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
	 * 删除帖子
	 *
	 * @param postId 帖子id
	 * @param userId 帖子用户id
	 * @return 删除结果
	 */
	Boolean deletePost(Integer postId, Integer userId);

	/**
	 * 分页查询帖子列表
	 *
	 * @param current 当前页
	 * @param size    每页大小
	 * @param userId  用户id，为空时查找全部
	 * @return 分页数据
	 */
	ListVO<PostListVO> getPostListByPage(Integer current, Integer size, Integer userId);

	/**
	 * 获取帖子详情信息
	 *
	 * @param postId 帖子id
	 * @param userId 用户id
	 * @return 帖子详情信息
	 */
	PostDetailVO getPostDetailInfo(Integer postId, Integer userId);

	/**
	 * 审核帖子内容
	 *
	 * @param postId   帖子id
	 * @param decision 审核选择 1 通过，2 未通过
	 */
	void passPostContent(Integer postId, Integer decision);

}
