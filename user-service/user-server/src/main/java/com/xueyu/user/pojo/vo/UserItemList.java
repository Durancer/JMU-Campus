package com.xueyu.user.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户物品列表
 *
 * @author durance
 */
@NoArgsConstructor
@Data
public class UserItemList {


    /**
     * 自增id
     */
    Integer id;

    /**
     * 名称
     */
    String nickname;

    /**
     * 头像路径
     */
    String avatarUrl;

    /**
     * 用户邮箱
     */
    String email;

    /**
     * 性别 0 匿 | 1 boy | 2 girl
     */
    Integer sex;

    /**
     * 商品信息
     */
    List<UserItemView> userItemList;

}
