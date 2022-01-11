package me.develop_han.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

	private String memberName;
	private OrderStatus orderStatus;
}
