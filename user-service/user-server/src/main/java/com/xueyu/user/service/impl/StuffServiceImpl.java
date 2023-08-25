package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.mapper.ItemMapper;
import com.xueyu.user.mapper.ItemViewMapper;
import com.xueyu.user.pojo.domain.Item;
import com.xueyu.user.pojo.vo.ItemView;
import com.xueyu.user.service.StuffService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author durance
 */
@Service
public class StuffServiceImpl extends ServiceImpl<ItemMapper, Item> implements StuffService {

    @Resource
    ItemViewMapper itemViewMapper;

    @Override
    public ListVO<ItemView> getItemByPage(Integer current, Integer size) {
        IPage<ItemView> page = new Page<>(current, size);
        itemViewMapper.selectPage(page, null);
        ListVO<ItemView> res = new ListVO<>();
        BeanUtils.copyProperties(page, res);
        res.setRecords(page.getRecords());
        return res;
    }

}
