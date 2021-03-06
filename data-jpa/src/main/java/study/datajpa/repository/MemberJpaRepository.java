package study.datajpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import study.datajpa.entity.Member;

@Repository
public class MemberJpaRepository {

	@PersistenceContext
	private EntityManager em;

	public Member save(Member member) {
		em.persist(member);

		return member;
	}

	public void delete(Member member) {
		em.remove(member);
	}

	public List<Member> findAll() {
		return em
			.createQuery("select m from Member m", Member.class)
			.getResultList();
	}

	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(em.find(Member.class, id));
	}

	private long count() {
		return em
			.createQuery("select count(m) from Member m", Long.class)
			.getSingleResult();
	}

	public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
		return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member.class)
			.setParameter("username", username)
			.setParameter("age", age)
			.getResultList();
	}

	public List<Member> findByPage(int age, int offset, int limit) {
		return em.createQuery("select m from Member m where m.age = :age", Member.class)
			.setParameter("age", age)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.getResultList();
	}

	public long totalCount(int age) {
		return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
			.setParameter("age", age)
			.getSingleResult();
	}

}
