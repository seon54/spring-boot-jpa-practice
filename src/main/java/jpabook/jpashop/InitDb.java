package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "강남", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000);
            Book book2 = createBook("JPA2 Book", 20000);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);

            em.persist(order);
        }
        public void dbInit2() {
            Member member = createMember("userB", "서울", "강남", "1111");
            em.persist(member);

            Book book1 = createBook("Spring1 Book", 10000);
            Book book2 = createBook("Spring2 Book", 20000);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);

            em.persist(order);
        }
    }

    public static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    public static Book createBook(String name, int price) {
        Book book1 = new Book();
        book1.setName(name);
        book1.setPrice(price);
        book1.setStockQuantity(100);
        return book1;
    }

    private static Member createMember(String username, String city, String street, String zipcode) {
        Member member = new Member();
        member.setUsername(username);         ;
        member.setAddress(new Address(city, street, zipcode));
        return member;
    }
}
