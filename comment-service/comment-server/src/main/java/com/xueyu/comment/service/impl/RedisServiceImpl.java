package com.xueyu.comment.service.impl;

import com.xueyu.comment.mapper.CommentMapper;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.service.RedisService;
import com.xueyu.comment.utils.RedisKeyUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.xueyu.comment.utils.RedisKeyUtils.*;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    CommentMapper commentMapper;

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Override
    public void likes(Integer userId, Integer commentId) {
        String likedKey = RedisKeyUtils.getLikedKey(userId, commentId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String value = RedisKeyUtils.getValue(LIKE,time);
        redisTemplate.opsForHash().put(CACHE_COMMENT_LIKE, likedKey, value);
        commentMapper.updateLikeNum(commentId,1);
    }

    @Override
    public void unLikes(Integer userId, Integer commentId) {
        String likedKey = RedisKeyUtils.getLikedKey(userId, commentId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String value = RedisKeyUtils.getValue(UNLIKE,time);
        redisTemplate.opsForHash().put(CACHE_COMMENT_LIKE, likedKey,value);
        commentMapper.updateLikeNum(commentId,-1);
    }

    @Override
    public Map<Like,String> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(CACHE_COMMENT_LIKE, ScanOptions.NONE);
        Map<Like,String> likeStatusMap = new HashMap<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 userId,commentId
            String[] split = key.split("::");
            Integer userId = Integer.parseInt(split[0]);
            Integer commentId = Integer.parseInt(split[1]);
            String value = (String) entry.getValue() ;

            //把点赞数据存入likeStatusMap
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Like like = new Like();
            like.setUserId(userId);
            like.setCommentId(commentId);
            like.setCreateTime(time);
            likeStatusMap.put(like,value);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(CACHE_COMMENT_LIKE, key);
        }
        return likeStatusMap;
    }

    @Override
    public String[] getStatus(String value) {
        return value.split("&");
    }
}
