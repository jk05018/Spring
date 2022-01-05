package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemoryMemberRepositoryTest {
	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	public void save() throws Exception {
		//given
		Member member = new Member();
		member.setName("spring");
		//when
		repository.save(member);
		//then

		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);
	}

	@Test
	public void findbyName() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		//when
		Member result = repository.findByName("spring1").get();
		//then
		assertThat(result).isEqualTo(member1);
	}

	@Test
	public void findAll() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		//when
		List<Member> result = repository.findAll();
		//then
		assertThat(result.size()).isEqualTo(2);

	}
}
