package com.xueyu.post.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * 话题服务接口
 */
@RestController
@RequestMapping("post/topic")
public class TopicController {
    @Resource
    private TopicService topicService;
    /**
     * 查看所有话题
     *
     * @return 所有话题详细信息
     */
    @GetMapping("list")
    public RestResult<List> list(){
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
    public RestResult<List>  listByName(String name){
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
    public RestResult<?> add(Topic topic){
        int TOPIC_LENGTH=15;
        if(topic.getName().length()>TOPIC_LENGTH){
            throw new PostException("话题长度不能超过15个字");
        }
        Boolean result = topicService.creatTopic(topic);
        if(!result){
            return RestResult.fail("创建失败");
        }
        return RestResult.ok(null,"创建成功");
    }
}
