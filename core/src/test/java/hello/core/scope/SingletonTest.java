package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    public void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        System.out.println("bean1 = " + bean1);

        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isSameAs(bean2);

        //수작업으로 호출
        bean1.destroy();
        bean2.destroy();

        ac.close();

    }

    @Scope("singleton")
    static class SingletonBean{

        @PostConstruct
        public void init(){
            System.out.println("singletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("singletonBean.destroy");
        }
    }
}
