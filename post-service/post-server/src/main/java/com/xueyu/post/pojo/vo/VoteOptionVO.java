package com.xueyu.post.pojo.vo;

import lombok.Data;

/**
 * 投票选项显示实体类
 *
 * @author fj0591
 */
@Data
public class VoteOptionVO {

    /**
     * 选项id
     */
    Integer optionId;

    /**
     * 投票id
     */
    Integer voteId;

    /**
     * 内容
     */
    String content;

    /**
     * 投票人数
     */
    Integer num;

    /**
     * 投票占百分比
     */
    String ratio;

}
