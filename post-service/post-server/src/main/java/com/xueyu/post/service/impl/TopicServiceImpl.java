package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.PostTopicMapper;
import com.xueyu.post.mapper.TopicMapper;
import com.xueyu.post.pojo.domain.PostTopic;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    public Boolean createTopic(List<String> topicNames, Integer postId) {
        // 统计目前数据库没有的话题
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Topic::getName, topicNames);
        List<Topic> topics = query().getBaseMapper().selectList(wrapper);
        // 去掉已存在的topic
        Set<String> requestTopics = new HashSet<>(topicNames);
        Set<String> existTopic = topics.stream().map(Topic::getName).collect(Collectors.toSet());
        requestTopics = requestTopics.stream().filter(topic -> !existTopic.contains(topic)).collect(Collectors.toSet());

        Date now = new Date();
        List<Topic> insertList = requestTopics.stream().map(topicName ->{
            Topic topic = new Topic();
            topic.setName(topicName);
            topic.setCreateTime(now);
            return topic;
        }).collect(Collectors.toList());
        // 一次存入数据库未存在的话题
        saveBatch(insertList);
        log.info("数据库新增话题插入报文 ->{}", topicNames);
        // 如果帖子id为空，不插入关联数据
        if(Objects.isNull(postId)){
            return true;
        }
        // 插入关联数据
        ArrayList<HashMap> maps = new ArrayList<>();
        LambdaQueryWrapper<Topic> linkWrapper = new LambdaQueryWrapper<>();
        wrapper.in(Topic::getName, topicNames);
        List<Topic> linkTopics = topicMapper.selectList(linkWrapper);
        if(!linkTopics.isEmpty()){
            for (Topic topic : linkTopics) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("topicId", topic.getId());
                map.put("postId", postId);
                maps.add(map);
            }
        }
        topicMapper.insertPostTopics(maps);
        return true;
    }

    @Override
    public void checkTopicLength(String name){
        final int TOPIC_MIN_LENGTH = 2;
        final int TOPIC_MAX_LENGTH = 15;
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

    @Override
    public ListVO<PostListVO> getPostListByTopic(Integer current, Integer size, Integer userId, String name) {

        return null;
    }
}
