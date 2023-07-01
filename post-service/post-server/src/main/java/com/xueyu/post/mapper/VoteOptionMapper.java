package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.VoteOption;
import org.apache.ibatis.annotations.Param;

public interface VoteOptionMapper extends BaseMapper<VoteOption> {

    /**
     * 添加投票选项人数
     *
     * @param optionId 选择id
     */
    void addVoteOptionNum(@Param("optionId") Integer optionId);

}
