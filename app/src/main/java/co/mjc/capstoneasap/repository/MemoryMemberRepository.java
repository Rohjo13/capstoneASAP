package co.mjc.capstoneasap.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import co.mjc.capstoneasap.domain.Member;

public class MemoryMemberRepository implements MemberRepository {

    private final Map<String, Member> dbMap;

    // 임시 DB는 HashMap 사용
    public MemoryMemberRepository() {
        dbMap = new HashMap<>();

        // default ID -> 로그인 기능 체크 Test로 만들어서 사용하지 않았음
        Member member = new Member();
        member.setMemID("123");
        member.setMemMail("123@123.com");
        member.setMemPw("123");
        dbMap.put(member.getMemID(), member);
    }


    // Save 기능 구현 x
    @Override
    public void save(Member member) {
        dbMap.put(member.getMemID(), member);
    }

    // getId
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Member> getId(String id) {
        return Optional.ofNullable(dbMap.get(id));
    }

    // member Delete
    @Override
    public void delete(String id) {
        dbMap.remove(id);
    }
}
