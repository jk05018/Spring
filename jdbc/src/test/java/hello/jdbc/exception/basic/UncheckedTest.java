package hello.jdbc.exception.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncheckedTest {
	private static final Logger log = LoggerFactory.getLogger(UncheckedTest.class);

	/**
	 *  RuntimeException을 상속받은 예외는 언체크 예외가 된다.
	 */
	static class MyUncheckedException extends RuntimeException {
		public MyUncheckedException(String message) {
			super(message);
		}
	}

	static class Service {
		Repository repository = new Repository();

		public void callCheck() {
			try {
				repository.call();
			} catch (MyUncheckedException e) {
				log.info("got error message = {}", e.getMessage(), e);
			}
		}

		public void callThrows(){
			repository.call();
		}
	}

	static class Repository {
		public void call() {
			throw new MyUncheckedException("ex");
		}
	}
}
