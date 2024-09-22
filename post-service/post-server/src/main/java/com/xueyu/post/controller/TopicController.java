package com.xueyu.post.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.common.core.request.PageRequest;
import com.xueyu.common.core.result.ListVO;
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

    /**
     * 模糊搜索话题
     *
     * @param request req
     * @param name 话题名字
     * @return 话题信息
     */
    @GetMapping("list")
    public RestResult<ListVO<Topic>> getPageTopicListByName(PageRequest request, String name) {
        ListVO<Topic> pageTopic = topicService.getPageTopic(request, name);
        return RestResult.ok(pageTopic);
    }

    /**
     * 添加话题
     *
     * @param name 话题内容
     * @return 添加结果
     */
    @PostMapping("add")
    public RestResult<?> addTopic(String name){
        Boolean result = topicService.createTopic(Arrays.asList(name), null);
        if(!result){
            return RestResult.fail("创建失败");
        }
        return RestResult.ok(null,"创建成功");
    }

    /**
     * 通过话题分页获取帖子列表
     *
     * @param name 话题名称
     * @param userId 用户id
     * @return 所有帖子详细信息
     */
    @GetMapping("list/post")
    public RestResult<ListVO<PostListVO>> getPostListByTopic(String name, @RequestHeader(required = false) Integer userId, PageRequest request){
        ListVO<PostListVO> postListInfo = topicService.getPostListByTopic(request, userId, name);
        return RestResult.ok(postListInfo);
    }

}
