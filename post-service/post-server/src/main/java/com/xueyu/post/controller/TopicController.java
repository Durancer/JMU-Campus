package com.xueyu.post.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.service.PostService;
import com.xueyu.post.service.TopicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 话题服务接口
 */
@RestController
@RequestMapping("post/topic")
public class TopicController {

    @Resource
    private TopicService topicService;

    @Resource
    PostService postService;

    /**
     * 查看所有话题
     * todo 改为分页获取
     *
     * @return 所有话题详细信息
     */
    @GetMapping("all")
    public RestResult<List> getAllTopic(){
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
    public RestResult<List<Topic>> listByName(String name, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Topic::getName, name);
        List<Topic> topicList = topicService.list(wrapper);
        return RestResult.ok(topicList);
    }

    /**
     * 添加话题
     *
     * @param name 话题内容
     * @return 添加结果
     */
    @PostMapping("add")
    public RestResult<?> addTopic(String name){
        Boolean result = topicService.createTopic(Arrays.asList(name));
        if(!result){
            return RestResult.fail("创建失败");
        }
        return RestResult.ok(null,"创建成功");
    }

    /**
     * 通过话题获取帖子列表
     *
     * @param name 话题名称
     * @param userId 用户id
     * @return 所有帖子详细信息
     */
    @GetMapping("list")
    public RestResult<List<PostListVO>> getPostListByTopic(String name, @RequestHeader(required = false) Integer userId){
        List<PostListVO> postListInfo = postService.getPostListByTopicIds(name, userId);
        return RestResult.ok(postListInfo);
    }

}
