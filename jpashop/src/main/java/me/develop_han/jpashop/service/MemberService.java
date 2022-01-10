package me.develop_han.jpashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.develop_han.jpashop.domain.Member;
import me.develop_han.jpashop.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private final MemberRepository memberRepository;


	/**
	 * 회원가입
	 */

	@Transactional
	public Long join(Member member){
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member){
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("[ERROR] 이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId){
		return memberRepository.findOne(memberId);
	}



}
