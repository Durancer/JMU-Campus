package com.xueyu.post.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 帖子与话题关联
 *
 * @author durance
 */
@Data
@TableName("post_topic")
public class PostTopic{

    /**
     * 帖子id
     */
    Integer postId;

    /**
     * 话题id
     */
    Integer topicId;

}
