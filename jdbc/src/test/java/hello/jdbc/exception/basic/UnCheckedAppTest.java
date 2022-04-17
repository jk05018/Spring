package hello.jdbc.exception.basic;

import java.net.ConnectException;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnCheckedAppTest {

	@Test
	void checked() {
		Controller controller = new Controller();
		Assertions.assertThatThrownBy(() -> controller.request())
			.isInstanceOf(Exception.class);
	}

	static class Controller {
		Service service = new Service();

		public void request() {
			service.logic();
		}
	}

	static class Service {
		Repository repository = new Repository();
		NetworkClient networkClient = new NetworkClient();

		public void logic() {
			repository.call();
			networkClient.call();
		}

	}

	// 싱대는 서버, 나는 클리이언
	static class NetworkClient {
		public void call() {
			try {
				connectNetwork();
			} catch (ConnectException exception) {
				throw new RuntimeConnectException(exception);
			}
		}

		public void connectNetwork() throws ConnectException {
			throw new ConnectException("ex");
		}

	}

	static class Repository {
		public void call() {
			try {
				runSql();
			} catch (SQLException exception) {
				throw new RuntimeSqlException(exception);
			}
		}

		public void runSql() throws SQLException {
			throw new SQLException("ex");
		}
	}

	static class RuntimeConnectException extends RuntimeException {
		public RuntimeConnectException(Throwable cause) {
			super(cause);
		}
	}

	static class RuntimeSqlException extends RuntimeException {
		public RuntimeSqlException(Throwable cause) {
			super(cause);
		}
	}
}
