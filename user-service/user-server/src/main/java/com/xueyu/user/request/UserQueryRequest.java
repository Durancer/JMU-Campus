package com.xueyu.user.request;

import com.xueyu.common.core.request.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * 管理站用户请求
 *
 * @author durance
 */
@Data
public class UserQueryRequest extends PageRequest {

    /**
     * 自增id
     */
    Integer id;

    /**
     * 用户名
     */
    String username;

    /**
     * 名称
     */
    String nickname;

    /**
     * 用户邮箱
     */
    String email;

    /**
     * 性别 0 匿 | 1 boy | 2 girl
     */
    Integer sex;

    /**
     * 电话
     */
    String phone;

    /**
     * 创建时间
     */
    Date createTime;

}
