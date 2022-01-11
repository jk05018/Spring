package me.develop_han.jpashop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.develop_han.jpashop.domain.DelivertStatus;
import me.develop_han.jpashop.domain.Delivery;
import me.develop_han.jpashop.domain.Item;
import me.develop_han.jpashop.domain.Member;
import me.develop_han.jpashop.domain.Order;
import me.develop_han.jpashop.domain.OrderItem;
import me.develop_han.jpashop.repository.ItemRepository;
import me.develop_han.jpashop.repository.MemberRepository;
import me.develop_han.jpashop.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;

	/** 주문 **/
	@Transactional
	public Long order(Long memberId, Long itemId, int count){
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		delivery.setStatus(DelivertStatus.READY);

		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		Order order = Order.createOrder(member, delivery, orderItem);

		orderRepository.save(order);
		return order.getId();
	}

	@Transactional
	public void cancleOrder(Long orderId){
		Order order = orderRepository.findOne(orderId);
		order.cancel();
	}
}
