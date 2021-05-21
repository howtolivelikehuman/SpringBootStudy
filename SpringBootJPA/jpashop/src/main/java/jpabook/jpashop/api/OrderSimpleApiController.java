package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * xToOne
 * Order
 * Order - Member (M : 1)
 * Order - Delivery (1: 1)
 **/

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/vi/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getStatus();
        }
        return all;
    }
    @GetMapping("api/v2/simple-orders")
    public Result ordersV2(){

        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = all.stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return new Result(result);

    }

    @GetMapping("api/v3/simple-orders")
    public Result ordersV3(){

        List<Order> all = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = all.stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return new Result(result);
    }

    @GetMapping("api/v4/simple-orders")
    public Result ordersV4(){
        return  new Result(orderSimpleQueryRepository.findOrderDtos());
    }



    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
