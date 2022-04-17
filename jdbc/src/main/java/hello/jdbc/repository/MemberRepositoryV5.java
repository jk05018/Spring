package hello.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;

/**
 * 예외 누수 문제 해결
 */
@Repository
public class MemberRepositoryV5 implements MemberRepository {
	private static final Logger log = LoggerFactory.getLogger(MemberRepositoryV5.class);

	private final JdbcTemplate jdbcTemplate;

	public MemberRepositoryV5(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(Member member) {
		// 김영한 강사님처럼 이런 방법도 있겠지만 try catch문으로 알아서 닫아주도록 하는 방법도 있다.
		// 일일히 DriverManager에서 Connection을 얻어와서 쿼리를 날리는 방법 -> 외부 Resource 매우 낭비 -> Connection Pool 개념 등
		String SAVE_QUERY = "INSERT INTO member(member_id, money) VALUES (?,?)";

		jdbcTemplate.update(SAVE_QUERY, member.getMemberId(), member.getMoney());
		return member;
	}

	@Override
	public Member findById(String memberId) {
		// 김영한 강사님처럼 이런 방법도 있겠지만 try catch문으로 알아서 닫아주도록 하는 방법도 있다.
		// 일일히 DriverManager에서 Connection을 얻어와서 쿼리를 날리는 방법 -> 외부 Resource 매우 낭비 -> Connection Pool 개념 등
		String search_query = "SELECT * FROM member WHERE member_id = ?";

		return jdbcTemplate.queryForObject(search_query, memberRowMapper(), memberId);
	}

	private RowMapper<Member> memberRowMapper() {
		return (rs, rowNum) -> {
			Member member = new Member();
			member.setMemberId(rs.getString("member_id"));
			member.setMoney(rs.getInt("money"));
			return member;
		};

	}

	@Override
	public void update(String memberId, int money) {
		String update_query = "UPDATE member SET money = ? WHERE member_id = ?";

		jdbcTemplate.update(update_query, money, memberId);
	}

	@Override
	public void delete(String memberId) {
		String delete_query = "DELETE FROM member WHERE member_id = ?";

		jdbcTemplate.update(delete_query);
	}

}
