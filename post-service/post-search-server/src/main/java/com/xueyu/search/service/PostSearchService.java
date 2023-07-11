package com.xueyu.search.service;

import com.xueyu.search.pojo.UserSearchVO;
import java.util.List;

public interface PostSearchService {
    List searchByPage(UserSearchVO userSearchVO);
}
