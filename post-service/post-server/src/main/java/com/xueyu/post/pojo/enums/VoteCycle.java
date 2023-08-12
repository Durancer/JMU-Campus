package com.xueyu.post.pojo.enums;

/**
 * 投票周期
 *
 * @author fj0591
 */
public enum VoteCycle {

    /**
     * 一天
     */
    DAY("day"),

    /**
     * 一周
     */
    WEEK("week"),

    /**
     * 一月
     */
    MONTH("month"),

    /**
     * 一年
     */
    YEAR("year");

    private final String value;

    VoteCycle(String value){this.value = value;}

    public String getValue() {
        return value;
    }

}
