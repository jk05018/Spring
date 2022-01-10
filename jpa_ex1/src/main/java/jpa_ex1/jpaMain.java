package jpa_ex1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class jpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		//
		EntityManager em = emf.createEntityManager();
		//
		// EntityTransaction tx = em.getTransaction();
		// tx.begin();
		//
		// Member member = new Member();
		// member.setId(1L);
		// member.setName("hello");
		//
		// em.persist(member);
		//
		// tx.commit();
		//
		//
		// // 코드
		em.close();
		//
		emf.close();

	}
}
