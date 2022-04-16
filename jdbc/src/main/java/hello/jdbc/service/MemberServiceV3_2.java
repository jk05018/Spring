package hello.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Service
public class MemberServiceV3_2 {
	private static final Logger log = LoggerFactory.getLogger(MemberServiceV3_2.class);


	// private final PlatformTransactionManager transactionManager;
	private final TransactionTemplate txTemplate;
	private final MemberRepositoryV3 memberRepository;

	public MemberServiceV3_2(PlatformTransactionManager transactionManager,
		MemberRepositoryV3 memberRepository) {
		// TransactionTEmplate을 주입 받는 게 아니고, PlatformTransactionManager를 주입받아 만들어준다?
		// 보통 이 패턴으로 많이 사용한다.
		// 주입 받고 사용해도 되는데 이렇게 사용하는 이유는 1. 관례이다 2. TransactionTemplate은 클래스여서 유연성이 없다 PlatformTransactionManager가 인터페이스이므로 유연성을 가질 수 있음. 근데 외부에서 빈으로 설정하면 되기에 딱히?
		this.txTemplate = new TransactionTemplate(transactionManager);
		this.memberRepository = memberRepository;
	}

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		txTemplate.executeWithoutResult((status) -> {
			try {
				bizLogic(fromId,toId,money);
			} catch (SQLException exception) {
				throw new IllegalStateException(exception);
			}
		});

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

	private void bizLogic(String fromId, String toId, int money) throws SQLException {
		final Member fromMember = memberRepository.findById(fromId);
		final Member toMember = memberRepository.findById(toId);

		memberRepository.update(fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(toId, toMember.getMoney() + money);
	}

	private void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("이체 중 예외 발생");
		}
	}
}
