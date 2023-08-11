package com.xueyu.gateway.filter;

import com.xueyu.gateway.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 限制单ip访问，防止单ip频繁访问
 *
 * @author durance
 */
@Component
@Slf4j
public class AccessLimitFilter implements GlobalFilter, Ordered {

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		// 获取请求ip地址
		String ipAddress = RequestUtil.getIpAddress(request);
		if (checkLimit(ipAddress)) {
			// 如果超限了，则返回频繁访问信息
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.REQUEST_TIMEOUT);
			return response.setComplete();
		}
		return chain.filter(exchange);
	}

	/**
	 * 核对ip地址是否超出访问限制
	 * 当前限流策略为单ip 10s最多访问 30次
	 *
	 * @param ipAddress ip地址
	 * @return 是否超限
	 */
	public boolean checkLimit(String ipAddress) {
		// todo 将限流参数转移到配置文件当中
		int time = 10, count = 30;
		String key = "limit:" + ipAddress;
		Integer hasLimit = redisTemplate.opsForValue().get(key);
		if (hasLimit != null) {
			if (hasLimit == 0) {
				log.warn("ip 地址为 ->{}, 触发限流告警", ipAddress);
				return true;
			}
			redisTemplate.opsForValue().decrement(key);
		} else {
			redisTemplate.opsForValue().set(key, count, time, TimeUnit.SECONDS);
		}
		return false;
	}

	@Override
	public int getOrder() {
		return -2;
	}

}
