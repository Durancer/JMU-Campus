package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.PostTopicMapper;
import com.xueyu.post.mapper.TopicMapper;
import com.xueyu.post.pojo.domain.PostTopic;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Resource
    private TopicMapper topicMapper;

    @Resource
    PostTopicMapper postTopicMapper;

    @Override
    public int listByNamenumber(String name) {
        return topicMapper.listByNamenumber(name);
    }

    @Override
    public Boolean createTopic(List<String> topicNames) {
        // 统计目前数据库没有的话题
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Topic::getName, topicNames);
        List<Topic> topics = query().getBaseMapper().selectList(wrapper);
        for(Topic topic : topics){
            // 移除数据库中有的话题，将剩余话题插入
            topicNames.remove(topic.getName());
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Topic> insertList = new ArrayList<>();
        for (String name : topicNames) {
            Topic topic = new Topic();
            topic.setName(name);
            topic.setCreateTime(now);
            insertList.add(topic);
        }
        // 一次存入数据库未存在的话题
        super.saveBatch(insertList);
        log.info("数据库新增 -> {} 条话题， 插入报文 ->{}", topicNames.size(), topicNames);
        return true;
    }

    @Override
    public void checkTopicLength(String name){
        int TOPIC_MIN_LENGTH = 2;
        int TOPIC_MAX_LENGTH = 15;
        if(!(name.length() <= TOPIC_MAX_LENGTH && name.length() >= TOPIC_MIN_LENGTH)){
            throw new PostException("话题长度需要在 2-15个字符之间");
        }
    }

    @Override
    public Map<Integer, List<Topic>> getTopicByPostIds(List<Integer> postIds) {
        LambdaQueryWrapper<PostTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PostTopic::getPostId, postIds);
        List<PostTopic> postTopics = postTopicMapper.selectList(wrapper);
        // 统计所有要查询的话题id
        List<Integer> topicIds = new ArrayList<>();
        for (PostTopic postTopic : postTopics) {
            topicIds.add(postTopic.getTopicId());
        }
        // 查出所有涉及的 话题内容
        LambdaQueryWrapper<Topic> topicWrapper = new LambdaQueryWrapper<>();
        if (!CollectionUtils.isEmpty(topicIds)) {
            topicWrapper.in(Topic::getId, topicIds);
        }
        List<Topic> topicList = query().getBaseMapper().selectList(topicWrapper);

        // 创建关联 map，减小查询复杂度  key : topicId, value : topic对象
        Map<Integer, Topic> topicMap = new HashMap<>();
        for (Topic topic : topicList) {
            topicMap.put(topic.getId(), topic);
        }
        // 创建响应对象
        Map<Integer, List<Topic>> res = new HashMap<>();
        for (PostTopic postTopic : postTopics) {
            List<Topic> resTopicList = res.get(postTopic.getPostId());
            if(CollectionUtils.isEmpty(resTopicList)){
                // 第一次未创建该集合，进行创建
                resTopicList = new ArrayList<>();
                res.put(postTopic.getPostId(), resTopicList);
            }
            // 直接从 topicMap 中拿出对象
            resTopicList.add(topicMap.get(postTopic.getTopicId()));
        }
        return res;
    }

}
