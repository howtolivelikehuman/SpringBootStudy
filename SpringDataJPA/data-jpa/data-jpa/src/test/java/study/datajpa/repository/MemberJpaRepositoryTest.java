package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;
    @Test
    public void testMember(){
        System.out.println("memberJpaRepository = " + memberJpaRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMem1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMem2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMem1).isEqualTo(member1);
        assertThat(findMem2).isEqualTo(member2);

        //이름 변경시 더티체킹
        findMem1.setUsername("membbb1111");

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deleteCount = memberJpaRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("AAA");

        Member member = result.get(0);
        assertThat(member).isEqualTo(m1);
    }

    @Test
    public void paging(){

        //given
        memberJpaRepository.save(new Member("mem1", 10));
        memberJpaRepository.save(new Member("mem2", 10));
        memberJpaRepository.save(new Member("mem3", 10));
        memberJpaRepository.save(new Member("mem4", 10));
        memberJpaRepository.save(new Member("mem5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //페이지 계산 공식
        //totalPage -> 카운트/사이즈
        //마지막 페이지인지..
        //최초 페이지인지.. 등

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkUpdate(){
        memberJpaRepository.save(new Member("mem1", 10));
        memberJpaRepository.save(new Member("mem2", 19));
        memberJpaRepository.save(new Member("mem3", 20));
        memberJpaRepository.save(new Member("mem4", 21));
        memberJpaRepository.save(new Member("mem5", 40));

        int resultCount = memberJpaRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);

    }

}