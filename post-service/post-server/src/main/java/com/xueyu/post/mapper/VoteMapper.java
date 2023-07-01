package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.Vote;
import org.apache.ibatis.annotations.Param;

public interface VoteMapper extends BaseMapper<Vote> {

    /**
     * 添加投票总人数
     *
     * @param voteId 投票id
     */
    void addVoteAmount(@Param("voteId") Integer voteId);

}
