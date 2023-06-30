package com.xueyu.post.pojo.domain;

/**
 * 投票类型
 *
 * @author fj0591
 */
public enum VoteType {

    /**
     * 多选类型投票
     */
    MULTIPLE("multiple"),

    /**
     * 单选类型投票
     */
    RADIO("radio");

    private final String value;

    VoteType(String value) {this.value = value;}

    public String getValue() {
        return value;
    }

}
