package com.xueyu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyu.user.pojo.domain.Item;

import java.util.List;

/**
 * @author durance
 */
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 增加物品发送数量（指系统一共派出多少）
     *
     * @param id 物品id
     * @param num 增加数量
     * @return 影响行数
     */
    int updateSendNumById(Integer id, Integer num);

    /**
     * 查询物品关联表里已有物品的用户
     *
     * @return id集合
     */
    List<Integer> selectDiffUserIds();

}
