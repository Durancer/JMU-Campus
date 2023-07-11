package com.xueyu.search.pojo;

import lombok.Data;

@Data
public class UserSearchVO {

    /**
    * 搜索关键字
    */
    String searchWords;
    /**
    * 当前页
    */
    int page;
    /**
    * 分页条数
    */
    int pageSize;

}