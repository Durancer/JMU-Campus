package com.xueyu.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.comment.pojo.domain.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * @author durance
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 在原数据的基础上更新评论收到点赞量
     *
     * @param commentId 评论id
     * @param num    修改值
     * @return 影响行数
     */
    Integer updateLikeNum(@Param("commentId") Integer commentId, @Param("num") Integer num);


}
