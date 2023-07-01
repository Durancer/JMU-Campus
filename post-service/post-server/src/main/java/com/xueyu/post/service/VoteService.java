package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.vo.VoteVO;

public interface VoteService extends IService<Vote> {
    boolean launchVote(Vote vote,String[] options);

    boolean deleteVote(Integer voteId);

    VoteVO getVoteDetail(Integer postId);

    Boolean isVoteExpired(Vote vote);
}
