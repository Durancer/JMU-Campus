package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.service.VoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("post/vote")
public class VoteController {

    @Resource
    VoteService voteService;

    @RequestMapping("add")
    public RestResult<?> addVote(Vote vote,String[] options){
        boolean status = voteService.addVote(vote,options);
        if(!status){
            return RestResult.fail("发布投票失败");
        }
        return RestResult.ok("发布成功");
    }

}
