package com.xueyu.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 定义令牌桶根据的key, 主机ip
 *
 * @author duranec
 */
@Configuration
public class KeyResolverConfig {

	@Bean
	public KeyResolver ipKeyResolver() {
		return new KeyResolver() {
			@Override
			public Mono<String> resolve(ServerWebExchange exchange) {
				return Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
			}
		};
	}

}
