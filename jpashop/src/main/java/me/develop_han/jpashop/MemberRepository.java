package me.develop_han.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	@PersistenceContext
	EntityManager em;

	public Long save(Member member){
		em.persist(member);
		return member.getId();
	}

	public Member find(Long memberId){
		return em.find(Member.class,memberId);
	}
}
