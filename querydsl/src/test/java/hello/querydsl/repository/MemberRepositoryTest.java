package hello.querydsl.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hello.querydsl.dto.MemberSearchCondition;
import hello.querydsl.dto.MemberTeamDto;
import hello.querydsl.entity.Member;
import hello.querydsl.entity.Team;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	EntityManager em;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void basicTest() {
		final Member member = new Member("member1", 10);
		memberRepository.save(member);

		final Member findMember = memberRepository.findById(member.getId()).get();

		assertThat(findMember).isEqualTo(member);

		final List<Member> result1 = memberRepository.findAll();

		assertThat(result1).containsExactly(member);
	}

	@Test
	void basicTest_querydsl() {
		final Member member = new Member("member1", 10);
		memberRepository.save(member);

		final Member findMember = memberRepository.findById(member.getId()).get();

		assertThat(findMember).isEqualTo(member);

		final List<Member> result1 = memberRepository.findAll();

		assertThat(result1).containsExactly(member);
	}

	@Test
	void serachTest() {
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

		final MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
		memberSearchCondition.setAgeGoe(35);
		memberSearchCondition.setAgeLoe(45);
		memberSearchCondition.setTeamName("teamB");

		final List<MemberTeamDto> findMembers = memberRepository.search(memberSearchCondition);

		assertThat(findMembers.size()).isEqualTo(1);
		assertThat(findMembers.get(0).getUsername()).isEqualTo("member4");

	}

}
