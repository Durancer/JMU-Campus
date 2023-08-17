package com.xueyu.gateway.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import com.xueyu.gateway.config.JwtProperties;
import com.xueyu.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * jwt 认证过滤器
 *
 * @author durance
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

	public static final String AUTHORIZE_TOKEN = "token";

	private Set<String> matchersCheck;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		// 进行请求路径判度，放行不需要认证的接口
		String path = request.getURI().getPath();
		// 拿到jwt的值
		String jwt = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
		// token不为空时，优先解析token
		if (!StringUtils.isEmpty(jwt)) {
			// 如果不为空则进行效验
			try {
				// 效验jwt正确性，如果错误会抛出异常
				JwtUtil.parseJwt(jwt);
				// 解析jwt拿到jwt的载荷跟其余信息
				Claims claims = JwtUtil.parseJwt(jwt);
				Integer userId = (Integer) claims.get("userId");
				ServerHttpRequest build = exchange.getRequest().mutate().header("userId", userId.toString()).build();
				exchange = exchange.mutate().request(build).build();
				// 只要token解析正确就进行返回
				return chain.filter(exchange);
			} catch (Exception e) {
				// 出现异常可能是token过期或恶意攻击
				log.warn("jwt解析错误：{}", e.getMessage());
				// 如果解析错误（过期了也会解析错误），判断是否为免鉴权接口
				if (verifyNoAuthentication(path)){
					return chain.filter(exchange);
				}
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}
		}
		// 没有token，则判断是否为免鉴权接口
		if(verifyNoAuthentication(path)){
			return chain.filter(exchange);
		}
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}

	/**
	 * 判断接口是否为免鉴权接口
	 *
	 * @param path 请求接口
	 * @return 判断结果
	 */
	private boolean verifyNoAuthentication(String path){
		//将不需要认证的接口存储在 Set中，减少判断是否为非鉴权接口的时间复杂度
		if(CollectionUtils.isEmpty(matchersCheck)){
			matchersCheck = new HashSet<>();
			String[] matchers = JwtProperties.matchers;
			matchersCheck.addAll(Arrays.asList(matchers));
		}
		// 为非鉴权接口直接跳过， 否则返回未认证
		return matchersCheck.contains(path);
	}

	@Override
	public int getOrder() {
		// 过滤器优先级，越小越先
		return -1;
	}

}
