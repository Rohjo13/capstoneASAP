package co.mjc.capstoneasap.service;

import android.app.Dialog;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Optional;

import co.mjc.capstoneasap.LsMainActivity;
import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;
import co.mjc.capstoneasap.repository.ScheduleRepository;

public class ScheduleService {
    ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    // 스케쥴 저장
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }


    // 스케쥴 꺼내옴(임시)
    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Schedule> getScheduleOne(String lecName) {
        Optional<Schedule> scheduleObject = scheduleRepository.getScheduleId(lecName);
        if(scheduleObject.isPresent()) return scheduleObject;
        return Optional.empty();
    }


    // 오늘 날짜 계산
    public String dateCheck() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);
        String returnName;
        switch (dayOfWeek) {
            case 2:
                returnName = ScheduleEnum.MONDAY.name();
                break;
            case 3:
                returnName = ScheduleEnum.TUESDAY.name();
                break;
            case 4:
                returnName = ScheduleEnum.WEDNESDAY.name();
                break;
            case 5:
                returnName = ScheduleEnum.THURSDAY.name();
                break;
            case 6:
                returnName = ScheduleEnum.FRIDAY.name();
                break;
            default:
                returnName = "쉬는 날";
                break;
        }
        return returnName;
    }
}
