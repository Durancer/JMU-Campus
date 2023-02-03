package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.PostGeneral;
import org.apache.ibatis.annotations.Param;

/**
 * @author durance
 */
public interface PostGeneralMapper extends BaseMapper<PostGeneral> {

	/**
	 * 在原数据的基础上更新帖子收到点赞量
	 *
	 * @param postId 用户id
	 * @param num    修改值
	 * @return 影响行数
	 */
	Integer updateLikeNumByPostId(@Param("postId") Integer postId, @Param("num") Integer num);

	/**
	 * 在原数据的基础上更新用户收到评论
	 *
	 * @param postId 用户id
	 * @param num    修改值
	 * @return 影响行数
	 */
	Integer updateCommentNumByPostId(@Param("postId") Integer postId, @Param("num") Integer num);

}
