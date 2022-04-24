package co.mjc.capstoneasap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;
import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryMemberRepository;
import co.mjc.capstoneasap.repository.MemoryScheduleRepository;
import co.mjc.capstoneasap.repository.ScheduleRepository;
import co.mjc.capstoneasap.service.MemberService;
import co.mjc.capstoneasap.service.ScheduleService;

public class LsMainActivity extends AppCompatActivity {

    TextView dayOfWeek;
    TextView selectDate;
    TextView loginData;


    // 로그인 한 회원을 알아야 한다.
    ScheduleRepository scheduleRepository;
    ScheduleService scheduleService;
    MemberRepository memberRepository;
    MemberService memberService;
    Member loginMember;

    // Schedule DI(?) 주입
    public LsMainActivity() {
        scheduleRepository = new MemoryScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        Intent intent = getIntent();
        loginMember = (Member)intent.getSerializableExtra("123");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls_main);
        // 오늘 날짜
        loginData = findViewById(R.id.loginData);

        // 회원님 아이디
        loginData.setText("안녕하세요 "+ loginMember.getMemId() + "님!");
        dayOfWeek = findViewById(R.id.dayofweek);
        dayOfWeek.setText(scheduleService.dateCheck());
    }

    // 메뉴바 생성 : 추가, 수정, 삭제, 로그아웃
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 메뉴 자언 추가, 수정, 삭제, 로그아웃이 선택되었을 때
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createSchedule:
                setCreateSchedule();
                break;
//            case R.id.editSchedule:
//                break;
            case R.id.deleteSchedule:
                deleteCreateSchedule();
                break;
            case R.id.logout:
                logout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setCreateSchedule() {

        // schedule을 만드는 Dialog 생성
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.createschedule);
        dialog.setTitle("시간표 추가");

        Button addSchedule = dialog.findViewById(R.id.addSchedule);
        Button cancelSchedule = dialog.findViewById(R.id.cancelAddSchedule);

        EditText createNameSchedule = dialog.findViewById(R.id.createNameSchedule);
        selectDate = dialog.findViewById(R.id.selectDate);


        // schedule 객체 생성
        Schedule schedule = new Schedule();


        // 요일을 설정하는 팝업 메뉴
        selectDate.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(this, view);
            popup.getMenuInflater().inflate(R.menu.datepopup_schedule, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.dateMon:
                        schedule.setDayOTW(ScheduleEnum.MONDAY);
                        selectDate.setText("월요일");
                        break;
                    case R.id.dateTUE:
                        schedule.setDayOTW(ScheduleEnum.TUESDAY);
                        selectDate.setText("화요일");
                        break;
                    case R.id.dateWED:
                        schedule.setDayOTW(ScheduleEnum.WEDNESDAY);
                        selectDate.setText("수요일");
                        break;
                    case R.id.dateTHU:
                        schedule.setDayOTW(ScheduleEnum.THURSDAY);
                        selectDate.setText("목요일");
                        break;
                    case R.id.dateFRI:
                        schedule.setDayOTW(ScheduleEnum.FRIDAY);
                        selectDate.setText("금요일");
                        break;
                    default:
                        break;
                }
                return true;
            });
            popup.show();
        });

        // 강의 이름 설정 : ex) 자바캡스톤디자인
        schedule.setLecName(createNameSchedule.getText().toString());

        // 회원의 데이터에 schedule setting
        loginMember.setSchedule(schedule);

        // 확인 버튼인데, 데이터가 다 입력되어야만 추가 완료
        addSchedule.setOnClickListener(view1 -> {
            if (createNameSchedule.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),
                        "전부 입력해주세요.", Toast.LENGTH_LONG).show();
            } else {
            Toast.makeText(getApplicationContext(),
                    "강의가 추가 완료되었습니다.", Toast.LENGTH_LONG).show();
            dialog.dismiss();
            }
        });

        // 취소 버튼
        cancelSchedule.setOnClickListener(view2 -> dialog.dismiss());
        dialog.show();
    }

    // Deprecated
    // 현재는 사용하지 않는다.
    @Deprecated
    public void deleteCreateSchedule() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("진짜");
        alertDialogBuilder.setMessage("삭제 하시겠습니까?");
        alertDialogBuilder.setPositiveButton("yes", (dialogInterface, i) -> {
            Toast.makeText(LsMainActivity.this,
                    "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show();
        });
        alertDialogBuilder.setNegativeButton("no", (dialogInterface, i) -> {
            Toast.makeText(LsMainActivity.this,
                    "삭제가 취소되었습니다.", Toast.LENGTH_LONG).show();
//            finish(); // 액티비티 종료 우리는 액티비티가 하나이므로, 앱 자체가 종료됨 액티비티가 많으면
            // 하나의 액티비티만 종료한다.
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // 초기화면 로그아웃
    public void logout() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // 로그인한 멤버는 이제 없음
        loginMember = null;
        startActivity(intent);
    }
}