package com.xueyu.user.sdk.pojo.utils;

import com.xueyu.user.sdk.pojo.config.UserAnonymityConfig;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户工具类
 *
 * @author durance
 */
@Component
public class UserFactory {

    @Resource
    UserAnonymityConfig userAnonymityConfig;

    /**
     * 生成匿名用户
     * @return ret
     */
    public UserSimpleVO buildAnonymityUserInfo(){
        UserSimpleVO userSimpleVO = new UserSimpleVO();
        userSimpleVO.setAvatarUrl(userAnonymityConfig.getAvatarUrl());
        userSimpleVO.setSex(userAnonymityConfig.getSex());
        userSimpleVO.setNickname(userAnonymityConfig.getUsername());
        userSimpleVO.setId(-1);
        return userSimpleVO;
    }

}
