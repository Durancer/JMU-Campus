package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Topic;

import java.util.List;
import java.util.Map;


public interface TopicService extends IService<Topic> {

    int listByNamenumber(String name);

    /**
     * 创建话题
     *
     * @param topicNames 话题名称列表
     * @return
     */
    Boolean createTopic(List<String> topicNames);

    /**
     * 判断话题长度
     *
     * @param name 话题名称
     * @return
     */
    void checkTopicLength(String name);

    /**
     * 根据帖子id查询话题信息
     *
     * @param postIds 帖子id
     * @return key 为 帖子id， value为 该帖子携带的话题
     */
    Map<Integer, List<Topic>> getTopicByPostIds(List<Integer> postIds);

//    /**
//     * 按照话题分页查询帖子列表
//     *
//     * @param current 当前页
//     * @param size    每页大小
//     * @param userId  用户id
//     * @param name  话题名称
//     * @return 分页数据
//     */
//    ListVO<PostListVO> getPostListByTopic(Integer current, Integer size, Integer userId, String name);

}
