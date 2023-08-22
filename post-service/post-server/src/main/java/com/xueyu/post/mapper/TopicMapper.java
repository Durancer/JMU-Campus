package com.xueyu.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.post.pojo.domain.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface TopicMapper extends BaseMapper<Topic> {

    int listByNamenumber(String name);

    /**
     * 批量插入话题
     *
     * @param list 话题列表
     * @return 影响行数
     */
    Boolean insertPostTopics(ArrayList<HashMap> list);

    /**
     * 查询出帖子携带的id
     *
     * @param postId 帖子id
     * @return 话题信息
     */
    List<Topic> selectByPostId(Integer postId);

}
