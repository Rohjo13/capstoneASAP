package co.mjc.capstoneasap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryMemberRepository;
import co.mjc.capstoneasap.service.MemberService;

// 참고 ls_mainPage 는 loginSuccess
// 참고 ns_mainPage 는 noneLogin
public class MainActivity extends AppCompatActivity {
    EditText edtId;
    EditText edtPwd;
    Button btn_login;
    MemberRepository memberRepository;
    MemberService memberService;
    public MainActivity() {
        System.out.println("생성자 실행됨");
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    // 앱 Main
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 메인 페이지
        setContentView(R.layout.activity_ns_main);
        // login Button
        btn_login = findViewById(R.id.btn_login);
        // ID , PWD
        edtId = findViewById(R.id.edtId);
        edtPwd = findViewById(R.id.edtPwd);
        // Login Logic
        btn_login.setOnClickListener(
                // 람다식
                view -> {
            if(memberService.login(edtId,edtPwd)) {
                setContentView(R.layout.activity_ls_main);
            }
            // 비밀번호 틀림 || 아이디 잘못 입력
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("경고!").setMessage("존재하지 않는 회원이거나 비밀번호가 다릅니다.");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}