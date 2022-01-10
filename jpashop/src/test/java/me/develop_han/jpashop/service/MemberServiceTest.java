package me.develop_han.jpashop.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import me.develop_han.jpashop.domain.Member;
import me.develop_han.jpashop.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
		//given
		Member member = new Member();
		member.setName("seunghan");
		//when
		Long joinedId = memberService.join(member);
		//then
		Member findMember = memberService.findOne(joinedId);
		assertThat(member).isEqualTo(findMember);
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}

	@Test
	public void 중복_회원_예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("seunghan");

		Member member2 = new Member();
		member2.setName("seunghan");
		//when
		memberService.join(member1);
		assertThrows(IllegalStateException.class,() -> memberService.join(member2));

		//then
	}

}
