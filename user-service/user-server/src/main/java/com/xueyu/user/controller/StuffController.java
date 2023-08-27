package com.xueyu.user.controller;

import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import com.xueyu.user.pojo.vo.ItemView;
import com.xueyu.user.pojo.vo.UserItemList;
import com.xueyu.user.service.StuffService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author durance
 */
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
    @GetMapping("list/all")
    public RestResult<ListVO<ItemView>> getItemList(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size){
        ListVO<ItemView> itemByPage = stuffService.getItemByPage(current, size);
        return RestResult.ok(itemByPage);
    }

    /**
     * 获取用户物品信息
     *
     * @param userId 用户id
     * @return 用户物品信息
     */
    @GetMapping("list/user")
    public RestResult<UserItemList> getUserItemList(@RequestHeader Integer userId){
        UserItemList userStuff = stuffService.getUserStuff(userId);
        return RestResult.ok(userStuff);
    }

    /**
     * 分页获取所有用户物品信息
     *
     * @param current 当前页
     * @param size 页大小
     * @return 用户物品信息
     */
    @GetMapping("list/all/user")
    public RestResult<ListVO<UserItemList>> getAllUserItemList(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size){
        ListVO<UserItemList> allUserStuff = stuffService.getAllUserStuff(current, size);
        return RestResult.ok(allUserStuff);
    }

}