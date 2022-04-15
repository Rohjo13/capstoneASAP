package co.mjc.capstoneasap.dto;

public class Schedule {

    // 수업 이름
    private String lecName;

    // 무슨 요일인지? Day of the week?
    private ScheduleEnum dayOTW;

    // 수업 시간(몇시간인지? 3시간?)
    private int lecTime;

    // 몇번째인지?(몇번째 수업인지? 쳣수업?)
    private int lecOrder;

    public String getLecName() {
        return lecName;
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

    public int getLecTime() {
        return lecTime;
    }

    public void setLecTime(int lecTime) {
        this.lecTime = lecTime;
    }

    public int getLecOrder() {
        return lecOrder;
    }

    public void setLecOrder(int lecOrder) {
        this.lecOrder = lecOrder;
    }
}
