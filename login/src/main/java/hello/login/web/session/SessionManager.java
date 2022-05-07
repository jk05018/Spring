package hello.login.web.session;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {

	public static final String SESSZION_COOKIE_NAME = "mySessionId";

	private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

	/**
	 * 세션 생성
	 * @param value
	 * @param response
	 */
	public void createSession(Object value, HttpServletResponse response) {
		final String sessionId = UUID.randomUUID().toString();

		sessionStore.put(sessionId, value);

		final Cookie mySessionCookie = new Cookie(SESSZION_COOKIE_NAME, sessionId);
		response.addCookie(mySessionCookie);
	}

	/**
	 * 세션 조회
	 */
	public Object getSession(HttpServletRequest request) {
		final Cookie cookie = findCookie(request, SESSZION_COOKIE_NAME);

		if (cookie == null) {
			return null;
		}

		return sessionStore.get(cookie.getValue());

	}

	/**
	 * 세션 만료
	 */
	public void expire(HttpServletRequest request) {
		final Cookie sessionCookie = findCookie(request, SESSZION_COOKIE_NAME);
		if (sessionCookie != null) {
			sessionStore.remove(sessionCookie.getValue());
		}
	}

	private Cookie findCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null) {
			return null;
		}
		return Arrays.stream(request.getCookies())
			.filter(c -> c.getName().equals(cookieName))
			.findAny()
			.orElse(null);
	}

}
