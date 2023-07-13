package com.xueyu.search.service;

import com.xueyu.search.pojo.UserSearchVO;
import java.util.List;

public interface PostSearchService {

    /**
     * es分页搜索
     *
     * @param userSearchVO 用户搜索类
     * @return {@link List}
     */
    List searchByPage(UserSearchVO userSearchVO);
}
