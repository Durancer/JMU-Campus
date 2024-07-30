package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.vo.PostDetailVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author durance
 */
public interface PostService extends IService<Post> {


	/**
	 * 上传帖子信息
	 *
	 * @param post    帖子
	 * @param files   文件
	 * @param vote    投票
	 * @param options 选项
	 * @param names   话题
	 * @return {@link Boolean}
	 */
	Boolean publishPost(Post post, MultipartFile[] files, Vote vote ,String[] options,List<String> names);

	/**
	 * 删除帖子
	 *
	 * @param postId 帖子id
	 * @param userId 帖子用户id
	 * @return 删除结果
	 */
	Boolean deletePost(Integer postId, Integer userId);

	/**
	 * 分页查询当前登录用户的帖子列表, 获取用户帖子列表使用，userId用于指定查询的用户
	 *
	 * @param current 当前页
	 * @param size    每页大小
	 * @param userId  用户id，为空时查找全部
	 * @return 分页数据
	 */
	ListVO<PostListVO> getUserPostListByPage(Integer current, Integer size, Integer userId);

	/**
	 * 分页其他用户的帖子列表, 获取用户帖子列表使用，userId用于指定查询的用户
	 *
	 * @param current 当前页
	 * @param size    每页大小
	 * @param userId  用户id，为空时查找全部
	 * @return 分页数据
	 */
	ListVO<PostListVO> getUserSelfPostListByPage(Integer current, Integer size, Integer userId);

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

	/**
	 * 分页获得所有帖子, 首页使用，userId用于判断是否点赞
	 *
	 * @param current 当前
	 * @param size    大小
	 * @param userId  用户id
	 * @return {@link ListVO}<{@link PostListVO}>
	 */
	ListVO<PostListVO> getAllPostListByPage(Integer current, Integer size, Integer userId);

	/**
	 * 帖子列表查询最后一层，封装 帖子列表信息，处理帖子额外的信息查询，如 用户信息设置等
	 *
	 * @param list   列表
	 * @param userId 用户id
	 * @return {@link List}<{@link PostListVO}>
	 */
	List<PostListVO> dealPostListInfo(List<PostView> list, Integer userId);

	/**
	 * 分页获取未审核的帖子
	 *
	 * @param current 当前页
	 * @param size 页大小
	 * @param userId 用户id
	 * @return 帖子信息
	 */
	ListVO<PostListVO> getStatusPostListByPage(Integer current, Integer size, Integer userId);

	/**
	 * 通过话题名称分页查询其对应的帖子详情
	 *
	 * @param topicName 话题名称
	 * @param userId 用户id
	 * @param current 当前页
	 * @param size 页大小
	 * @return 帖子列表
	 */
	ListVO<PostListVO>  getPostListByTopicIds(String topicName, Integer userId, Integer current, Integer size);

}
