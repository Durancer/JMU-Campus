package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.vo.HotPostVO;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.service.HotPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("post/hot")
public class HotPostController {

    @Resource
    HotPostService hotPostService;

    /**
     * 获得热门帖子
     *
     * @return {@link RestResult}<{@link PostListVO}>
     */
    @GetMapping("list")
    public RestResult<List<PostListVO>> getHotPost(@RequestHeader(required = false) Integer userId) {
        return RestResult.ok(hotPostService.getHotPostList(userId));
    }

}
