package co.mjc.capstoneasap.service;

import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.repository.MemberRepository;


// 모든 Service 객체
public class MemberService extends AppCompatActivity {

//    EditText edtId;
//    EditText edtPwd;

    MemberRepository memberRepository;

    // MainActivity(Controller, Bean 과 같은 역할로 생각한다.) 에서 DI 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 값을 받고 해쉬맵에는 있는지? 판단
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean login(EditText edtId,EditText edtPwd) {
        String id = edtId.getText().toString();
        String pwd = edtPwd.getText().toString();
        Optional<Member> member = memberRepository.getId(id);
        System.out.println("아이디 : " + id );
        System.out.println("패스워드 : " + pwd);
        if (member.isPresent()
                && member.get().getMemId().equals(id) && member.get().getMemPw().equals(pwd))
            return true;
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Member getById(EditText id) {
        String memId = id.getText().toString();
        return memberRepository.getId(memId).get();
    }


    @Deprecated
    public Boolean logout() {
        return false;
    }

    @Deprecated
    public void save(Member member) {
        // 여기서부터 다시 수정 중복 여부 검사
        memberRepository.save(member);
    }

}
