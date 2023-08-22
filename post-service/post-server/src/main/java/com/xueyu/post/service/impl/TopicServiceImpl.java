package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.mapper.TopicMapper;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Resource
    private TopicMapper topicMapper;
    @Override
    public int listByNamenumber(String name) {
        return topicMapper.listByNamenumber(name);
    }
    @Override
    public Boolean creatTopic(Topic topic) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        topic.setCreateTime(now);
        int topicNumber = topicMapper.listByNamenumber(topic.getName());
        if(topicNumber==0){
            topicMapper.insert(topic);
            return  true;
        }
        return false;
    }
}
