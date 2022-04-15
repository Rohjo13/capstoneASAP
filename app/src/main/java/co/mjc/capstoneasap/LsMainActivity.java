package co.mjc.capstoneasap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import co.mjc.capstoneasap.repository.MemoryScheduleRepository;
import co.mjc.capstoneasap.repository.ScheduleRepository;
import co.mjc.capstoneasap.service.ScheduleService;

public class LsMainActivity extends AppCompatActivity {

    EditText date;

    ScheduleRepository scheduleRepository;
    ScheduleService scheduleService;

    
    // Schedule DI 주입
    public LsMainActivity() {
        scheduleRepository = new MemoryScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);
    }
    // 시간표 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // 오늘 날짜 넘겨주기
        //        String dayOfWeek = scheduleService.Today();
//        date = findViewById(R.id.date);
//        date.setText(dayOfWeek);
        // 메뉴바 생성
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls_main);
    }
}