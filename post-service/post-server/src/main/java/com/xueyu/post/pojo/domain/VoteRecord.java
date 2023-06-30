package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 投票记录表
 *
 * @author fj0591
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("vote_record")
public class VoteRecord {

    /**
     * 投票记录id
     */
    @TableId
    Integer recordId;

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 选项id
     */
    Integer optionId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    Timestamp createTime;

}
