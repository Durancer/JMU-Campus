package com.xueyu.post.service;

import com.xueyu.post.pojo.vo.HotPostVO;
import com.xueyu.post.pojo.vo.PostListVO;

import java.util.List;

/**
 * @author fsj0591
 */
public interface HotPostService {

    /**
     * 搜索热门帖子
     */
    void searchHotPost();

    List<PostListVO> getHotPostList(Integer userId);
}
