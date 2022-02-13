package me.seunghan.security_jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.seunghan.security_jwt.filter.MyFilter1;
import me.seunghan.security_jwt.filter.MyFilter2;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MyFilter1> filter1(){
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
		bean.addUrlPatterns("/*"); // 모든 영역에 걸리도록 설정하는 건가?
		bean.setOrder(0); // 낮은 번호가 필터 중에서 가장 먼저 실행됨
		return bean;
	}

	@Bean
	public FilterRegistrationBean<MyFilter2> filter2(){
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
		bean.addUrlPatterns("/*");
		bean.setOrder(1); // 낮은 번호가 필터 중에서 가장 먼저 실행됨
		return bean;
	}
}
