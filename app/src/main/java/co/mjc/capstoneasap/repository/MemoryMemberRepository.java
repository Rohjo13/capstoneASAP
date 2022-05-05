package co.mjc.capstoneasap.repository;

import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;

public class MemoryMemberRepository implements MemberRepository {

    private final Map<String, Member> dbMap;

    // 임시 DB는 HashMap 사용
    // 생성자에서 그냥 만든거 없어도 됌 테스트 용
    public MemoryMemberRepository() {
        // 얘만 필요하다.
        dbMap = new HashMap<>();

        // 이하 필요없음
        ArrayList<Schedule> arrayList = new ArrayList();
        // default ID -> 로그인 기능 체크 Test로 만들어서 사용하지 않았음
        Member member = new Member();
        member.setMemId("123");
        member.setMemMail("123@123.com");
        member.setMemPw("123");
        // list도 default로 만들어서 줬음
        member.setScheduleArrayList(arrayList);
        ArrayList<ImageView> cameraDataList = new ArrayList<>();
        member.setCameraDataList(cameraDataList);


        dbMap.put(member.getMemId(), member);
    }

    // Save
    @Override
    public void save(Member member) {
        dbMap.put(member.getMemId(), member);
    }

    // getId, getId는 로그인과 로그아웃을 담당합니다.
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Member> getId(String id) {
        return Optional.ofNullable(dbMap.get(id));
    }
}
