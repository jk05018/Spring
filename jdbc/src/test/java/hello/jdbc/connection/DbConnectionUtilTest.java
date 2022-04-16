package hello.jdbc.connection;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DbConnectionUtilTest {

	@Test
	void connection() {
		final Connection connection = DbConnectionUtil.getConnection();

		// JUnit과 assertj, hamcrest의 각각틔 특성을 잘 알고 적재적소에 쓰는 법을 공부해야 겠다.
		Assertions.assertThat(connection).isNotNull();
	}
}
