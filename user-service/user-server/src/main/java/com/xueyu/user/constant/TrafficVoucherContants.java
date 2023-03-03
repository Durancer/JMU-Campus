package com.xueyu.user.constant;

/**
 * 流量券相关常量
 *
 * @author durance
 */
public class TrafficVoucherContants {

	public static final String LOCK_KEY = "traffic:lock";

	public static final String STOCK_KEY = "traffic:voucher_num";

	public static final String GET_USER_KEY = "traffic:get_users";

	public static final Long LOCK_TTL = 10L;

	public static final Long ADD_LOCK_TTL = 3L;

	public static final Integer VOUCHER_NUM = 10;

}
