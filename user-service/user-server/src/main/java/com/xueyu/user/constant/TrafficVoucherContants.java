package com.xueyu.user.constant;

import java.util.HashSet;
import java.util.Set;

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

	/**
	 * 数据用来存储已经领取过流量券的用户id，便于判断
	 * 在流量券领取活动结束之后将有定时任务来清空本集合，便于下次活动开始时，再次使用
	 */
	public static Set<Integer> userIds = new HashSet<>(16);

}
