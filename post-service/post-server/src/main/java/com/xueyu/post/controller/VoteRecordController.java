package com.xueyu.post.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.service.VoteRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("post/vote/record")
public class VoteRecordController {

    @Resource
    VoteRecordService voteRecordService;

    /**
     * 添加投票记录
     *
     * @param userId    用户id
     * @param voteId    投票id
     * @param optionIds 选择id
     * @return {@link RestResult}<{@link ?}>
     */
    @PostMapping("add")
    public RestResult<?> addVoteRecord(@RequestHeader Integer userId, Integer voteId, Integer[] optionIds){
        boolean status = voteRecordService.addVoteRecord(userId,voteId,optionIds);
        if(!status){
            return RestResult.fail("投票失败");
        }
        return RestResult.ok("投票成功");
    }

    /**
     * 是否投票
     *
     * @param userId 用户id
     * @param voteId 投票id
     * @return {@link RestResult}<{@link List}<{@link Integer}>>
     */
    @GetMapping("is")
    public RestResult<List<Integer>> isVote(@RequestHeader Integer userId, Integer voteId){
        return RestResult.ok(voteRecordService.isVote(userId,voteId));
    }

}
