package com.xueyu.post.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 热门帖子
 *
 * @author LENOVO
 * @date 2023/07/15
 */
@Data
public class HotPostVO extends PostView{

    /**
     * 分数
     */
    Integer score;

}
