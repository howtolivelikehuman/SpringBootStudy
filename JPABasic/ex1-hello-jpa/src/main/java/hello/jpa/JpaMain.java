package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //persistence.xml에 있는 값
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //삽입
        //사실 이 과정을 스프링이 다 해줌
        try {
            //비영속
            Member member = new Member(11L, "홍길동");
            //영속
            em.persist(member);

            //조회
            Member find = em.find(Member.class, 11L);
            System.out.println("findMem = " + find.getId() + " " + find.getUsername());

            //비영속
            Member member2 = new Member(12l,"박정무");
            //영속
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }

        List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5)
                .setMaxResults(8)
                .getResultList();
        System.out.println("result = " + result);


        tx.begin();

        try {
            //찾고
            Member findMem = em.find(Member.class, 11L);
            //그냥 바꾸기
            findMem.setUsername("임꺽정");
            em.flush();
            System.out.println("==================");
            //persist 없어도 됨
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();

            tx.rollback();
        } finally {
            //WEB의 경우 WAS가 내려갈때 엔티티매니저도 내려줘야함.
            em.close();
        }


        //close
        emf.close();
    }
}
