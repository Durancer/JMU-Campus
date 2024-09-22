package com.xueyu.post.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.request.PostQueryRequest;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.service.PostViewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author durance
 */
@RestController
@RequestMapping("post")
public class PostViewController {

    @Resource
    PostViewService postViewService;

    /**
     * 获得帖子列表，// todo 缺少整合投票、话题信息
     *
     * @param request req
     */
    @GetMapping("manage/list")
    public RestResult<ListVO<PostView>> getManagePostList(PostQueryRequest request){
        return RestResult.ok(postViewService.getManagePostListPage(request));
    }

    /**
     * 获得帖子列表
     *
     * @param userId userId
     */
    @GetMapping("user/top")
    public RestResult<List<PostView>> getUserTopPost(Integer userId){
        return RestResult.ok(postViewService.getUserTopPost(userId));
    }

}
