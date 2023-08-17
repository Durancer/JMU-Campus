package com.xueyu.user.pojo.vo;

import com.xueyu.user.pojo.domain.UserGeneral;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户统计响应 vo
 *
 * @author durance
 */
@Data
@NoArgsConstructor
public class UserGeneralVO {

    /**
     * 用户信息
     */
    UserView userInfo;

    /**
     * 用户统计数据
     */
    UserGeneral userData;

}
