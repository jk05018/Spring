package me.seunghan.security_jwt.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seunghan.security_jwt.model.User;
import me.seunghan.security_jwt.repository.UserRepository;

// http://localhost:8080/login -> SpringSecurity 기본 loginProcessingUrl?
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("PrincipalDetailsServiced loadUserByUsername 시작");
		User userEntity = userRepository.findByUsername(username);
		return new PrincipalDetails(userEntity);
	}
}
