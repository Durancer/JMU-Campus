package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    int listByNamenumber(String name);
    Boolean insertPostTopic(HashMap<String,Integer> map);
}
