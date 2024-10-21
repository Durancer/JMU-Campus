package com.xueyu.user.request;

import com.xueyu.common.core.request.PageRequest;
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
public class FollowGetRequest extends PageRequest {

    /**
     * 用户id
     */
    @NotNull
    Integer userId;

}
