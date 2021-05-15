package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final만 생성자 만들기
public class MemberService {

    //field Injection
    //@Autowired
   private final MemberRepository  memberRepository;

    // setter Injection
    //@Autowired
    // public void setMemberRepository(MemberRepository memberRepository){
    //    this.memberRepository = memberRepository;
    //}

    // Constructor Injection
    // @Autowired 최신 스프링은 이거 생략 가능
    //public MemberService(MemberRepository memberRepository){
    //    this.memberRepository = memberRepository;
    //}

    /** 회원가입 **/
    @Transactional //변경
    public Long join (Member member){
        //중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //실무에서는 멀티스레드에서 최후의 방어를 해야함 -> member name을 유니크 제약조건으로
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /** 전체 회원 조회 **/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    /**한명 조회**/
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
