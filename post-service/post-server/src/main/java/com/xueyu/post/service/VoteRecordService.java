package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.VoteRecord;

import java.util.List;

public interface VoteRecordService extends IService<VoteRecord> {
    boolean addVoteRecord(Integer userId, Integer voteId, Integer[] optionIds);

    List<Integer> isVote(Integer userId, Integer voteId);
}
