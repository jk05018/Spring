package me.develop_han.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import me.develop_han.jpashop.domain.Order;
import me.develop_han.jpashop.domain.OrderSearch;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public void save(Order order){
		em.persist(order);
	}

	public Order findOne(Long id){
		return em.find(Order.class, id);
	}

	public List<Order> findAllByString(OrderSearch orderSearch){
		//language=JPAQL
		String jpql = "select o From Order o join o.member m";
		boolean isFirstCondition = true;
		//주문 상태 검색
		if (orderSearch.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		}
		//회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}
		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
			.setMaxResults(1000); //최대 1000건
		if (orderSearch.getOrderStatus() != null) {
			query = query.setParameter("status", orderSearch.getOrderStatus());
		}
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query = query.setParameter("name", orderSearch.getMemberName());
		}
		return query.getResultList();
	}

	public List<Order> findAllByStatus(OrderSearch orderSearch){
		return em.createQuery("select o from Order o where o.orderStatus = :status",Order.class)
			.setParameter("status",orderSearch.getOrderStatus()).getResultList();
	}
}
