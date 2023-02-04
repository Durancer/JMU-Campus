package com.xueyu.user.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户服务客户端
 *
 * @author durance
 */
@FeignClient(value = "user-server", fallback = UserClientResolver.class)
public interface UserClient {

}
