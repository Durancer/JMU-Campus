package com.xueyu.comment.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fsj0591
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment_like")
public class Like {

    /**
     * 点赞表主键
     */
    @TableId
    Integer likeId;

    /**
     * 点赞用户id
     */
    Integer userId;

    /**
     * 评论id
     */
    Integer commentId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    Date createTime;

}
