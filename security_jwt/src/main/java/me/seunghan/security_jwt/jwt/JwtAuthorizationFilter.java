package me.seunghan.security_jwt.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.slf4j.Slf4j;
import me.seunghan.security_jwt.auth.PrincipalDetails;
import me.seunghan.security_jwt.model.User;
import me.seunghan.security_jwt.repository.UserRepository;

// 시큐리티가 filter를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 떄 위 필터를 무조건 타게 되어 있음
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;

	public JwtAuthorizationFilter(
		AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	// 이제 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		log.info("인증이나 권한이 필요한 주소 요청이 됨.");
		String jwtHeader = request.getHeader("Authorization");
		log.info("Authorization : {}", jwtHeader);

		// header가 있는지 확인
		if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response); // 그냥 다음으로 넘어가라
			return;
		}

		// JWT 토큰을 검증을 해서 전상적인 사용자인지 확인
		String jwtToken = jwtHeader.replace("Bearer ", "");

		String username = JWT.require(Algorithm.HMAC512("secret")).build().verify(jwtToken)
			.getClaim("username").asString();


		//서명이 정상적으로 됨
		if(username != null){
			User userEntity = userRepository.findByUsername(username);

			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

			// JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());

			// 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request,response);
	}
}
