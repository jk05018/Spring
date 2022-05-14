package hello.querydsl.repository;

import static hello.querydsl.entity.QMember.*;
import static hello.querydsl.entity.QTeam.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.querydsl.dto.MemberSearchCondition;
import hello.querydsl.dto.MemberTeamDto;
import hello.querydsl.dto.QMemberTeamDto;
import hello.querydsl.entity.Member;
import hello.querydsl.entity.QTeam;

@Repository
public class MemberJpaRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public MemberJpaRepository(EntityManager em) {
		this.em = em;
		queryFactory = new JPAQueryFactory(em);
	}

	public void save(Member member) {
		em.persist(member);
	}

	public Optional<Member> findById(Long id) {
		final Member findMember = em.find(Member.class, id);
		return Optional.ofNullable(findMember);
	}

	public Optional<Member> findById_querydsl(Long id){
		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(member.id.eq(id))
				.fetchOne()
		);
	}

	public List<Member> findAll() {
		return em
			.createQuery("select m from Member m", Member.class)
			.getResultList();
	}

	// querydsl version find All
	public List<Member> findALl_querydsl(){
		return queryFactory
			.selectFrom(member)
			.fetch();
	}

	public List<Member> findByUsername(String username) {
		return em
			.createQuery("select m from Member m where m.username  = :username", Member.class)
			.setParameter("username", username)
			.getResultList();
	}

	// queryDsl version findByUsername
	public List<Member> findByUsername_querydsl(String username){
		return queryFactory
			.selectFrom(member)
			.where(member.username.eq(username))
			.fetch();
	}

	public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition){
		final BooleanBuilder builder = new BooleanBuilder();

		if(hasText(condition.getUsername())){
			builder.and(member.username.eq(condition.getUsername()));
		}

		if(hasText(condition.getTeamName())){
			builder.and(team.name.eq(condition.getTeamName()));
		}

		if (condition.getAgeGoe() != null) {
			builder.and(member.age.goe(condition.getAgeGoe()));
		}

		if(condition.getAgeLoe() != null){
			builder.and(member.age.loe(condition.getAgeLoe()));
		}

		return queryFactory
			.select(new QMemberTeamDto(
				member.id.as("memberId"),
				member.username,
				member.age,
				team.id.as("teamId"),
				team.name.as("teamName")
			))
			.from(member)
			.leftJoin(member.team, team)
			.where(builder)
			.fetch();

	}

	public List<MemberTeamDto> searchByWhereParameter(MemberSearchCondition condition){
		return queryFactory
			.select(new QMemberTeamDto(
				member.id.as("memberId"),
				member.username,
				member.age,
				team.id.as("teamId"),
				team.name.as("teamName")
			))
			.from(member)
			.leftJoin(member.team, team)
			.where(
				usernameCond(condition.getUsername()),
				teamNameCond(condition.getTeamName()),
				ageGoeCond(condition.getAgeGoe()),
				ageLoeCond(condition.getAgeLoe())
			)
			.fetch();
	}

	private BooleanExpression usernameCond(String username){
		return isEmpty(username) ? null : member.username.eq(username);
	}

	private BooleanExpression teamNameCond(String teamName){
		return isEmpty(teamName) ? null : team.name.eq(teamName);
	}

	private BooleanExpression ageGoeCond(Integer age){
		return age == null ? null : member.age.goe(age);
	}

	private BooleanExpression ageLoeCond(Integer age){
		return age == null ? null : member.age.loe(age);
	}

}
