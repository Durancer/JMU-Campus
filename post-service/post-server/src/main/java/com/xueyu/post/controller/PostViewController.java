package com.xueyu.post.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.PostQueryRequest;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.service.PostViewService;
import com.xueyu.post.service.VoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author durance
 */
@RestController
@RequestMapping("post/manage")
public class PostViewController {

    @Resource
    PostViewService postViewService;

    /**
     * 获得帖子列表，// todo 缺少整合投票、话题信息
     *
     * @param request req
     */
    @GetMapping("list")
    public RestResult<ListVO<PostView>> getManagePostList(PostQueryRequest request){
        return RestResult.ok(postViewService.getManagePostListPage(request));
    }

}
