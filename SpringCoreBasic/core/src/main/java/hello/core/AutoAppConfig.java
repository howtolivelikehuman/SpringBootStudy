package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter( //제외할 타입-> @Configuration, 우리가 만든 AppConfig 예제와 충돌 안하려고
                type =  FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {


    /*    @Bean(name = "memoryMemberRepository")
        MemberRepository memberRepository(){
            return new MemoryMemberRepository();
        }
     */
}
