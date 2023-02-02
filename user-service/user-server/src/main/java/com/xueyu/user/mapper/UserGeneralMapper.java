package com.xueyu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.user.pojo.domain.UserGeneral;
import org.apache.ibatis.annotations.Param;

/**
 * @author durance
 */
public interface UserGeneralMapper extends BaseMapper<UserGeneral> {

	/**
	 * 更新用户访问量
	 *
	 * @param userId 用户id
	 * @return 影响行数
	 */
	Integer updateViewNumByUserId(@Param("userId") Integer userId);

	/**
	 * 在原数据的基础上更新用户点赞量
	 *
	 * @param userId 用户id
	 * @param num    修改值
	 * @return
	 */
	Integer updateLikeNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

}
