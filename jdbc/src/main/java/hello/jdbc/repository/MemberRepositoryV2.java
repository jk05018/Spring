package hello.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import hello.jdbc.domain.Member;

/**
 * JDBC - ConnectionParam
 */
@Repository
public class MemberRepositoryV2 {
	private static final Logger log = LoggerFactory.getLogger(MemberRepositoryV2.class);

	private final DataSource dataSource;

	public MemberRepositoryV2(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Member save(Member member) throws SQLException {
		// 김영한 강사님처럼 이런 방법도 있겠지만 try catch문으로 알아서 닫아주도록 하는 방법도 있다.
		// 일일히 DriverManager에서 Connection을 얻어와서 쿼리를 날리는 방법 -> 외부 Resource 매우 낭비 -> Connection Pool 개념 등
		String SAVE_QUERY = "INSERT INTO member(member_id, money) VALUES (?,?)";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SAVE_QUERY);
			preparedStatement.setString(1, member.getMemberId());
			preparedStatement.setInt(2, member.getMoney());

			final int updated = preparedStatement.executeUpdate();

			if (updated != 1) {
				throw new RuntimeException(
					MessageFormat.format("Member가 save되지 않았습니다. member_id : {0}", member.getMemberId()));
			}

			return member;
		} catch (SQLException e) {
			log.error("DB error", e);
			throw e; // 왜 Exception을 그냥 SQL Exception으로 throw했을까
		} finally {
			// 만약 final에서 예외가 터진다면 예외가 밖으로까지 나감 -> try-catchansdmfh cjflgownjdigka
			close(connection, preparedStatement, null);
		}
	}

	public Member findById(String memberId) throws SQLException {
		// 김영한 강사님처럼 이런 방법도 있겠지만 try catch문으로 알아서 닫아주도록 하는 방법도 있다.
		// 일일히 DriverManager에서 Connection을 얻어와서 쿼리를 날리는 방법 -> 외부 Resource 매우 낭비 -> Connection Pool 개념 등
		String search_query = "SELECT * FROM member WHERE member_id = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(search_query);
			preparedStatement.setString(1, memberId);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Member(resultSet.getString("member_id"), resultSet.getInt("money"));
			} else {
				throw new NoSuchElementException(
					MessageFormat.format("member not found memberId = {0}", memberId));
			}

		} catch (SQLException e) {
			log.error("DB error", e);
			throw e; // 왜 Exception을 그냥 SQL Exception으로 throw했을까
		} finally {
			// 만약 final에서 예외가 터진다면 예외가 밖으로까지 나감 -> try-catchansdmfh cjflgownjdigka
			close(connection, preparedStatement, resultSet);
		}
	}

	public Member findById(Connection connection, String memberId) throws SQLException {
		// 김영한 강사님처럼 이런 방법도 있겠지만 try catch문으로 알아서 닫아주도록 하는 방법도 있다.
		// 일일히 DriverManager에서 Connection을 얻어와서 쿼리를 날리는 방법 -> 외부 Resource 매우 낭비 -> Connection Pool 개념 등
		String search_query = "SELECT * FROM member WHERE member_id = ?";

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(search_query);
			preparedStatement.setString(1, memberId);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Member(resultSet.getString("member_id"), resultSet.getInt("money"));
			} else {
				throw new NoSuchElementException(
					MessageFormat.format("member not found memberId = {0}", memberId));
			}

		} catch (SQLException e) {
			log.error("DB error", e);
			throw e; // 왜 Exception을 그냥 SQL Exception으로 throw했을까
		} finally {
			// connection은 여기서 닫지 않는다.
			JdbcUtils.closeResultSet(resultSet);
			JdbcUtils.closeStatement(preparedStatement);
			// JdbcUtils.closeConnection(connection); // connection param을 ㅗ넘겨 받을 때 connection을 닫으면 안됨 -> Tranraction 유지하고 있는 중이므
		}
	}

	public void update(String memberId, int money) throws SQLException {
		String update_query = "UPDATE member SET money = ? WHERE member_id = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(update_query);
			preparedStatement.setInt(1, money);
			preparedStatement.setString(2, memberId);

			final int updated = preparedStatement.executeUpdate();

			if (updated != 1) {
				throw new RuntimeException(
					MessageFormat.format("Member가 update되지 않았습니다. member_id : {0}", memberId));
			}

		} catch (SQLException e) {
			log.error("DB error", e);
			throw e; // 왜 Exception을 그냥 SQL Exception으로 throw했을까
		} finally {
			// 만약 final에서 예외가 터진다면 예외가 밖으로까지 나감 -> try-catchansdmfh cjflgownjdigka
			close(connection, preparedStatement, null);
		}
	}

	public void update(Connection connection, String memberId, int money) throws SQLException {
		String update_query = "UPDATE member SET money = ? WHERE member_id = ?";

		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(update_query);
			preparedStatement.setInt(1, money);
			preparedStatement.setString(2, memberId);

			final int updated = preparedStatement.executeUpdate();

			if (updated != 1) {
				throw new RuntimeException(
					MessageFormat.format("Member가 update되지 않았습니다. member_id : {0}", memberId));
			}

		} catch (SQLException e) {
			log.error("DB error", e);
			throw e; // 왜 Exception을 그냥 SQL Exception으로 throw했을까
		} finally {
			// connection은 여기서 닫지 않는다.
			JdbcUtils.closeStatement(preparedStatement);
			// JdbcUtils.closeConnection(connection); // connection param을 ㅗ넘겨 받을 때 connection을 닫으면 안됨 -> Tranraction 유지하고 있는 중이므
		}
	}

	public void delete(String memberId) throws SQLException {
		String delete_query = "DELETE FROM member WHERE member_id = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(delete_query);
			preparedStatement.setString(1, memberId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			log.error("DB error", e);
			throw e;
		} finally {
			close(connection, preparedStatement, null);
		}
	}

	private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		// close 부분을 일일히 작성하지 않고 Utils를 써도 된다.
		JdbcUtils.closeResultSet(resultSet);
		JdbcUtils.closeStatement(preparedStatement);
		JdbcUtils.closeConnection(connection);
	}

	private Connection getConnection() throws SQLException {
		final Connection connection = dataSource.getConnection();
		log.info("get Connection {} , class {}" , connection, connection.getClass());
		return connection;
	}
}
