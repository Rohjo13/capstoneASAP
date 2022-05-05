package co.mjc.capstoneasap.dto;


import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

// Member Information
// 직렬화 해서 객체 넘길 것이기 때문에 Serializable 구현
public class Member implements Serializable {

    private String memId;
    private String memPw;
    private String memMail;

    // 문제있음 객체 배열로 받던 ArrayList로 받던 바꿔야함
//    Schedule schedule;
    private ArrayList<Schedule> scheduleArrayList;

    private ArrayList<ImageView> cameraDataList;
    // 그냥 리스트 다 던져버리자
    public ArrayList<Schedule> getScheduleArrayList() {
        return scheduleArrayList;
    }

    public void setScheduleArrayList(ArrayList<Schedule> scheduleArrayList) {
        this.scheduleArrayList = scheduleArrayList;
    }

    public ArrayList<ImageView> getCameraDataList() {
        return cameraDataList;
    }

    public void setCameraDataList(ArrayList<ImageView> cameraDataList) {
        this.cameraDataList = cameraDataList;
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
