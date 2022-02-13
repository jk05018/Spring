package me.seunghan.security_jwt.jwt;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
			// 로그인이 서옥ㅇ하면 반환되는 autehntication에는 로그인에 대한 정보가 나옴
			Authentication authentication = authenticationManager.authenticate(token);

			// authentication 객체가 session 영역에 저장됨 -> 로그인이 되었다는 뜻
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			log.info("{}",principalDetails.getUser().getUsername()); // 출력이 되었다면

			return authentication;
		} catch (IOException e) {
			e.printStackTrace();

			// 2. 정상인지 로그인 시도를 해 보는 것이다. authenticationManager로 로그인 시도를 하면 PrincipalDetailsService가 호출이 된다.
			// -> loadByUsername() 함수가 실행이 된다.

			// 3. principalDetails를 세션에 담고 // 세션에 안담으면 권한관리를 안해줌?(권환관리를 위해서 세션에 담는다)

			// 4. JWT 토큰을 만들어서 응답해 주면 됨
			return null;
		}
	}
}
