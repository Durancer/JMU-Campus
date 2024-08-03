package com.xueyu.comment.request;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xueyu.common.web.request.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * @author durance
 */
@Data
public class CommentQueryRequest extends PageRequest {

    /**
     * 自增id
     */
    Integer id;

    /**
     * 帖子id
     */
    Integer postId;

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 回复的用户id，为根评论时此项为null
     */
    Integer toUserId;

    /**
     * 根评论id，本身为根评论则为 id
     */
    Integer rootId;

    /**
     * 评论内容
     */
    String content;

    /**
     * 评论类型
     */
    String type;

    /**
     * 创建时间
     */
    Date createTime;

}
