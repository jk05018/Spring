package me.develop_han.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import me.develop_han.jpashop.domain.Address;
import me.develop_han.jpashop.domain.Book;
import me.develop_han.jpashop.domain.Item;
import me.develop_han.jpashop.domain.Member;
import me.develop_han.jpashop.domain.Order;
import me.develop_han.jpashop.domain.OrderStatus;
import me.develop_han.jpashop.exception.NotEnoughStockException;
import me.develop_han.jpashop.repository.OrderRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@PersistenceContext EntityManager em;

	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;

	@Test
	public void 상품주문() throws Exception{
		//given
		Member member = createMember();
		Item item = createBook("시골 JPA",10000,10);
		int orderCount = 2;

		//when
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		//then
		Order getOrder = orderRepository.findOne(orderId);

		assertEquals(OrderStatus.ORDER,getOrder.getOrderStatus(),"상품 주문 시 상태는 ORDER");
		assertEquals(getOrder.getOrderItems().size(),1,"주문한 상품의 종류 수가 정확해야 한다.");
		assertEquals(10000*2,getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다");
		assertEquals(item.getStockQuantity(),8,"주문 수량만큼 재고가 줄어들어야 한다.");
	}

	@Test
	public void 상품주문_재고수량초과() throws Exception{
		Member member = createMember();
		Item item = createBook("시골 JPA",10000,10);
		int orderCount = 11;

		assertThrows(NotEnoughStockException.class,() -> orderService.order(member.getId(), item.getId(), orderCount));
	}

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setStockQuantity(stockQuantity);
		book.setPrice(price);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("seunghan");
		member.setAddress(new Address("서울", "강가","123-123"));
		em.persist(member);
		return member;

	}

}
