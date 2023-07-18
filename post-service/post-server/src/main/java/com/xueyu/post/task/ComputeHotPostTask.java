package com.xueyu.post.task;

import com.xueyu.post.service.HotPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fsj0591
 * @date 2023/07/15
 */
@Component
@Slf4j
public class ComputeHotPostTask {

    @Resource
    HotPostService hotPostService;

    @Scheduled(cron = "0 59 23 * * ?")
    public void computeHotPost(){
        log.info("计算热门帖子定时任务开始");
        hotPostService.searchHotPost();
        log.info("计算热门帖子定时任务结束");
    }

}
