package hello.jdbc.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;

/**
 * 트랜잭션 - DataSource, TransactionManager 자동 등록
 */
@SpringBootTest
@TestConfiguration
class MemberServiceV3_4Test {
	private static final Logger log = LoggerFactory.getLogger(MemberServiceV3_4Test.class);

	private static final String MEMBER_A = "memberA";
	private static final String MEMBER_B = "memberB";
	private static final String MEMBER_EX = "ex";

	@Autowired
	private MemberRepositoryV3 memberRepository;
	@Autowired
	private MemberServiceV3_3 memberService;

	@TestConfiguration
	static class Config { // 왜 static이지?

		private final DataSource dataSource;

		Config(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Bean
		MemberRepositoryV3 memberRepositoryV3() {
			return new MemberRepositoryV3(
				dataSource
			);
		}

		@Bean
		MemberServiceV3_3 memberServiceV3_3() {
			return new MemberServiceV3_3(memberRepositoryV3());

		}
	}

	@Test
	void AopCheck() {
		log.info("memberService.class {}", memberService.getClass());
		log.info("memberREpository.class {}", memberRepository.getClass());
		// service 객체에만 AOP를 적용하였
		Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
		Assertions.assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();

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
