package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memId = 1L;
        Member member = new Member(memId, "memA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memId, "itemA", 10000);
        System.out.println("ORDER = "+ order);
    }
}
