package com.xueyu.search.pojo;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class SearchPostVO {


    /**
     * 帖子id
     */
    private Long id;


    /**
     * 标题
     */
    private String title;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Timestamp createTime;

}