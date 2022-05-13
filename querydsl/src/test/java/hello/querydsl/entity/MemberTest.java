package hello.querydsl.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Transactional
// @Commit
class MemberTest {

	@Autowired
	EntityManager em;

	@Test
	void testEntity() {
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

		// 초기화
		em.flush();
		em.clear();

		final List<Member> members = em.createQuery("select m from Member m", Member.class)
			.getResultList();

		assertThat(members)
			.extracting("id")
			.containsExactlyInAnyOrder(member1.getId(), member2.getId(), member3.getId(), member4.getId());
	}
}
