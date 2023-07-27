package com.xueyu.search.controller;

import com.xueyu.common.core.result.RestResult;
import com.xueyu.search.pojo.UserSearchVO;
import com.xueyu.search.service.PostSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 帖子搜索接口
 *
 * @author fsj0591
 */
@RestController
@RequestMapping("post/search")
public class PostSearchController {

    @Resource
    PostSearchService postSearchService;

    /**
     * es分页搜索
     *
     * @param userSearchVO 用户搜索类
     * @return {@link RestResult}<{@link List}>
     */
    @PostMapping("page")
    public RestResult<List> searchByPage(UserSearchVO userSearchVO){
        return RestResult.ok(postSearchService.searchByPage(userSearchVO));
    }


}
