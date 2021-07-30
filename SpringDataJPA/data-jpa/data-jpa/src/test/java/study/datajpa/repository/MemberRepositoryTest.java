package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(true)
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @PersistenceContext EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMem1 = memberRepository.findById(member1.getId()).get();
        Member findMem2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMem1).isEqualTo(member1);
        assertThat(findMem2).isEqualTo(member2);

        //이름 변경시 더티체킹
        findMem1.setUsername("membbb1111");

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findHelloBy(){
        List<Member> helloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");

        Member member = result.get(0);
        assertThat(member).isEqualTo(m1);
    }

    @Test
    public void testQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findMember("AAA",10);

        Member member = result.get(0);
        assertThat(member).isEqualTo(m1);
    }

    @Test
    public void findUsernameList(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();
        for (String s: result) {
            System.out.println("s = " + s);
        }
    }


    @Test
    public void findMemberDto(){

        Team team = new Team("t1");
        teamRepository.save(team);

        Member m1 = new Member("AAA",10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> result = memberRepository.findMemberDto();
        for (MemberDto s: result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findByNames(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA","BBB"));
        for (Member s: result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void returnType(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> aaa = memberRepository.findListByUsername("AAA");
        Member bbb = memberRepository.findMemberByUsername("AAA");
        Optional<Member> ccc = memberRepository.findOptionalByUsername("BBB");


    }

    @Test
    public void paging(){

        //given
        memberRepository.save(new Member("mem1", 10));
        memberRepository.save(new Member("mem2", 10));
        memberRepository.save(new Member("mem3", 10));
        memberRepository.save(new Member("mem4", 10));
        memberRepository.save(new Member("mem5", 10));

        int age = 10;
        int page = 3;
        int size = 3;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> pages = memberRepository.findByAge(age, pageRequest);
        //이걸로 반환해서 뱉기
        Page<MemberDto> pageMap = pages.map(member -> new MemberDto(member.getId(), member.getUsername(), null));


        //then
        List<Member> content = pages.getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(pages.getTotalElements()).isEqualTo(5);
        assertThat(pages.getTotalPages()).isEqualTo(3);
        assertThat(pages.getNumber()).isEqualTo(0);
        assertThat(pages.isFirst()).isTrue();
        assertThat(pages.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("mem1", 10));
        memberRepository.save(new Member("mem2", 19));
        memberRepository.save(new Member("mem3", 20));
        memberRepository.save(new Member("mem4", 21));
        memberRepository.save(new Member("mem5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findByUsername("mem5");
        Member member5 = result.get(0);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member mem1 = new Member("mem1", 10, teamA);
        Member mem2 = new Member("mem2", 20, teamB);
        memberRepository.save(mem1);
        memberRepository.save(mem2);

        em.flush();
        em.clear();
        //DB에 완전히 반영되고, 영속성 컨텍스트가 완전히 날아감.


        /*member랑 team은 N:1 관계 (Lazy)
        Member 조회시 team은 조회 안함 (사용하는 시점에 조회) = 지연 로딩
         */

        //when
        List<Member> members = memberRepository.findAll();
        for (Member a : members){
            System.out.println("memberName = " + a.getUsername());
            System.out.println("memberTeam = " + a.getTeam().getName());
            System.out.println("memberTeam = " + a.getTeam().getClass());
        }

        //then
    }
}
