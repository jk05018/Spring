package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	void basicCRUD() {
		final Member member1 = new Member("member1");
		final Member member2 = new Member("member2");

		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		final Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		final Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		final List<Member> all = memberJpaRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);

		final List<Member> deletedCount = memberJpaRepository.findAll();
		assertThat(deletedCount.size()).isEqualTo(0);


	}
}
