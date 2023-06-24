package com.xueyu.comment.utils;

import java.sql.Timestamp;

public class RedisKeyUtils {

    /**
     * 评论缓存key
     */
    public static final String CACHE_COMMENT_LIKE = "COMMENT_LIKE";

    /**
     * 用户被点赞数量key
     */
    //public static final String CACHE_COMMENT_LIKE_COUNT = "COMMENT_LIKE_COUNT";

    public static final String LIKE = "1";

    public static final String UNLIKE = "0";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     *
     * @param userId    用户id
     * @param commentId 评论id
     * @return {@link String}
     */
    public static String getLikedKey(Integer userId, Integer commentId){
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append("::");
        builder.append(commentId);
        return builder.toString();
    }

    /**
     * 拼接点赞状态和点赞时间作为key。格式 1::2023-6-24
     *
     * @param status 状态
     * @param time   时间
     * @return {@link String}
     */
    public static String getValue(String status, Timestamp time){
        StringBuilder builder = new StringBuilder();
        builder.append(status);
        builder.append("&");
        builder.append(time);
        return builder.toString();
    }

}
