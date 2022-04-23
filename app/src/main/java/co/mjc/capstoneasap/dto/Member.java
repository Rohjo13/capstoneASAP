package co.mjc.capstoneasap.dto;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

// Member Information
public class Member implements Serializable {

    private String memId;
    private String memPw;
    private String memMail;

    Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

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
