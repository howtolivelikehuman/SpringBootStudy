package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {
    
    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
    
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class); 
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        
        //Thread A -> 10000 주문
        statefulService1.order("userA", 10000);
        //Thread B -> 20000 주문
        statefulService2.order("userB", 20000);
        
        //A가 주문금액 조회
        int price = statefulService1.getPrice();

        //근데 20000됨
        assertThat(statefulService1.getPrice()).isNotEqualTo(20000);
    }

}