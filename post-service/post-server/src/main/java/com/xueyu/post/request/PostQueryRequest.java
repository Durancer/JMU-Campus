package com.xueyu.post.request;

import com.xueyu.common.core.request.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * 帖子查询请求
 *
 * @author durance
 */
@Data
public class PostQueryRequest extends PageRequest {

    /**
     * 自增id
     */
    Integer id;

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 帖子标题
     */
    String title;

    /**
     * 帖子文本内容
     */
    String content;

    /**
     * 帖子创建时间
     */
    Date createTime;

    /**
     * 帖子状态 0为审核中 | 1为公开
     */
    Integer status;

    /**
     * 帖子状态 0 公开 | 1 私密
     */
    Integer isPrivate;

}
