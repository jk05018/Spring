package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

public class ConnectionTest {
	private static final Logger log = LoggerFactory.getLogger(ConnectionTest.class);

	@Test
	void driverManager() throws SQLException {
		//이러면 2 개의 connection을 얻게 된다. 만약 안 닫는다면 resource를 계속 소모하므로 리소스 누수가 발생한다.
		final Connection connection1 = DriverManager.getConnection(URL, username, password);
		final Connection connection2 = DriverManager.getConnection(URL, username, password);
	}

	@Test
	void dataSourceDriverMananger() throws SQLException {
		// DriverManagerDataSource - 항상 새로운 커넥션을 획득한다. because DriverManager에서 바로 사용하기 때문
		final DataSource driverManagerDataSource = new DriverManagerDataSource(URL, username, password);
		useDataSource(driverManagerDataSource);
	}

	@Test
	void dataSourceConnectionPool() throws SQLException, InterruptedException {
		// 커넥션 풀링 : hikari -> Spring에서 자동으로 히카리를 쓴다.
		final HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaximumPoolSize(10);
		dataSource.setPoolName("MyPool");

		useDataSource(dataSource);
		Thread.sleep(1000);
	}

	private void useDataSource(DataSource dataSource) throws SQLException {
		final Connection con1 = dataSource.getConnection();
		final Connection con2 = dataSource.getConnection();
		log.info("connection = {}, class = {}", con1, con1.getClass());
		log.info("connection = {}, class = {}", con2, con2.getClass());

	}
}
