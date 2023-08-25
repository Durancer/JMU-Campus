package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.pojo.domain.Item;
import com.xueyu.user.pojo.vo.ItemView;


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

}
