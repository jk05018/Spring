package hello.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

public class CheckedTest {
	private static final Logger log = LoggerFactory.getLogger(CheckedTest.class);

	@Test
	void checked_catch() {
		Service service = new Service();
		service.callCatch();
	}

	@Test
	void checked_throw() {
		Service service = new Service();
		assertThatThrownBy(() -> service.callThrows())
			.isInstanceOf(MyCheckedException.class);
	}

	/**
	 * Exception을 상속받ㅇ느 예외는 체크 예외가 된다
	 */
	static class MyCheckedException extends Exception {
		public MyCheckedException(String message) {
			super(message);
		}
	}

	static class Service {
		Repository repository = new Repository();

		/**
		 * 예외를 잡아서 처리하는 코드
		 */
		public void callCatch() {
			try {
				repository.call();
			} catch (MyCheckedException e) {
				// 예외 처리 로직
				log.info("예외 처리, message {}", e.getMessage(), e);
			}
		}

		/**
		 * 체크 예외르 밖으로 던지는 코드
		 */
		public void callThrows() throws MyCheckedException {
			repository.call();
		}

	}

	@Test
	void checked_catched() {
	}

	static class Repository {
		public void call() throws MyCheckedException {
			throw new MyCheckedException("ex");
		}
	}
}
