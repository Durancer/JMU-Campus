package com.xueyu.post.pojo.vo;

import com.xueyu.post.pojo.domain.VoteOption;
import lombok.Data;
import java.util.List;

/**
 * 投票显示实体类
 *
 * @author fj0591
 */
@Data
public class VoteVO {

    /**
     * 投票id
     */
    Integer voteId;

    /**
     * 帖子id
     */
    Integer postId;

    /**
     * 投票主题
     */
    String topic;

    /**
     * 投票类型
     */
    String type;

    /**
     * 投票是否过期
     */
    Boolean expired;

    /**
     * 投票总人数
     */
    Integer amount;

    /**
     * 选项列表
     */
    List<VoteOption> optionList;
}
