package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.Post;

import java.util.List;

/**
 * @author durance
 */
public interface PostMapper extends BaseMapper<Post> {
    List<Integer> getPostDetailInfoByTopiId(Integer topicId);

}
