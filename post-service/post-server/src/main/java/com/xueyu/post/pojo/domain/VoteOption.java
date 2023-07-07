package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投票选项表
 *
 * @author fj0591
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("vote_option")
public class VoteOption {

    /**
     * 选项id
     */
    @TableId
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

}
