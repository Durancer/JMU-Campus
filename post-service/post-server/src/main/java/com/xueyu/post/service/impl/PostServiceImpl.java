package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.*;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.ImageAnnex;
import com.xueyu.post.pojo.domain.LikePost;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.domain.PostGeneral;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.PostService;
import com.xueyu.resource.client.ResourceClient;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserDetail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.xueyu.post.sdk.constant.PostMqContant.POST_DELETE_KEY;
import static com.xueyu.post.sdk.constant.PostMqContant.POST_EXCHANGE;

/**
 * @author durance
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

	@Resource
	ImageAnnexService imageAnnexService;

	@Resource
	PostGeneralMapper postGeneralMapper;

	@Resource
	ResourceClient resourceClient;

	@Resource
	UserClient userClient;

	@Resource
	LikePostMapper likePostMapper;

	@Resource
	RabbitTemplate rabbitTemplate;

	@Resource
	PostViewMapper postViewMapper;

	@Resource
	ImageAnnexViewMapper imageAnnexViewMapper;

	@Override
	public Boolean publishPost(Post post, MultipartFile[] files) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		post.setCreateTime(now);
		// 存入帖子数据，获得主键值
		query().getBaseMapper().insert(post);
		// 添加数据统计表行数据
		PostGeneral postGeneral = new PostGeneral();
		postGeneral.setPostId(post.getId());
		postGeneralMapper.insert(postGeneral);
		if (files != null) {
			List<ImageAnnex> images = new ArrayList<>();
			for (MultipartFile file : files) {
				// 将存入的图片名称存入集合
				ImageAnnex imageAnnex = new ImageAnnex();
				imageAnnex.setFileName(resourceClient.uploadImageFile(file).getData().get("fileName"));
				imageAnnex.setParentId(post.getId());
				images.add(imageAnnex);
			}
			// 将文件名存入帖子服务的图片附件表
			imageAnnexService.saveBatch(images);
		}
		return true;
	}

	@Override
	public Boolean deletePost(Integer postId, Integer userId) {
		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Post::getId, postId).eq(Post::getUserId, userId);
		Post post = query().getBaseMapper().selectOne(wrapper);
		// 如果未查出数据说明帖子为另外的用户或不存在
		if (post == null) {
			throw new PostException("用户id与帖子id不匹配");
		}
		// 删除帖子图片
		LambdaQueryWrapper<ImageAnnex> imgWrapper = new LambdaQueryWrapper<>();
		imgWrapper.eq(ImageAnnex::getParentId, postId);
		List<ImageAnnex> imgList = imageAnnexService.list(imgWrapper);
		// 如果帖子有图片则进行图片删除
		if (imgList.size() != 0) {
			String[] fileList = new String[imgList.size()];
			for (int i = 0; i < fileList.length; i++) {
				fileList[i] = imgList.get(i).getFileName();
			}
			resourceClient.deleteFilesListByFileName(fileList);
		}
		// todo 删除帖子评论
		// 删除帖子
		int delete = query().getBaseMapper().delete(wrapper);
		if (delete != 1) {
			throw new PostException("帖子删除失败");
		}
		// 发送mq消息
		PostOperateDTO postOperateDTO = new PostOperateDTO();
		postOperateDTO.setUserId(userId);
		postOperateDTO.setPostId(postId);
		postOperateDTO.setAuthorId(post.getUserId());
		rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_DELETE_KEY, postOperateDTO);
		return true;
	}

	@Override
	public ListVO<PostListVO> getPostListByPage(Integer current, Integer size, Integer userId) {
		IPage<PostView> page = new Page<>(current, size);
		LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
		// userId不为空则查找用户帖子列表
		if (userId != null) {
			wrapper.eq(PostView::getUserId, userId);
		}
		postViewMapper.selectPage(page, wrapper);
		ListVO<PostListVO> result = new ListVO<>();
		// 将除具体记录外的分页数据赋值
		BeanUtils.copyProperties(result, page);
		List<PostView> records = page.getRecords();
		for (PostView record : records) {
			PostListVO postListVO = new PostListVO();
			BeanUtils.copyProperties(postListVO, record);
			// 查询图片信息 todo 优化：一次查出所有的附件图片
			LambdaQueryWrapper<ImageAnnexView> imgWrapper = new LambdaQueryWrapper<>();
			imgWrapper.eq(ImageAnnexView::getParentId, record.getId());
			List<ImageAnnexView> imageAnnexViews = imageAnnexViewMapper.selectList(imgWrapper);
			// 创建图片列表
			if (imageAnnexViews.size() != 0) {
				String[] imgs = new String[imageAnnexViews.size()];
				for (int j = 0; j < imgs.length; j++) {
					imgs[j] = imageAnnexViews.get(j).getImgUrl();
				}
				postListVO.setImgList(imgs);
			}
			LambdaQueryWrapper<LikePost> likeWrapper = new LambdaQueryWrapper<>();
			likeWrapper.eq(LikePost::getPostId, record.getId());
			List<LikePost> likePosts = likePostMapper.selectList(likeWrapper);
			// 创建用户id列表并设值用户值 todo 优化：添加用户服务接口，一次查询多组用户信息，减少client的调用
			List<Integer> userIds = new ArrayList<>();
			for (LikePost likePost : likePosts) {
				userIds.add(likePost.getUserId());
			}
			if (userIds.size() != 0) {
				List<UserDetail> userDetailList = userClient.getUserDeatilInfoList(userIds).getData();
				postListVO.setUserLikeBOList(userDetailList);
			}
		}
		return result;
	}

}
