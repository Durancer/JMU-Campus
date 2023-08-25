package com.xueyu.user.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.vo.ItemView;
import com.xueyu.user.service.StuffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user/stuff")
public class StuffController {

    @Resource
    StuffService stuffService;

    /**
     * 分页获取系统物品
     *
     * @param current 当前页
     * @param size 每页大小
     * @return 分页数据
     */
    @GetMapping("list")
    public RestResult<ListVO<ItemView>> getItemList(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size){
        ListVO<ItemView> itemByPage = stuffService.getItemByPage(current, size);
        return RestResult.ok(itemByPage);
    }

}