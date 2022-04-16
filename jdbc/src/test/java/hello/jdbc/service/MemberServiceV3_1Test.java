package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;

/**
 * 트랜잭션 - 트랜잭션 매니저 이용
 */
class MemberServiceV3_1Test {

	private static final String MEMBER_A = "memberA";
	private static final String MEMBER_B = "memberB";
	private static final String MEMBER_EX = "ex";

	private MemberRepositoryV3 memberRepository;
	private MemberServiceV3_1 memberService;

	@BeforeEach
	void beforeAll() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, username, password);
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		memberRepository = new MemberRepositoryV3(dataSource);
		memberService = new MemberServiceV3_1(transactionManager, memberRepository);

	}

	@AfterEach
	void afterEach() throws SQLException {
		memberRepository.delete(MEMBER_A);
		memberRepository.delete(MEMBER_B);
		memberRepository.delete(MEMBER_EX);
	}

	@DisplayName("정상 이체")
	@Test
	void accountTransfer() throws SQLException {
		//given
		final Member memberA = new Member(MEMBER_A, 10000);
		final Member memberB = new Member(MEMBER_B, 10000);
		memberRepository.save(memberA);
		memberRepository.save(memberB);

		//when
		memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

		//then
		final Member findMemberA = memberRepository.findById(memberA.getMemberId());
		final Member findMemberB = memberRepository.findById(memberB.getMemberId());

		assertThat(findMemberA.getMoney(), is(8000));
		assertThat(findMemberB.getMoney(), is(12000));

	}

	@DisplayName("이체 중 예외 발생")
	@Test
	void accountTransferEX() throws SQLException {
		//given
		final Member memberA = new Member(MEMBER_A, 10000);
		final Member memberEx = new Member(MEMBER_EX, 10000);
		memberRepository.save(memberA);
		memberRepository.save(memberEx);

		//when
		// MemberA 돈만 까진
		Assertions.assertThatThrownBy(
			() -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
			.isInstanceOf(IllegalStateException.class);

		//then
		final Member findMemberA = memberRepository.findById(memberA.getMemberId());
		final Member findMemberB = memberRepository.findById(memberEx.getMemberId());

		// roll back 완료
		assertThat(findMemberA.getMoney(), is(10000));
		assertThat(findMemberB.getMoney(), is(10000));

	}
}
