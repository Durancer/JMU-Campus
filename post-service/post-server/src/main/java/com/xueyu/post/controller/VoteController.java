package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.service.VoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("post/vote")
public class VoteController {

    @Resource
    VoteService voteService;

    @PostMapping("launch")
    public RestResult<?> launchVote(Vote vote, String[] options){
        boolean status = voteService.launchVote(vote,options);
        if(!status){
            return RestResult.fail("发布投票失败");
        }
        return RestResult.ok("发布成功");
    }

    @PostMapping("delete")
    public RestResult<?> deleteVote(Integer voteId){
        boolean status = voteService.deleteVote(voteId);
        if(!status){
            return RestResult.fail("删除投票失败");
        }
        return RestResult.ok("删除投票成功");
    }

    @GetMapping("getone")
    public RestResult<VoteVO> getVoteDetail(@RequestParam Integer postId){
        return RestResult.ok(voteService.getVoteDetail(postId));
    }

}
