package com.xueyu.common.data.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：mofan
 * @Date：2023/3/5 12:49
 * @version：1.0
 * @Description：
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{
    /**
     * 插入时的填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject)
    {
        log.info("start insert fill.....");
        this.setFieldValByName("createTime", new Date(), metaObject);
    }

    /**
     * 更新时的填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject)
    {
        log.info("start update fill.....");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}