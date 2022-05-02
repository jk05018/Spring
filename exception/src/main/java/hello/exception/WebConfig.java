package hello.exception;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.exception.filter.LogFilter;
import hello.exception.intercepter.LogInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
		.order(1)
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**","*/ico","/error","/error-page/**"); // 오류 페이지 경로를 넣어줌?
	}

	// @Bean
	public FilterRegistrationBean logFilter(){
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new LogFilter());
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/*");
		filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR); // 이 필터는 dispatchType이 REQUEST일 경우, ERROR일 경우 둘 다 호출이 된다.
		return filterFilterRegistrationBean;
	}
}
