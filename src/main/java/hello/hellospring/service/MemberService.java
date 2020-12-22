package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    public Long join(Member member){ // 회원가입

        /*//동명이인 중복회원 불가 조건.
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
            // result에 null이 아니라 어떠한 값이 있으면 로직이 동작. Optional이기 때문에 가능.
        });*/
        // 안이뻐서. 어짜피 result가 반환이 됐기 때문에 바로 ifPresent가 들어갈 수 있음.
        // 이렇게 로직이 나오는 경우, 메서드로 뽑는게 좋음.
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers(){ // 전체 회원 조회
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
