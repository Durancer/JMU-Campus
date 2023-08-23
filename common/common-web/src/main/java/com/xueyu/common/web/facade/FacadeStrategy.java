package com.xueyu.common.web.facade;

/**
 * 门面策略接口
 *
 * @param <T> 请求体
 * @param <E> 响应体
 * @author durancer
 */
public interface FacadeStrategy<T, E> {

    /**
     * 策略执行业务
     *
     * @param t 请求体
     * @return 响应体
     */
    E execBusiness(T t);

}
