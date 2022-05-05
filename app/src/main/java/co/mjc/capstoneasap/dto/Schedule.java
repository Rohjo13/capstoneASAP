package co.mjc.capstoneasap.dto;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import co.mjc.capstoneasap.R;

public class Schedule {

    // 수업 이름
    private String lecName;

    // 무슨 요일인지? Day of the week?
    private ScheduleEnum dayOTW;

    public String getLecName() {
        return this.lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public ScheduleEnum getDayOTW() {
        return dayOTW;
    }

    public void setDayOTW(ScheduleEnum dayOTW) {
        this.dayOTW = dayOTW;
    }

}
