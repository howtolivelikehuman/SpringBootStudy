package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();
    @Test
    @DisplayName("VIP는 10% 할인")
    void vip_o() {
        //GIVEN
        Member memberVIP = new Member(1l, "memberVIP", Grade.VIP);
        //WHEN
        int discount = discountPolicy.discount(memberVIP,10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP 아니면 할인 X")
    void vip_x(){
        //GIVEN
        Member memberBASIC = new Member(1l, "memberVIP", Grade.BASIC);
        //WHEN
        int discount = discountPolicy.discount(memberBASIC,10000);
        //then
        assertThat(discount).isEqualTo(0);
    }
}