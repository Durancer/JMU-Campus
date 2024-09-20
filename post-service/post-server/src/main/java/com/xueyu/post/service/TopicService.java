package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.request.PageRequest;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.pojo.vo.PostListVO;

import java.util.List;
import java.util.Map;


/**
 * @author renmengzhuo
 */
public interface TopicService extends IService<Topic> {

    int listByNamenumber(String name);

    /**
     * 创建话题
     *
     * @param topicNames 话题名称列表
     * @param postId 帖子id
     * @return
     */
    Boolean createTopic(List<String> topicNames, Integer postId);

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

    /**
     * 分页查询话题
     *
     * @param request 当前页
     * @param name  话题名称
     * @return 分页数据
     */
    ListVO<Topic> getPageTopic(PageRequest request, String name);

    /**
     * 按照话题分页查询帖子列表
     *
     * @param request 当前页
     * @param userId  用户id
     * @param name  话题名称
     * @return 分页数据
     */
    ListVO<PostListVO> getPostListByTopic(PageRequest request, Integer userId, String name);

}
