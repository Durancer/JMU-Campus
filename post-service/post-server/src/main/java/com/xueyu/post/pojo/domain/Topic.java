package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("topic")
public class Topic {
    /**
     * 自增id
     */
    @TableId
    Integer id;

    /**
     * 话题名称
     */
    String name;

    /**
     *  话题创建时间
     */
    Date createTime;
}
