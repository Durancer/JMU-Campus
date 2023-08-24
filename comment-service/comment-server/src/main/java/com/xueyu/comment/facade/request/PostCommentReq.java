package com.xueyu.comment.facade.request;

import com.xueyu.comment.pojo.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求体
 *
 * @author durance
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCommentReq {

    /**
     * 当前操作用户id
     */
    Integer userId;

    /**
     * 评论列表
     */
    List<Comment> comments;

}
