package hello.querydsl;

import static hello.querydsl.entity.QMember.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.querydsl.dto.MemberDto;
import hello.querydsl.dto.UserDto;
import hello.querydsl.entity.Member;
import hello.querydsl.entity.QMember;
import hello.querydsl.entity.Team;

@SpringBootTest
@Transactional
public class QueryDslAdvancedTest {

	@PersistenceContext
	EntityManager em;
	JPAQueryFactory queryFactory;

	@BeforeEach
	void setUp() {
		queryFactory = new JPAQueryFactory(em);

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");

		em.persist(teamA);
		em.persist(teamB);

		final Member member1 = new Member("member1", 10, teamA);
		final Member member2 = new Member("member2", 20, teamA);

		final Member member3 = new Member("member3", 30, teamB);
		final Member member4 = new Member("member4", 40, teamB);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}

	@Test
	void dto_조회() {
		final List<MemberDto> result = em.createQuery("select new hello.querydsl.dto.MemberDto(m.username, m.age) "
			+ "from Member m", MemberDto.class).getResultList();

		// 프로퍼티 방식 - setter, 자바 빈즈 방식 사용
		final List<MemberDto> result1 = queryFactory
			.select(Projections.bean(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

		// 필드를 이용하는 방식 -
		final List<MemberDto> result2 = queryFactory
			.select(Projections.fields(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

		// 생성자를 이용한 방식
		final List<MemberDto> result3 = queryFactory
			.select(Projections.constructor(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

	}

	@Test
	void dto_별칭이_다를_때() {

		final List<UserDto> result4 = queryFactory
			.select(Projections.fields(UserDto.class, member.username.as("name"), member.age))
			.from(member)
			.fetch();

		for (UserDto userDto : result4) {
			System.out.println("name : " + userDto.getName());
			System.out.println("age : " + userDto.getAge());
		}
	}

	@Test
	void 동적쿼리_BooleanBuilder() {
		String usernameParam = "member1";
		Integer ageParam = 10;

		final List<Member> result = searchMember1(usernameParam, ageParam);

		assertThat(result.size()).isEqualTo(1);

	}

	private List<Member> searchMember1(String usernameCond, Integer ageCond){
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		if (usernameCond != null) {
			booleanBuilder.and(member.username.eq(usernameCond));
		}
		if (ageCond != null) {
			booleanBuilder.and(member.age.eq(ageCond));
		}

		return queryFactory
			.selectFrom(member)
			.where(booleanBuilder)
			.fetch();
	}

	private List<Member> searchMember2(String usernameCond, Integer ageCond){
		return queryFactory
			.selectFrom(member)
			.where(usernameEq(usernameCond), ageEq(ageCond))
			.fetch();

		// where 파라미터로 묶일 경우 null이면 넘어감
	}

	private BooleanExpression usernameEq(String usernameCond){
		return usernameCond != null ? member.username.eq(usernameCond) : null;
	}

	private BooleanExpression ageEq(Integer ageCond){
		return ageCond != null ? member.age.eq(ageCond) : null;
	}

	@Test
	void bulkupdate() {
		// bulk 연산 바로 실행해버림
		final long count = queryFactory
			.update(member)
			.set(member.username, "비회원")
			.where(member.age.lt(20))
			.execute();
	}
}
