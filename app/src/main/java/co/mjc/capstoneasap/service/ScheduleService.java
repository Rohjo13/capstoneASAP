package co.mjc.capstoneasap.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Optional;

import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;
import co.mjc.capstoneasap.repository.ScheduleRepository;

public class ScheduleService {
    ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void scheduleSave(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Schedule> getBySchedule(String lecName) {
        Optional<Schedule> scheduleObject = scheduleRepository.getScheduleId(lecName);
        // 이렇게 보내면? 다시 체크해야지 않을까
        if(scheduleObject.isPresent()) return scheduleObject;
        return Optional.empty();
    }

    public String Today() {
        int dayOfWeek = Calendar.DAY_OF_WEEK;
        switch (dayOfWeek) {
            case 1:
                return ScheduleEnum.MON.name();
            case 2:
                return ScheduleEnum.TUE.name();
            case 3:
                return ScheduleEnum.WED.name();
            case 4:
                return ScheduleEnum.THU.name();
            case 5:
                return ScheduleEnum.FRI.name();
            default:
                return "주말";
        }
    }
}
