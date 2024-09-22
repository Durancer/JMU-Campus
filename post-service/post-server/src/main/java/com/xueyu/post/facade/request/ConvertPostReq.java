package com.xueyu.post.facade.request;

import com.xueyu.post.pojo.vo.PostView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求体
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertPostReq {

    /**
     * 帖子id集合
     */
    List<Integer> postIds;

    /**
     * 帖子作者id集合
     */
    List<Integer> authors;

    /**
     * 帖子记录
     */
    List<PostView> records;

    /**
     * 请求用户id
     */
    Integer userId;

}
