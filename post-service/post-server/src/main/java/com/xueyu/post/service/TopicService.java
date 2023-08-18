package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.post.pojo.domain.Topic;


public interface TopicService extends IService<Topic> {
    int listByNamenumber(String name);
}
