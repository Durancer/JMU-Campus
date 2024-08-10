package com.xueyu.common.core.request;

import lombok.Data;

import java.util.Objects;

/**
 * 分页请求基类
 *
 * @author durance
 */
@Data
public class PageRequest {

    /**
     * 当前页
     */
    Integer current;

    /**
     * 页大小
     */
    Integer size;

    /**
     * 默认1
     * @return 当前页
     */
    public Integer getCurrent() {
        if (Objects.isNull(current)){
            return 1;
        }
        return current;
    }

    /**
     * 默认10
     * @return 页大小
     */
    public Integer getSize() {
        if (Objects.isNull(size)){
            return 10;
        }
        return size;
    }

}
