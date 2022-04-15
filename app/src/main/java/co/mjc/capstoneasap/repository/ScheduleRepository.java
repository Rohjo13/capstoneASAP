package co.mjc.capstoneasap.repository;

import java.util.Optional;

import co.mjc.capstoneasap.dto.Schedule;

public interface ScheduleRepository {
    // Schedule 양식에 맞게 저장하는 기능
    void save(Schedule schedule);

    // 수업 수정 기능
    void modify(Schedule schedule);

    // 수업을 삭제하는 기능
    void delete(Schedule schedule);

    Optional<Schedule> getScheduleId(String lecName);
}
