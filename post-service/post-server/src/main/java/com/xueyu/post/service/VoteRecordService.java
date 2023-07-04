package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.domain.VoteRecord;

import java.util.List;

public interface VoteRecordService extends IService<VoteRecord> {

    /**
     * 添加投票记录
     *
     * @param userId    用户id
     * @param voteId    投票id
     * @param optionIds 选择id
     * @return boolean
     */
    boolean addVoteRecord(Integer userId, Integer voteId, Integer[] optionIds);

    /**
     * 是否投票
     *
     * @param userId 用户id
     * @param voteId 投票id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> isVote(Integer userId, Integer voteId);

    /**
     * 投票是否过期
     *
     * @param vote 投票
     * @return {@link Boolean}
     */
    Boolean isVoteExpired(Vote vote);
}
