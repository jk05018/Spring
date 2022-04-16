package hello.jdbc.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import hello.jdbc.domain.Member;

class MemberRepositoryV0Test {

	MemberRepositoryV0 repository = new MemberRepositoryV0();

	@Test
	void crud() throws SQLException {
		// save
		Member member = new Member("memberV0", 10000);
		repository.save(member);

		// findById
		final Member findMember = repository.findById(member.getMemberId());
		assertThat(findMember, samePropertyValuesAs(member));

		// update : money : 10000 -> 20000
		repository.update(member.getMemberId(), 20000);
		final Member updatedMember = repository.findById(member.getMemberId());

		assertThat(updatedMember.getMoney(),is(20000));

		//delete
		repository.delete(member.getMemberId());

	}

}
