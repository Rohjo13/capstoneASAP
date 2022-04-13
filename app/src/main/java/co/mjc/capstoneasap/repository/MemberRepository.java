package co.mjc.capstoneasap.repository;

import java.util.Optional;

import co.mjc.capstoneasap.domain.Member;


// Interface Repository
public interface MemberRepository {
    void save(Member member);
    Optional<Member> getId(String id);
    void delete(String id);

}
