package com.cos.security1.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 시큐리티 설정에서 loginPrecessingUrl("/login");
 *  /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어있는 loadUserByUsername 함수가 실
 *  이거는 fastcampus꺼 이용하자 그게 더 깔끔 어떻게 UserDetails와 UserDetailService를 사용하는지만 알아두
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	// 시큐리티 session(SecurityContextHolder에 저장) = Authenticaiton = UserDetails 이므로
	// 이렇게 반환해 주기만 해도 만들어진 PrincipalDetails(UserDetails)가 DaoProviderManager를 통해 Autehntication을 생성하고 비밀번호를 비교
	//  후 인증 된다면 SecurityContextHolder에 주입?
	//UserDetailsService returned null, which is an interface contract violation 발생
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("entered username : {}" , username);
		//findBy 규칙 -> username 문법 JPA 공부하기
		User userEntity = userRepository.findByUsername(username);
		log.info("find user username : {}",userEntity.getUsername());
		if (userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
