package hello.querydsl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hello.querydsl.dto.MemberSearchCondition;
import hello.querydsl.dto.MemberTeamDto;

public interface MemberRepositoryCustom {
	List<MemberTeamDto> search(MemberSearchCondition condition);
	Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
	Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);

}
