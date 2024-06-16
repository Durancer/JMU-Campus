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
     * @param postId 帖子id
     * @return boolean
     */
    boolean deletePostVote(Integer postId);

    /**
     * 获得投票信息
     *
     * @param postId post id
     * @return {@link VoteVO}
     */
    VoteVO getVoteDetail(Integer postId, Integer userId);

}
