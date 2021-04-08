package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {
        //persistence.xml에 있는 값
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setId(1l);
            member.setUsername("A");
            member.setRoleType(RoleType.USER);
            em.persist(member);

            User user = new User();
            em.persist(user);
            System.out.println("user.id = " + user.getId());

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
