package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 2);

            Order order = createOrder(member, orderItem1, orderItem2);
            em.persist(order);

        }

        public void dbInit2(){
            Member member = createMember("userB", "부산", "2", "2222");
            em.persist(member);

            Book book1 = createBook("Spring1 Book", 20000, 300);
            Book book2 = createBook("Spring2 Book", 25000, 400);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 50000, 2);

            Order order = createOrder(member, orderItem1, orderItem2);
            em.persist(order);

        }
    }

    private static Order createOrder(Member member, OrderItem orderItem1, OrderItem orderItem2) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return Order.createOrder(member, delivery, orderItem1, orderItem2);
    }

    private static Book createBook(String bookname, int price, int stock) {
        Book book = new Book();
        book.setName(bookname);
        book.setPrice(price);
        book.setStockQuantity(stock);
        return book;
    }

    private static Member createMember(String username, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(username);
        member.setAddress(new Address(city, street, zipcode));
        return member;
    }
}


