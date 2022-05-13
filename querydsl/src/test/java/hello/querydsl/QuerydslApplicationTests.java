package hello.querydsl;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.querydsl.entity.Hello;
import hello.querydsl.entity.QHello;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

	@Autowired
	EntityManager entityManager;

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		entityManager.persist(hello);

		final JPAQueryFactory query = new JPAQueryFactory(entityManager);
		QHello qHello = new QHello("h");

		final Hello result = query
			.selectFrom(qHello)
			.fetchOne();

		assertThat(result).isEqualTo(hello);
	}

}
