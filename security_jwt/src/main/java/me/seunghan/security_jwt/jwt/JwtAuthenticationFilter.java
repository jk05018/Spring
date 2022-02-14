package me.seunghan.security_jwt.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seunghan.security_jwt.auth.PrincipalDetails;
import me.seunghan.security_jwt.model.User;

/*
스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
/login 요청해서 username, password 전송하면(post)
UsernamePasswordAuthenticationFilter가 동작을 함
하지만 우리가 FormLogin을 disable 시켜놓았기 때문에 작동하지 않
 */
// 아 여기서 JWT 토큰을 verify하고 User 정보를 받아와서 UsernamePasswordAuthentication을 만들어 넘겨주면 되는구나!!!!
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	// 로그인 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		// formLogin을 disable 하면서 UsernamePasswordAuthenticationFilter가 비활성화 되었지만
		// username을 상속받아 작성한 JwtAuthenticationFilter를 Filter에 끼워 줌으로싸 직덩
		log.info("JwtAutenticationFilter : 로그인 시도");

		// 1. username, password 받아서
		try {

			//Json으로 왔을 때
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			log.info("{}", user);

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());

			// 이게 실행될 때 PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
			// DB에 있는 username과 Password가 일치한다.
			Authentication authentication = authenticationManager.authenticate(token);

			// authentication 객체가 session 영역에 저장됨 -> 로그인이 되었다는 뜻 // JWT를 사용하지 않았다.
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			log.info("{}", principalDetails.getUser().getUsername()); // 출력이 되었다면

			//authentication 객체가 session 영역에 저장을  히야하고 그 방법이 authentication을 return 해주는 것?
			// 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 것임
			// 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 session을 넣어준다.

			//JWT 토큰을 만들어줌?
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
			// UsernamePasswordAuthenticationFilter(attemptAuthentication())  -> manager(userdetailsService(loadUserbyUsername()))으로 유저 정보 호출 뒤
			// 인증 후 Authentication 반환 -> successAuthentication() 실행
			return null;
		}

	}

	// 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
	// 여기에서 JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("successfulAuthentication() start : 인증 완료");
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

		//RSA 방식 X Hash암호방식? HMAC 방식
		String jwtToken = JWT.create()
			.withSubject(principalDetails.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10))) // 10분
			.withClaim("id",principalDetails.getUser().getId())
			.withClaim("username",principalDetails.getUser().getUsername())
			.sign(Algorithm.HMAC512("secret"));

		response.addHeader("Authorization","Bearer " + jwtToken);
		// / success url로 넘어가지 않네?
	}
}
