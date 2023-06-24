package com.xueyu.comment.service;

import com.xueyu.comment.pojo.domain.Like;
import java.util.Map;

public interface RedisService {

    /**
     * redis点赞
     *
     * @param userId    用户id
     * @param commentId 评论id
     */
    void likes(Integer userId, Integer commentId);

    /**
     * redis取消点赞
     *
     * @param userId    用户id
     * @param commentId 评论id
     */
    void unLikes(Integer userId, Integer commentId);


    /**
     * 获取Redis中存储的所有点赞数据
     *
     * @return {@link Map}<{@link Like},{@link Integer}>
     */
    Map<Like,String> getLikedDataFromRedis();

    /**
     * 获得redis中点赞的状态
     *
     * @return {@link String}
     */
    String[] getStatus(String value);

}
