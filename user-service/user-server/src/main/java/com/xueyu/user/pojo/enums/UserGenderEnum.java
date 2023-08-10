package com.xueyu.user.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author durancer
 */
@AllArgsConstructor
@Getter
public enum UserGenderEnum {

    HIDE(0, "匿"),

    BOY(1, "男"),

    GIRL(2, "女");

    /**
     * 码，存入数据库
     */
    Integer code;

    /**
     * 性别
     */
    String sex;

}
