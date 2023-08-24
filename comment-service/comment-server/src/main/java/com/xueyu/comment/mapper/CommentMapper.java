package com.xueyu.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.comment.pojo.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查找出每个帖子里的热度最高（如果存在两条热度最高的，取创建时间早的）的评论
     *
     * @param postIds 帖子id
     * @return
     */
    List<Comment> selectMaxHotByPostId(List<Integer> postIds);

}
