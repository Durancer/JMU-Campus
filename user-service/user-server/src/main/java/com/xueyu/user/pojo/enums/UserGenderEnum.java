package com.xueyu.user.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author durancer
 */
@AllArgsConstructor
@Getter
public enum UserGenderEnum {

    /**
     * 匿
     */
    HIDE(0, "匿", "default.jpg"),

    /**
     * 男
     */
    BOY(1, "男", "default_boy.png"),

    /**
     * 女
     */
    GIRL(2, "女", "default_girl.png");

    /**
     * 码，存入数据库
     */
    final Integer code;

    /**
     * 性别
     */
    final String sex;

    /**
     * 默认头像
     */
    final String defaultAvatarUrl;

    /**
     * 是否在枚举中
     * @param code code
     */
    public static boolean isInEnums(Integer code){
        for(UserGenderEnum userGenderEnum : UserGenderEnum.values()){
            if (userGenderEnum.code.equals(code)){
                return true;
            }
        }
        return false;
    }

    /**
     * 头像是否在枚举中
     * @param avatarUrl 头像
     */
    public static boolean isAvatarInEnums(String avatarUrl){
        for(UserGenderEnum userGenderEnum : UserGenderEnum.values()){
            if (userGenderEnum.defaultAvatarUrl.equals(avatarUrl)){
                return true;
            }
        }
        return false;
    }

    /**
     * 通过code取枚举
     * @param code code
     */
    public static UserGenderEnum getEnumByCode(Integer code){
        for(UserGenderEnum userGenderEnum : UserGenderEnum.values()){
            if (userGenderEnum.code.equals(code)){
                return userGenderEnum;
            }
        }
        return null;
    }

}
