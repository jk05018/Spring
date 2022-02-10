package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true) // 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 무슨 언어 있댔음 패캠 강의 듣기
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 403 접근 권한 없
			.anyRequest().permitAll()
			.and().formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") // /login 주소가 호출이 된다면 시큐리티가 낚아채서 대신 로그인을 진행해줌 -> Controller에 /login 만들지 않아도 됨
		.defaultSuccessUrl("/")
		;

	}
}
