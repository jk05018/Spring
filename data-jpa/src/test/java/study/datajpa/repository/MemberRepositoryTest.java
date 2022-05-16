package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRe;

	@Test
	void basicCRUD() {
		final Member member1 = new Member("member1");
		final Member member2 = new Member("member2");

		memberRe.save(member1);
		memberRe.save(member2);

		final Member findMember1 = memberRe.findById(member1.getId()).get();
		final Member findMember2 = memberRe.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		final List<Member> all = memberRe.findAll();
		assertThat(all.size()).isEqualTo(2);

		memberRe.delete(member1);
		memberRe.delete(member2);

		final List<Member> deletedCount = memberRe.findAll();
		assertThat(deletedCount.size()).isEqualTo(0);


	}

	@Test
	void findByUsernameAndAgeGreaterThanTest() {
		final Member member1 = new Member("AAA",10);
		final Member member2 = new Member("AAA",20);

		memberRe.save(member1);
		memberRe.save(member2);

		final List<Member> result = memberRe.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);

	}

	@Test
 	void findUserQueryTest() {
		final Member member1 = new Member("AAA",10);
		final Member member2 = new Member("AAA",20);

		memberRe.save(member1);
		memberRe.save(member2);

		final List<Member> result = memberRe.findUser("AAA", 10);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(10);
		assertThat(result.size()).isEqualTo(1);

	}

}
