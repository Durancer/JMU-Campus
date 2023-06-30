package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Vote;

public interface VoteService extends IService<Vote> {
    boolean addVote(Vote vote,String[] options);
}
