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
    Integer current = 1;

    /**
     * 页大小
     */
    Integer size = 2;

}
