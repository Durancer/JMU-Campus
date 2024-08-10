package com.xueyu.comment.request;



import com.xueyu.common.core.request.PageRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author durance
 */
@Data
@NoArgsConstructor
public class PostCommentQueryRequest extends PageRequest {

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 帖子id
     */
    @NotNull
    Integer postId;

}
