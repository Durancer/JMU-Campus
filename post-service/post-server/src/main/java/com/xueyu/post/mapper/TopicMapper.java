package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


public interface TopicMapper extends BaseMapper<Topic> {
    int listByNamenumber(String name);
    Boolean insertPostTopics(ArrayList<HashMap> list);
}
