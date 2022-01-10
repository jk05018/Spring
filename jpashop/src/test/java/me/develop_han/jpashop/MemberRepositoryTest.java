package me.develop_han.jpashop;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback
	public void saveAndFindTest() throws Exception{
		Member member = new Member();
		member.setName("seunghan");
		memberRepository.save(member);

		Member findMember = memberRepository.find(member.getId());
		assertThat(findMember.getName()).isEqualTo(member.getName());
		assertThat(findMember).isEqualTo(member);
	}

}
