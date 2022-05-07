package hello.login.web.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;

class SessionManagerTest {
	SessionManager sessionManager = new SessionManager();

	@DisplayName("1. session 생성 테스트")
	@Test
	void test(){
		// given
		MockHttpServletResponse response = new MockHttpServletResponse();
		Member member = new Member();

		//when
		sessionManager.createSession(member, response);

		 //then 요쳥에 읍답 쿠키가 저장이 되어있어야 함
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());

		final Object result = sessionManager.getSession(request);

		assertThat(result).isEqualTo(member);

		// 세션 만료
		sessionManager.expire(request);

		final Object expired = sessionManager.getSession(request);

		assertThat(expired).isNull();



	}

}
