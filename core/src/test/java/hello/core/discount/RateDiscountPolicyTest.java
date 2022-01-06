package hello.core.discount;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.core.member.Grade;
import hello.core.member.Member;

class RateDiscountPolicyTest {

	RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

	@Test
	@DisplayName("VIP는 10% 할인이 적용되어야 한다")
	public void vip할인() throws Exception{
		//given
		Member member = new Member(1L, "seunghan", Grade.VIP);

		//when
		int discountPrice = discountPolicy.discount(member, 10000);
		//then

		Assertions.assertThat(discountPrice).isEqualTo(1000);
	}

	@Test
	@DisplayName("VIP가 아니라면 할인은 적용되지 않아야 한다")
	public void vip할인_X() throws Exception{
		//given
		Member member = new Member(1L, "seunghan", Grade.BASIC);

		//when
		int discountPrice = discountPolicy.discount(member, 10000);
		//then

		Assertions.assertThat(discountPrice).isEqualTo(0);
	}

}
