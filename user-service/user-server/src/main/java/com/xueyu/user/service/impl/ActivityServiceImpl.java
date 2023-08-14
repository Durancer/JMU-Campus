package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.user.exception.UserException;
import com.xueyu.user.mapper.ItemMapper;
import com.xueyu.user.mapper.UserItemMapper;
import com.xueyu.user.mapper.UserMapper;
import com.xueyu.user.pojo.domain.Item;
import com.xueyu.user.pojo.domain.User;
import com.xueyu.user.pojo.domain.UserItem;
import com.xueyu.user.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.xueyu.user.constant.TrafficVoucherContants.*;

/**
 * 活动业务层
 *
 * @author durance
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

	@Resource
	RedisTemplate<String, String> redisTemplate;

	@Resource
	UserMapper userMapper;

	@Resource
	ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

	@Resource
	ItemMapper itemMapper;

	@Resource
	UserItemMapper userItemMapper;

	@Override
	public Boolean getTrafficVoucher(Integer userId) {
		// 判断是否还存剩余流量券
		String stock = redisTemplate.opsForValue().get(STOCK_KEY);
		if (stock == null) {
			throw new UserException("流量券抢送暂未开始");
		}
		// 判断用户是否已经抢到过
		if (userIds.contains(userId)) {
			throw new UserException("该用户已经抢过");
		}
		// 判断是否存在用户
		User user = userMapper.selectById(userId);
		if (user == null) {
			throw new UserException("不存在该用户");
		}
		int stockNum = Integer.parseInt(stock);
		if (stockNum <= 0) {
			return false;
		}
		ScheduledFuture<?> addLockLifeThread = null;
		try {
			// 创建线程id, 用作判断
			String clientId = UUID.randomUUID().toString();
			// 设置分布式锁
			Boolean lock = redisTemplate.opsForValue().setIfPresent(LOCK_KEY, clientId, LOCK_TTL, TimeUnit.SECONDS);
			if (lock == null || !lock) {
				// todo 如果没有拿到锁优化为阻塞，不要直接返回
				return false;
			}
			// 使用线程池创建定时任务线程
			addLockLifeThread = scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
				// lock锁续命
				lengthenLockLife(clientId);
			}, ADD_LOCK_TTL, ADD_LOCK_TTL, TimeUnit.SECONDS);
			// 预减派发数量
			redisTemplate.opsForValue().decrement(STOCK_KEY);
			// 添加用户票券
			log.info("用户id -> {}, 抢到流量券", userId);
			LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Item::getName, "流量券");
			Item item = itemMapper.selectOne(wrapper);
			LambdaQueryWrapper<UserItem> userItemWrapper = new LambdaQueryWrapper<>();
			userItemWrapper.eq(UserItem::getItemId, item.getId());
			UserItem userItem = userItemMapper.selectOne(userItemWrapper);
			if (userItem == null) {
				// 如果未查到数据，说明用户还没有流量券直接添加
				UserItem addItem = new UserItem();
				addItem.setUserId(userId);
				addItem.setNum(1);
				addItem.setItemId(item.getId());
				userItemMapper.insert(addItem);
			} else {
				if (userItem.getNum() >= 1 && userItem.getItemId().equals(item.getId())) {
					userItem.setNum(userItem.getNum() + 1);
					userItemMapper.updateById(userItem);
				}
			}
			// 添加用户已抢送数据
			userIds.add(userId);
		} catch (Exception e) {
			log.info("执行出错：{}", e.getMessage());
		} finally {
			// 关闭续命线程，释放锁资源
			if (addLockLifeThread != null) {
				addLockLifeThread.cancel(true);
			}
			redisTemplate.delete(LOCK_KEY);
		}
		return true;
	}

	/**
	 * 对分布式锁进行续命
	 *
	 * @param clientId 创建的线程id
	 */
	public void lengthenLockLife(String clientId) {
		String redisLock = redisTemplate.opsForValue().get(LOCK_KEY);
		if (clientId.equals(redisLock)) {
			// 如果是此线程加的锁，进行续命操作
			redisTemplate.expire(LOCK_KEY, LOCK_TTL, TimeUnit.SECONDS);
			log.info("线程id {}，进行续命", clientId);
		}
	}

}
