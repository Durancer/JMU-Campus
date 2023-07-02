package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.vo.VoteVO;

public interface VoteService extends IService<Vote> {

    /**
     * 发起投票
     *
     * @param vote    投票
     * @param options 选项
     * @return boolean
     */
    boolean launchVote(Vote vote,String[] options);

    /**
     * 删除投票
     *
     * @param voteId 投票id
     * @return boolean
     */
    boolean deleteVote(Integer voteId);

    /**
     * 获得投票信息
     *
     * @param postId post id
     * @return {@link VoteVO}
     */
    VoteVO getVoteDetail(Integer postId);

    /**
     * 投票是否过期
     *
     * @param vote 投票
     * @return {@link Boolean}
     */
    Boolean isVoteExpired(Vote vote);

}
