package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnectionUtil {
	private static final Logger log = LoggerFactory.getLogger(DbConnectionUtil.class);

	public static Connection getConnection() {
		try {
			final Connection connection = DriverManager.getConnection(URL, username, password);
			log.info("get connection = {}, class = {}", connection, connection.getClass());
			return connection;
		} catch (SQLException exception) {
			// 예외가 터지면 checkedException을 upchecked Exception으로 변경해준다. -> 이유는 잘 생각해 보도록
			throw new IllegalStateException(exception);
		}
	}
}
