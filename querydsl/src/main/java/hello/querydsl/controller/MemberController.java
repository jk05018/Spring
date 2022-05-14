package hello.querydsl.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.querydsl.dto.MemberSearchCondition;
import hello.querydsl.dto.MemberTeamDto;
import hello.querydsl.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberJpaRepository memberJpaRepository;

	@GetMapping("/v1/members")
	public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition){
		return memberJpaRepository.searchByBuilder(condition);
	}
}
