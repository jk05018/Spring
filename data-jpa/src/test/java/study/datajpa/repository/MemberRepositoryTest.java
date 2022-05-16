package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;

	@Test
	void basicCRUD() {
		final Member member1 = new Member("member1");
		final Member member2 = new Member("member2");

		memberRepository.save(member1);
		memberRepository.save(member2);

		final Member findMember1 = memberRepository.findById(member1.getId()).get();
		final Member findMember2 = memberRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		final List<Member> all = memberRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		memberRepository.delete(member1);
		memberRepository.delete(member2);

		final List<Member> deletedCount = memberRepository.findAll();
		assertThat(deletedCount.size()).isEqualTo(0);


	}

	@Test
	void findByUsernameAndAgeGreaterThanTest() {
		final Member member1 = new Member("AAA",10);
		final Member member2 = new Member("AAA",20);

		memberRepository.save(member1);
		memberRepository.save(member2);

		final List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);

	}

	@Test
 	void findUserQueryTest() {
		final Member member1 = new Member("AAA",10);
		final Member member2 = new Member("AAA",20);

		memberRepository.save(member1);
		memberRepository.save(member2);

		final List<Member> result = memberRepository.findUser("AAA", 10);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(10);
		assertThat(result.size()).isEqualTo(1);

	}

	@Test
	void paging() {
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));

		int age = 10;
		int page = 0;
		int size = 3;

		final PageRequest pageRequest = PageRequest.of(page, size);

		final Page<Member> pageByAge = memberRepository.findByAge(age, pageRequest);

	}

	@Test
	void bulkupdate() {
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 19));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 21));
		memberRepository.save(new Member("member5", 40));

		final List<Member> members = memberRepository.findAll();

		for (Member member : members) {
			System.out.println("member : " + member);
		}

		final int i = memberRepository.buldAgeUpdtae(20);

		final List<Member> member1 = memberRepository.findAll();

		for (Member member : member1) {
			System.out.println("member : " + member);
		}

		assertThat(i).isEqualTo(3);

		em.flush();
		em.clear();

		final List<Member> members2 = memberRepository.findAll();

		for (Member member : members2) {
			System.out.println("member2: " + member);
		}

	}
}
