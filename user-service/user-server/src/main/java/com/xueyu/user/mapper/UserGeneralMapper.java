package com.xueyu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.user.pojo.domain.UserGeneral;
import org.apache.ibatis.annotations.Param;

/**
 * @author durance
 */
public interface UserGeneralMapper extends BaseMapper<UserGeneral> {

	/**
	 * 更新用户访问量，访问量不做减少操作
	 *
	 * @param userId 用户id
	 * @return 影响行数
	 */
	Integer updateViewNumByUserId(@Param("userId") Integer userId);

	/**
	 * 在原数据的基础上更新用户收到点赞量
	 *
	 * @param userId 用户id
	 * @param num    修改值
	 * @return 影响行数
	 */
	Integer updateLikeNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 在原数据的基础上更新用户收到评论量
	 *
	 * @param userId 用户id
	 * @param num    修改值
	 * @return 影响行数
	 */
	Integer updateCommentNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 在原数据的基础上更新用户粉丝数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateFansNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 在原数据的基础上更新用户帖子数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updatePostNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

}
