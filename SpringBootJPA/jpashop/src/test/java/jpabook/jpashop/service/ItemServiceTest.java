package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {


    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 상품등록() throws Exception{
        //given
        Item item = new Book();
        item.setName("MyBook");

        //when
        Long id = itemService.saveItem(item);

        //then
        em.flush();
        assertEquals(item, itemRepository.findOne(id));
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고_감소() throws Exception{
        //given
        Item item = new Album();
        item.addStock(500);
        Long id = itemService.saveItem(item);

        //when
        Item newitem = itemService.findOne(id);
        newitem.removeStock(501);

        //then
        fail("재고가 부족합니다.");
    }
}