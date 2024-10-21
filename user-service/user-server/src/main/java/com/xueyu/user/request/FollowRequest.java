package com.xueyu.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * 关注请求
 *
 * @author durance
 */
@Data
@NoArgsConstructor
public class FollowRequest {

    /**
     * 用户id
     */
    @NotNull
    Integer userId;

    /**
     * 关注id
     */
    @NotNull
    Integer followId;

}
