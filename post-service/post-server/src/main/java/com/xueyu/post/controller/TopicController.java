package com.xueyu.post.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * 话题服务接口
 */
@RestController
@RequestMapping("topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    /**
     * 查看所有话题
     *
     * @return 所有话题详细信息
     */
    @GetMapping("list")
    public RestResult list(){
        List<Topic> list = topicService.list();
        return RestResult.ok(list);

    }
    /**
     * 模糊搜索话题
     *
     * @param name 话题名字
     * @return 话题信息
     */
    @GetMapping("listByName")
    public RestResult  listByName(String name){
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Topic::getName,name);
        List<Topic> topicList = topicService.list(wrapper);
        return RestResult.ok(topicList);
    }
    //
    /**
     * 添加话题
     *
     * @param topic 话题内容
     * @return 添加结果
     */
    @PostMapping("add")
    public RestResult add(Topic topic){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        topic.setCreateTime(now);
        int TOPIC_LENGTH=15;
        int topic_length = topic.getName().length();
        if(topic_length>TOPIC_LENGTH){
            return RestResult.fail("话题长度不能超过15个字");
        }
        int topicNumber = topicService.listByNamenumber(topic.getName());
        if(topicNumber==0){
            topicService.save(topic);
            return RestResult.ok(200,"创建成功");
        }
        return RestResult.fail("该话题已经被创建");
    }
}
