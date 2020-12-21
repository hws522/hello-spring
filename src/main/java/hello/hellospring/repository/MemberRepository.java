package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); // 회원이 저장소에 저장됨.
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 리스트로 저장된 모든 회원 반환.
}
