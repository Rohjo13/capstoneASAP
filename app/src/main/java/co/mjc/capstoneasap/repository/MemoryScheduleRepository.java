package co.mjc.capstoneasap.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;

public class MemoryScheduleRepository implements ScheduleRepository{

    // 저장을 뭘로 할건지?
    // 어차피 해쉬맵 하나여도 상관없지 않나?
    // -> 요일별로 저장할 것이고,
    Map<String, Schedule> scheduleMap;

    public MemoryScheduleRepository() {
        scheduleMap = new HashMap<>();
    }

    @Override
    public void save(Schedule schedule) {
        // 저장하는데 String 이름값
        scheduleMap.put(schedule.getLecName(),schedule);
    }


    // 이름으로 꺼내옴
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Schedule> getScheduleId(String lecName) {
        return Optional.ofNullable(scheduleMap.get(lecName));
    }

    // 기능 구현은 9week~
    @Override
    public void modify(Schedule schedule) {

    }

    // 기능 구현은 9week~
    @Override
    public void delete(Schedule schedule) {

    }

}
