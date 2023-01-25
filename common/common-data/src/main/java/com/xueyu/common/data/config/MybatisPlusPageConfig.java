package com.xueyu.common.data.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 分页配置
 *
 * @author durance
 */
@Configuration
public class MybatisPlusPageConfig {

	@Bean
	public MybatisPlusInterceptor mybatisPlusDefaultInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 配置分页助手
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		// 阻止恶意的全表更新删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}

}
