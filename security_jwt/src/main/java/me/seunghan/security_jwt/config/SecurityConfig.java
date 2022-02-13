package me.seunghan.security_jwt.config;

import javax.persistence.Basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import me.seunghan.security_jwt.filter.MyFilter1;
import me.seunghan.security_jwt.filter.MyFilter3;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);
		http.csrf().disable();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다 stateless 서버로 만들겠다
			.and().addFilter(corsFilter); // 모든 요청은 이 필터를 통과하기에 내 서버는 cors 정책에서 벗어날 수 있다. CrossOrigin?
		// @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(O)
		http.formLogin().disable()
			.httpBasic().disable() // 기본적인 로그인 방식은 사용하지 않는다.
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
	}
}
