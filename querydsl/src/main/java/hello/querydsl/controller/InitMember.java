package hello.querydsl.controller;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import hello.querydsl.entity.Member;
import hello.querydsl.entity.Team;
import lombok.RequiredArgsConstructor;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

	private final InitMemberService initMemberService;

	@PostConstruct
	public void init(){
		initMemberService.init();
	}

	@Component
	static class InitMemberService{
		@PersistenceContext
		EntityManager em;

		// sample data 넣기
		@Transactional
		public void init(){
			final Team teamA = new Team("teamA");
			final Team teamB = new Team("teamB");
			em.persist(teamA);
			em.persist(teamB);

			for (int i = 0; i < 100; ++i) {
				Team selectedTeam = i % 2 == 0 ? teamA : teamB;
				em.persist(new Member("member" + i, i, selectedTeam));
			}



		}
	}
}
