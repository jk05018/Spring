package hello.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Service
public class MemberServiceV3_1 {
	private static final Logger log = LoggerFactory.getLogger(MemberServiceV3_1.class);

	// private final DataSource dataSource; // service 계층에서는 TRAsaction을 지원해 주기 위해 dataSource가 필요했구나
	private final PlatformTransactionManager transactionManager;
	private final MemberRepositoryV3 memberRepository;

	public MemberServiceV3_1(PlatformTransactionManager transactionManager,
		MemberRepositoryV3 memberRepository) {
		this.transactionManager = transactionManager;
		this.memberRepository = memberRepository;
	}

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		// 트랜잭션 시작
		final TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

		try {
			bizLogic(fromId, toId, money);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new IllegalStateException(e);
		} // trasactionManager 내부에서 커넥션을 생성? 받아오고 반환하기 때문에 release할 필요 없다.

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
