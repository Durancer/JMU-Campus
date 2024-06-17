package com.xueyu.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author durancer
 */
@AllArgsConstructor
@Getter
public enum ResultTypeEnum {

    SUCCESS(200, "success"),

    FAIL(400, "fail"),

    NO_FOUND(404, "请求失败"),

    FREQUENT(408, "访问频繁"),

    ERROR(500, "错误"),

    SERVICE_EXCEPTION(503, "调用服务异常");

    /**
     * 码
     */
    Integer code;

    /**
     * 描述
     */
    String desc;

}
