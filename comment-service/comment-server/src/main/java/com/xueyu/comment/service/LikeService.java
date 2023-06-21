package com.xueyu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.pojo.vo.LikeVO;

import java.util.List;

public interface LikeService extends IService<Like> {

    boolean isLike(Integer userId,Integer commentId);

    boolean like(Integer userId, Integer commentId);

    List<LikeVO> likeCommons(Integer userId);

    List<LikeVO> likeByCommons(Integer userId);

}
