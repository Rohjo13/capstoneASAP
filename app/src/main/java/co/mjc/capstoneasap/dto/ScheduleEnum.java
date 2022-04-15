package co.mjc.capstoneasap.dto;


// 자바 캘린더에는 요일이 월(1)~일(7)로 되어있음
public enum ScheduleEnum {
    // 그에 맞게끔 Enum 설정
    MON(1), TUE(2), WED(3),THU(4), FRI(5);

    private final int value;

    // Enum 에 필요한 값 생성자
    ScheduleEnum(int value) {
        this.value = value;
    }

    // 요일별 해당하는 값을 리턴
    public int intValue() {
        return value;
    }

}
