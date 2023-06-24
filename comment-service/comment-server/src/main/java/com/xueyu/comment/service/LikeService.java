package com.xueyu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.pojo.vo.LikeVO;

import java.util.List;

public interface LikeService extends IService<Like> {

    /**
     * 判断是否点赞
     *
     * @param userId    用户id
     * @param commentId 评论id
     * @return boolean
     */
    boolean isLike(Integer userId,Integer commentId);

    /**
     * 点赞或取消点赞
     *
     * @param userId    用户id
     * @param commentId 评论id
     * @return boolean
     */
    boolean like(Integer userId, Integer commentId);

    /**
     * 用户点赞列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link LikeVO}>
     */
    List<LikeVO> likeCommons(Integer userId);

    /**
     * 用户被点赞列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link LikeVO}>
     */
    List<LikeVO> likeByCommons(Integer userId);

    /**
     * 把redis持久化到mysql
     */
    void transLikedFromRedis2DB();
}
