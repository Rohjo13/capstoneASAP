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

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean login(EditText edtId,EditText edtPwd) {
        String id = edtId.getText().toString();
        String pwd = edtPwd.getText().toString();
        Optional<Member> member = memberRepository.getId(id);
        if (member.isPresent()
                && member.get().getMemID().equals(id) && member.get().getMemPw().equals(pwd))
            return true;
        return false;
    }
}
