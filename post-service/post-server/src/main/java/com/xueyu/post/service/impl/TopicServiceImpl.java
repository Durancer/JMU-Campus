package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.mapper.TopicMapper;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Override
    public int listByNamenumber(String name) {
        return topicMapper.listByNamenumber(name);
    }
}
