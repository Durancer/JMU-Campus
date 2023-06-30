package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.mapper.VoteMapper;
import com.xueyu.post.mapper.VoteOptionMapper;
import com.xueyu.post.mapper.VoteRecordMapper;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.service.VoteService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {

    @Resource
    VoteMapper voteMapper;

    @Resource
    VoteOptionMapper voteOptionMapper;

    @Resource
    VoteRecordMapper voteRecordMapper;

    @Override
    public boolean addVote(Vote vote,String[] options) {
        return false;
    }
}
