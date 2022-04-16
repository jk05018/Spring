package hello.jdbc.repository;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zaxxer.hikari.HikariDataSource;

import hello.jdbc.domain.Member;

class MemberRepositoryV1Test {

	MemberRepositoryV1 repository;

	@BeforeEach
	void beforeEach() {
		// final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, username, password);

		final HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		repository = new MemberRepositoryV1(dataSource);
	}

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
		assertThrows(NoSuchElementException.class,() ->  repository.findById(member.getMemberId()));

	}

}
