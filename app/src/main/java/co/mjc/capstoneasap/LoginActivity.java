package co.mjc.capstoneasap;

import static co.mjc.capstoneasap.LoginSuccessActivity.LogTAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryMemberRepository;
import co.mjc.capstoneasap.service.MemberService;

// 참고 ls_mainPage 는 loginSuccess
// 참고 ns_mainPage 는 noneLogin
public class LoginActivity extends AppCompatActivity {

    //
    EditText edtId;
    EditText edtPwd;
    Button btn_login;


    // 싱글 인스턴스로 사용할려고 다른 방법도 있는데 이 방법밖에 생각 안남
    static MemberRepository memberRepository;
    static MemberService memberService;

    // 생성자로 DI 주입 Bean 역할
    public LoginActivity() {
        Log.d(LogTAG,"MainActivity Constructor");

        // 없으면 만든다.
        if (memberRepository == null) {
            Log.d(LogTAG,"MainActivity.memberRepository is null");
            memberRepository = new MemoryMemberRepository();
        }
        if (memberService == null) {
            Log.d(LogTAG,"MainActivity.memberService is null");
            memberService = new MemberService(memberRepository);
        }
    }


    // 앱 Main
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LogTAG,"Main onCreate");
        super.onCreate(savedInstanceState);
        // 메인 페이지
        setContentView(R.layout.activity_ns_main);


        // memberR 에서 123멤버를 만들었음
        memberRepository.set123Member();
        // login Button
        btn_login = findViewById(R.id.btn_login);
        // Login Logic
        btn_login.setOnClickListener(
                // 람다식
                view -> {
                    // ID , PWD
                    edtId = findViewById(R.id.edtId);
                    edtPwd = findViewById(R.id.edtPwd);
                    Boolean loginAccess = memberService.login(edtId, edtPwd);
                    if (loginAccess) {
                        Member member = memberService.getById(edtId);
                        Log.d(LogTAG,"MainActivity onCreate memberSchedule Size : " + member.getScheduleArrayList().size());
                        // 로그인 성공 시, 화면 전환, 화면 전환하는 클래스는 Intent
                        // 새로운 Intent 를 생성하는데, LsMainActivity 로 인스턴스 생성
                        Intent intent = new Intent(this, LoginSuccessActivity.class);

                        // 로그인 정보 넘겨주기
                        intent.putExtra("loginAccess", member);
                        // 인텐트에서 생성한 LsMainActivity 로 전환한다.
                        startActivity(intent);
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