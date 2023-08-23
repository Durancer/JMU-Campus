package com.xueyu.post.facade.request;

import com.xueyu.post.pojo.vo.PostView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertDetailReq {

    /**
     * 用户id
     */
    Integer userId;

    /**
     * 帖子信息
     */
    PostView postView;

}
