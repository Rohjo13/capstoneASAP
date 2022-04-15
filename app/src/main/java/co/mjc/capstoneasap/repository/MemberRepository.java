package co.mjc.capstoneasap.repository;

import java.util.Optional;

import co.mjc.capstoneasap.dto.Member;


// Interface Repository
public interface MemberRepository {
    void save(Member member);
    Optional<Member> getId(String id);
}
