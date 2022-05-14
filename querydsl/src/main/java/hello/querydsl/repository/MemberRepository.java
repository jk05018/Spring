package hello.querydsl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.querydsl.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> , MemberRepositoryCustom{
	List<Member> findByUsername(String username);

}
