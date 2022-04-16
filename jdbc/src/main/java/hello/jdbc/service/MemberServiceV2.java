package hello.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종
 */
@Service
public class MemberServiceV2 {
	private static final Logger log = LoggerFactory.getLogger(MemberServiceV2.class);

	private final DataSource dataSource;
	private final MemberRepositoryV2 memberRepository;

	public MemberServiceV2(DataSource dataSource, MemberRepositoryV2 memberRepository) {
		this.dataSource = dataSource;
		this.memberRepository = memberRepository;
	}

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		final Connection connection = dataSource.getConnection();

		try {
			connection.setAutoCommit(false);
			bizLogic(fromId, toId, money, connection);
		} catch (Exception e) {
			log.info("Got Error in accountTransfer fromId : " + fromId + ", toId : " + toId);
			connection.rollback();// 실패시 rollback
			throw new IllegalStateException();
		} finally {
			release(connection);
		}

	}

	private void release(Connection connection) {
		if (connection != null) {
			try {
				// autocommit은 다시 복구되지 않음 여기서 복구시켜줘야함
				connection.setAutoCommit(true);
				connection.close();
			} catch (Exception e) {
				log.info("error", e);
			}
		}
	}

	private void bizLogic(String fromId, String toId, int money, Connection connection) throws SQLException {
		final Member fromMember = memberRepository.findById(connection, fromId);
		final Member toMember = memberRepository.findById(connection, toId);

		memberRepository.update(connection, fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(connection, toId, toMember.getMoney() + money);
		connection.commit();
	}

	private void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("이체 중 예외 발생");
		}
	}
}
