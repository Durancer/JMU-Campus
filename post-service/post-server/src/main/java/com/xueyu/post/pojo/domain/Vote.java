package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 投票表
 *
 * @author fj0591
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("vote")
public class Vote {

    /**
     * 投票id
     */
    @TableId
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
     * 投票周期
     */
    String cycle;

    /**
     * 投票总人数
     */
    Integer amount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    Date createTime;

}
