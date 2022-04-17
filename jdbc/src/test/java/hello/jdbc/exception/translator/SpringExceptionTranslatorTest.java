package hello.jdbc.exception.translator;

import static hello.jdbc.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import hello.jdbc.connection.ConnectionConst;

public class SpringExceptionTranslatorTest {
	private static final Logger log = LoggerFactory.getLogger(SpringExceptionTranslatorTest.class);

	DataSource dataSource;

	@BeforeEach
	void init() {
		dataSource = new DriverManagerDataSource(URL, username, password);
	}

	@Test
	void sqlExceptionErrorCode() {
		String sql = "select bad grammer";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeQuery();

		} catch (SQLException exception) {
			Assertions.assertThat(exception.getErrorCode()).isEqualTo(42122);
			int errorCode = exception.getErrorCode();
			log.info("errorCode = {}", errorCode);
			log.info("error", exception);
		}
	}

	@Test
	void exceptionTranslator() {
		String sql = "select bad grammer";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeQuery();

		} catch (SQLException exception) {
			Assertions.assertThat(exception.getErrorCode()).isEqualTo(42122);
			final SQLErrorCodeSQLExceptionTranslator errorTranslator = new SQLErrorCodeSQLExceptionTranslator();
			final DataAccessException resultException = errorTranslator.translate("select", sql, exception);

			log.info("result Exception : ", resultException);
			Assertions.assertThat(resultException.getClass()).isEqualTo(BadSqlGrammarException.class);

		}
	}
}


