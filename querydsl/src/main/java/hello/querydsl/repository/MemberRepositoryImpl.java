package hello.querydsl.repository;

import static hello.querydsl.entity.QMember.*;
import static hello.querydsl.entity.QTeam.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.querydsl.dto.MemberSearchCondition;
import hello.querydsl.dto.MemberTeamDto;
import hello.querydsl.dto.QMemberTeamDto;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<MemberTeamDto> search(MemberSearchCondition condition){
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

	@Override
	public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
		final List<MemberTeamDto> content = queryFactory
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
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<>(content, pageable, content.size());

	}

	@Override
	public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
		final List<MemberTeamDto> content = queryFactory
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
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		final long count = queryFactory
			.selectFrom(member)
			.leftJoin(member.team, team)
			.where(
				usernameCond(condition.getUsername()),
				teamNameCond(condition.getTeamName()),
				ageGoeCond(condition.getAgeGoe()),
				ageLoeCond(condition.getAgeLoe())
			)
			.fetchCount();

		return new PageImpl<>(content, pageable, content.size());
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
