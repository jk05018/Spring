package hello.jdbc.exception.translator;

import static hello.jdbc.connection.ConnectionConst.*;

import java.net.DatagramSocket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import hello.jdbc.repository.ex.MyDuplicateKeyException;
import lombok.RequiredArgsConstructor;

public class ExTranslatorV1Test {
	private static final Logger log = LoggerFactory.getLogger(ExTranslatorV1Test.class);

	Repository repository;
	Service service;

	@BeforeEach
	void init(){
		final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, username, password);
		repository = new Repository(driverManagerDataSource);
		service = new Service(repository);
	}

	@RequiredArgsConstructor
	static class Service {
		private static final Logger log = LoggerFactory.getLogger(Service.class);

		private final Repository repository;

		public void create(String memberId) {
			try {
				repository.save(new Member(memberId, 0));
				log.info("saveId {} = ", memberId);
			} catch (MyDuplicateKeyException e) {
				log.info("키 중복, 복구 시도");
				String retryId = generateNewId(memberId);
				log.info("retryId = {} ", retryId);
				repository.save(new Member(retryId, 0));
			} catch (MyDbException e) {
				log.info("데이터 접근 계층 예외", e);
				throw e;
			}

		}

		private String generateNewId(String memberId) {
			return memberId + new Random().nextInt(10000);
		}

	}

	@RequiredArgsConstructor
	static class Repository {

		private final DataSource dataSource;

		public Member save(Member member) {
			String sql = "insert into member(member_id, money) values (?,?)";

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, member.getMemberId());
				preparedStatement.setInt(2, member.getMoney());
				preparedStatement.executeUpdate();

				return member;
			} catch (SQLException exception) {
				if (exception.getErrorCode() == 23505) {
					throw new MyDuplicateKeyException(exception);
				}
				throw new MyDbException(exception);
			} finally {
			}
		}

	}

}
