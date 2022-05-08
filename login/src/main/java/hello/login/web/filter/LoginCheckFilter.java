package hello.login.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.PatternMatchUtils;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {
	private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		final String requestURI = httpRequest.getRequestURI();

		final HttpServletResponse httpResponse = (HttpServletResponse)response;

		try{
			log.info("인증 체크 필터 시작 {}", requestURI);

			if(isLoginCheckPath(requestURI)){
				log.info("인증 체크 로직 실행 {}", requestURI);
				final HttpSession session = httpRequest.getSession(false);
				if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
					log.info("미인증 사용자 요청 {}", requestURI);
					httpResponse.sendRedirect("/login?redirectURL="+requestURI);
					return;
				}
			}

			chain.doFilter(request, response);
		}catch (Exception e){
			throw e;
		}finally {
			log.info("인증 체크 필터 종료 {}", requestURI);
		}

	}

	/**
	 * 화이트 리스트 일 경우 인증 체크 X
	 */
	private boolean isLoginCheckPath(String requestURI){
		return !PatternMatchUtils.simpleMatch(whitelist,requestURI);
	}

}
