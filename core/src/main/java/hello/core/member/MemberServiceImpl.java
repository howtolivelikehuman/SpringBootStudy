package hello.core.member;

public class MemberServiceImpl implements MemberService{
    //인터페이스와 구현체 모두에 의존하게 됨 -> DIP 위반
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //Test용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
