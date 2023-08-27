package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.mapper.*;
import com.xueyu.user.pojo.domain.Item;
import com.xueyu.user.pojo.domain.UserItem;
import com.xueyu.user.pojo.vo.ItemView;
import com.xueyu.user.pojo.vo.UserItemList;
import com.xueyu.user.pojo.vo.UserItemView;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.service.StuffService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author durance
 */
@Service
public class StuffServiceImpl extends ServiceImpl<ItemMapper, Item> implements StuffService {

    @Resource
    ItemViewMapper itemViewMapper;

    @Resource
    UserViewMapper userViewMapper;

    @Resource
    UserItemViewMapper userItemViewMapper;

    @Resource
    ItemMapper itemMapper;

    @Resource
    UserItemMapper userItemMapper;

    @Override
    public ListVO<ItemView> getItemByPage(Integer current, Integer size) {
        IPage<ItemView> page = new Page<>(current, size);
        itemViewMapper.selectPage(page, null);
        ListVO<ItemView> res = new ListVO<>();
        BeanUtils.copyProperties(page, res);
        res.setRecords(page.getRecords());
        return res;
    }


    @Override
    public UserItemList getUserStuff(Integer userId) {
        LambdaQueryWrapper<UserItemView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserItemView::getUserId, userId);
        List<UserItemView> userItemViews = userItemViewMapper.selectList(wrapper);
        UserView userView = userViewMapper.selectById(userId);
        // 创建返回对象，封装属性
        UserItemList userItemList = new UserItemList();
        BeanUtils.copyProperties(userView, userItemList);
        userItemList.setUserItemList(userItemViews);
        return userItemList;
    }

    @Override
    public List<UserItemList> getUserListStuff(List<Integer> userIds) {
        // 查出所有用户信息,并封装为 map，减低事件复杂度
        LambdaQueryWrapper<UserView> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.in(UserView::getId, userIds);
        List<UserView> userViews = userViewMapper.selectList(userWrapper);
        Map<Integer, UserView> userViewMap = new HashMap<>();
        for (UserView userView : userViews) {
            userViewMap.put(userView.getId(), userView);
        }
        // 查出所有用户所拥有的物品信息
        LambdaQueryWrapper<UserItemView> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.in(UserItemView::getUserId, userIds);
        List<UserItemView> userItemViews = userItemViewMapper.selectList(itemWrapper);
        // 将查出来的商品进行分组
        Map<Integer, List<UserItemView>> itemsMap = new HashMap<>();
        for (UserItemView userItemView : userItemViews) {
            List<UserItemView> originItems = itemsMap.getOrDefault(userItemView.getUserId(), new ArrayList<>());
            originItems.add(userItemView);
            itemsMap.put(userItemView.getUserId(), originItems);
        }
        // 创建总响应体
        List<UserItemList> res = new ArrayList<>();
        for(Integer userId : userIds){
            // 创建单个用户的物品响应体
            UserItemList userItemList = new UserItemList();
            // 查找用户信息并赋值
            UserView userView = userViewMap.get(userId);
            BeanUtils.copyProperties(userView, userItemList);
            // 赋值商品信息
            userItemList.setUserItemList(itemsMap.get(userId));
            res.add(userItemList);
        }
        return res;
    }

    @Override
    public ListVO<UserItemList> getAllUserStuff(Integer current, Integer size) {
        QueryWrapper<UserItem> wrapper = new QueryWrapper<>();
        wrapper.select("distinct user_id");
        IPage<UserItem> page = new Page<>(current, size);
        userItemMapper.selectPage(page, wrapper);
        ListVO<UserItemList> res = new ListVO<>();
        BeanUtils.copyProperties(page, res);
        // 统计用户id
        List<Integer> userIds = new ArrayList<>();
        for (UserItem record : page.getRecords()) {
            userIds.add(record.getUserId());
        }
        List<UserItemList> userListStuff = getUserListStuff(userIds);
        res.setRecords(userListStuff);
        return res;
    }
}
