package com.xueyu.user.sdk.pojo.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户物品枚举类,字段对应数据库
 *
 * @author durance
 */
@AllArgsConstructor
@Getter
public enum UserStuffEnum {

    /**
     * 流量券
     */
    Traffix(1, "流量券", "用来给帖子增加流量", "traffic.jpg");

    /**
     * 物品id
     */
    Integer id;

    /**
     * 物品名称
     */
    String name;

    /**
     * 物品介绍，功能等
     */
    String desc;

    /**
     * 物品封面图
     */
    String image;

}
