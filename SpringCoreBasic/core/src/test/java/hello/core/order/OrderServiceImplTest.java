package hello.core.order;

import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class OrderServiceImplTest {

    @Test
    public void createOrder(){
        //이러면 nullpoint exception 발생 -> 수정자 주입이라 discountpolicy 등이 누락이 됨
        //테스트 짜는 입장에서는 의존관계가 눈에 들어오지 않기 때문.
        //하지만 생성자 주입시 컴파일 오류로 알아낼 수 있음
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1l, "name", Grade.VIP));
        OrderServiceImpl os = new OrderServiceImpl(memberRepository, new RateDiscountPolicy());

        Order order = os.createOrder(1L, "member" , 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}
