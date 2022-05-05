package co.mjc.capstoneasap.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Optional;

import co.mjc.capstoneasap.LsMainActivity;
import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.adapter.ScheduleExpandableAdapter;
import co.mjc.capstoneasap.dto.Member;
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
    // date 전체 변경 예정 5.5
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

    public void setCreateSchedule(Context context, Member loginMember, ScheduleExpandableAdapter adapter) {
        System.out.println("scheduleService.setCreateSchedule()");
        TextView selectDate;
        // schedule 을 만드는 Dialog 생성
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.createschedule);
        dialog.setTitle("시간표 추가");
        // Button 객체 생성
        Button addSchedule = dialog.findViewById(R.id.addSchedule);
        Button cancelSchedule = dialog.findViewById(R.id.cancelAddSchedule);
        // 강의 이름 설정
        EditText createNameSchedule = dialog.findViewById(R.id.createNameSchedule);
        selectDate = dialog.findViewById(R.id.selectDate);
        // schedule 객체 생성
        Schedule schedule = new Schedule();
        // 요일을 설정하는 팝업 메뉴
        selectDate.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, view);
            popup.getMenuInflater().inflate(R.menu.datepopup_schedule, popup.getMenu());
            // 요일을 선택하면 그에 맞게 Date 는 select 됨.
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.dateMon:
                        schedule.setDayOTW(ScheduleEnum.MONDAY);
                        selectDate.setText("월요일");
                        break;
                    case R.id.dateTUE:
                        schedule.setDayOTW(ScheduleEnum.TUESDAY);
                        selectDate.setText("화요일");
                        break;
                    case R.id.dateWED:
                        schedule.setDayOTW(ScheduleEnum.WEDNESDAY);
                        selectDate.setText("수요일");
                        break;
                    case R.id.dateTHU:
                        schedule.setDayOTW(ScheduleEnum.THURSDAY);
                        selectDate.setText("목요일");
                        break;
                    case R.id.dateFRI:
                        schedule.setDayOTW(ScheduleEnum.FRIDAY);
                        selectDate.setText("금요일");
                        break;
                    case R.id.dateSAT:
                        schedule.setDayOTW(ScheduleEnum.SATURDAY);
                        selectDate.setText("토요일");
                        break;
                    case R.id.dateSUN:
                        schedule.setDayOTW(ScheduleEnum.SUNDAY);
                        selectDate.setText("일요일");
                        break;
                    default:
                        break;
                }
                return true;
            });
            // show
            popup.show();
        });
        // 확인 버튼인데, 데이터가 다 입력되어야만 추가 완료 if 문으로 검사
        addSchedule.setOnClickListener(view1 -> {
            // 강의 이름 설정 : ex) 자바캡스톤디자인
            schedule.setLecName(createNameSchedule.getText().toString());

            // loginMember 에 변수 값 지정한 schedule 을 add
//            이 부분은 수정이 필요함 굳이 이렇게 추가 해야하는가? 어차피 list 를 받아와서 쓰는데
//            loginMember.getScheduleArrayList().add(schedule);
            // 해당 강의가 요일이 선택되지 않으면서, 이름이 없을 때
            if (createNameSchedule.getText().toString().equals("") || schedule.getDayOTW() == null) {
                // 간이 메세지 전달
                Toast.makeText(context,
                        "전부 입력해주세요.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context,
                        "강의가 추가 완료되었습니다.", Toast.LENGTH_LONG).show();
                loginMember.getScheduleArrayList().add(schedule);
                adapter.notifyDataSetChanged();
                // dismiss
                dialog.dismiss();
            }
        });
        // 취소 버튼
        cancelSchedule.setOnClickListener(view2 -> dialog.dismiss());
        dialog.show();

        // 여기도 수정 필요함 5.4
    }

    public void deleteCreateSchedule(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("진짜");
        alertDialogBuilder.setMessage("삭제 하시겠습니까?");
        alertDialogBuilder.setPositiveButton("yes", (dialogInterface, i) -> {
            Toast.makeText(context,
                    "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show();
        });
        alertDialogBuilder.setNegativeButton("no", (dialogInterface, i) -> {
            Toast.makeText(context,
                    "삭제가 취소되었습니다.", Toast.LENGTH_LONG).show();
//            finish(); // 액티비티 종료 우리는 액티비티가 하나이므로, 앱 자체가 종료됨 액티비티가 많으면
            // 하나의 액티비티만 종료한다.
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
