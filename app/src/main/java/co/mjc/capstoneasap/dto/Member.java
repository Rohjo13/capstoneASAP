package co.mjc.capstoneasap.dto;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

// Member Information
// 직렬화 해서 객체 넘길 것이기 때문에 Serializable 구현
public class Member implements Serializable {

    private String memId;
    private String memPw;
    private String memMail;

    // 문제있음 객체 배열로 받던 ArrayList로 받던 바꿔야함
    Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    // -----------------------------수정해야 됌-----------------------------------
    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getMemPw() {
        return memPw;
    }

    public void setMemPw(String memPw) {
        this.memPw = memPw;
    }

    public String getMemMail() {
        return memMail;
    }

    public void setMemMail(String memMail) {
        this.memMail = memMail;
    }

}
