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

    // 스케쥴 저장 현재는 안쓴다.
    @Deprecated
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }


    // 스케쥴 get 현재는 안쓴다.
    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Schedule> getScheduleOne(String lecName) {
        Optional<Schedule> scheduleObject = scheduleRepository.getScheduleId(lecName);
        if(scheduleObject.isPresent()) return scheduleObject;
        return Optional.empty();
    }


    // 날짜 체크
    // 당장은 오늘이 무슨 요일인지만 체크하고, 이 부분에 추가 기능 넣어서 요일별로 시간표 뜨게 할 예정
    public String dateCheck() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);
        String returnName;
        switch (dayOfWeek) {
            case 1:
                returnName = ScheduleEnum.SUNDAY.name();
                break;
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
            case 7:
                returnName = ScheduleEnum.SATURDAY.name();
                break;
            default:
                returnName = "NULL";
                break;
        }
        return returnName;
    }
}
