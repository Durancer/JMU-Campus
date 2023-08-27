package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.pojo.domain.Item;
import com.xueyu.user.pojo.vo.ItemView;
import com.xueyu.user.pojo.vo.UserItemList;

import java.util.List;


/**
 * @author durance
 */
public interface StuffService extends IService<Item> {

    /**
     * 分页获取系统物品
     *
     * @param current 当前页
     * @param size 每页大小
     * @return 分页数据
     */
    ListVO<ItemView> getItemByPage(Integer current, Integer size);


    /**
     * 获取用户物品信息
     *
     * @param userId 用户id
     * @return 用户物品信息
     */
    UserItemList getUserStuff(Integer userId);

    /**
     * 获取指定id多个用户物品信息
     *
     * @param userIds 用户id
     * @return 用户物品信息
     */
    List<UserItemList> getUserListStuff(List<Integer> userIds);

    /**
     * 分页获取所有用户的物品信息
     *
     * @return 分页数据
     */
    ListVO<UserItemList> getAllUserStuff(Integer current, Integer size);

}
