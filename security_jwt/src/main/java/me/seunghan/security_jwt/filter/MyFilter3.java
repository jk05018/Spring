package me.seunghan.security_jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		log.info("filter1");

		/*토큰 : 코스 만들었다고 가정하자
		만약 이 필터를 통해 Authorization을 체크하려 한다면 SpringSecurityFilter가 동작하기 전에 해야한다.
		why? 다 통과하고 인증 받을 필요 없으니까?*/

		/*
		토큰 : secret 이걸 만들어줘야함. id와 pw가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
		요청할 때 마다 header에 Authorization에 value값으로 토큰을 가지고 오겠죠?
		그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증
		 */


		if (req.getMethod().equals("POST")) {
			log.info("POST 요청");
			String headerAuth = req.getHeader("Authorization");
			log.info("Authorization : {}", headerAuth);

			// 만약 Authorization에 임시로 저장한 토큰이 넘어온다면
			if (headerAuth.equals("secret")) {
				chain.doFilter(req, res);
				// 계속 진행해!
			} else {
				// 아니면 멈춰
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
	}
}
