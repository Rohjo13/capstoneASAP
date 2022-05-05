package co.mjc.capstoneasap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.mjc.capstoneasap.adapter.ScheduleExpandableAdapter;
import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.dto.ScheduleEnum;
import co.mjc.capstoneasap.myexeption.HandlerThread;
import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryMemberRepository;
import co.mjc.capstoneasap.repository.MemoryScheduleRepository;
import co.mjc.capstoneasap.repository.ScheduleRepository;
import co.mjc.capstoneasap.service.MemberService;
import co.mjc.capstoneasap.service.ScheduleService;


// 로그아웃하고 로그인하는데 객체가 왜 또 생기지? static 으로 메모리에 올려놨는데도 불구하고
public class LsMainActivity extends AppCompatActivity {

    // 카메라 기능에 사용할 것
    static final int REQUEST_IMAGE_CAPTURE = 1;

    TextView dayOfWeek;
    TextView selectDate;
    TextView loginData;

    ImageView camera;
    ImageView folder;


    // 사진 저장하는 ImageViewList 임
    // 그냥 static 으로 CameraFolderAc 에 던져버린다.
    static ArrayList<ImageView> cameraImage;

    // 임시 카메라 이미지 위의 리스트에 들어갈 객체
    ImageView getCameraImage;

    // 이건 schedule 에 있는거고
    ArrayList<ImageView> imageViewList;

    // member 에 있는거고
    List<Schedule> scheduleList;

    ExpandableListView expandableListView;
    ScheduleExpandableAdapter scheduleAdapter;

    // 로그인 한 회원을 알아야 한다.
    ScheduleRepository scheduleRepository;
    ScheduleService scheduleService;
    MemberRepository memberRepository;
    MemberService memberService;
    Member loginMember;
    HandlerThread handlerThread;

    // 생성자 주입
    public LsMainActivity() {
        System.out.println("LsMainAc Constructor");
        handlerThread = new HandlerThread(this);

        scheduleRepository = new MemoryScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);
        memberRepository = MainActivity.memberRepository;
        memberService = MainActivity.memberService;
        // 이미지 뷰는 만든다.
    }


    // 중요한 것 객체를 찾아오기 전 부터 자꾸 객체에 접근하지 말자. 오류 많이 난다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("lsMain onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls_main);
        System.out.println("onCreate()");
        camera = findViewById(R.id.cameraFunc);
        folder = findViewById(R.id.folderFunc);
        // 로그인 성공시 loginAccess 로 멤버 객체 받아옴
        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccess");
        // 오늘 날짜
        loginData = findViewById(R.id.loginData);
        dayOfWeek = findViewById(R.id.dayofweek);
        dayOfWeek.setText(scheduleService.dateCheck());
        // 회원님 아이디 (hashMap에 default 로 들어가 있는 값이 id : 123 pwd : 123)
        loginData.setText("반갑습니다. : " + loginMember.getMemId() + "님");
        // Member 안에 있는 Schedule 을 끄집어 낼려고 CameraData 도 끄집어낼거임
        // 참고로 MemberRepository 에 내장해서 저장해놨음
        cameraImage = loginMember.getCameraDataList();
        scheduleList = loginMember.getScheduleArrayList();

        // 임시
        getCameraImage = findViewById(R.id.activityCamera_Image);

        // 업데이트
        memberShowDefaultData();

        expandableListView = findViewById(R.id.lsScheduleListExpandable);
        scheduleAdapter = new ScheduleExpandableAdapter(getApplicationContext(), loginMember.getScheduleArrayList()
                , imageViewList);
        expandableListView.setAdapter(scheduleAdapter);

        // 업데이트
        scheduleAdapter.notifyDataSetChanged();

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int gPos, long l) {
                System.out.println("onGroupClick");
//                expandableListView.setBackgroundColor(Color.parseColor("#333333"));
                Toast.makeText(getApplicationContext(), scheduleList.get(gPos).getLecName() +
                        "를 선택하셨습니다.", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int gPos, int cPos, long l) {
                // 여기 전체적으로 다 문제있음
                System.out.println("onChildClick");

                int childId = imageViewList.get(0).getId();
                switch (childId) {
                    case R.id.cameraFunc:
                        System.out.println("카메라 기능 클릭");
                        takeAPicture();
                        break;
                    case R.id.folderFunc:
                        System.out.println("폴더 기능 클릭");
                        memberCameraFolder();
                        break;
                    default:
                }
                Toast.makeText(getApplicationContext(), "안녕 자손!", Toast.LENGTH_LONG).show();
                return true;
            }
        });
//        // 리스트 뷰
//        listView = findViewById(R.id.lsScheduleListView);
//        scheduleAdapter = new ScheduleAdapter(this, scheduleArrayList);
//        // 스케쥴 동적 리스트 뷰
//        listView.setAdapter(scheduleAdapter);
//        // 리스트 뷰를 눌렀을 때 popUp menu On -> 익스펜더블로 변경 예정
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            PopupMenu popup = new PopupMenu(getApplicationContext(), view);
//            popup.getMenuInflater().inflate(R.menu.schedule_function, popup.getMenu());
//            popup.setOnMenuItemClickListener(menuItem -> {
//                switch (menuItem.getItemId()) {
//                    case R.id.takeAPicture:
//                        takeAPicture();
//                        break;
//                    case R.id.photoView:
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            });
//            popup.show();
//        });
    }

    // 사진 찍는 메서드
    private void takeAPicture() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // 이거 못쓰니까 쓰레드로 받던지 합시다.
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // 수정 필요 부분
//        }
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    // 사진 찍은거 가져와야지
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            getCameraImage.setImageBitmap(imageBitmap);
            cameraImage.add(getCameraImage);
        }
    }

//    static final int REQUEST_TAKE_PHOTO = 1;
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                ...
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//
//    private File createImageFile() {
//    }


    public void memberCameraFolder() {
        // 사진이 들어있는 액티비티로 전환
        Intent intent = new Intent(this, CameraFolderActivity.class);

        // 로그인 정보 넘겨주기
        intent.putExtra("loginAccessData", loginMember);
        // 인텐트에서 생성한 LsMainActivity 로 전환한다.
        startActivity(intent);
    }

    // 멤버가 가지고 있는 Data 를 화면에 뿌림 (현재 안됨 5.5)
    public void memberShowDefaultData() {
        System.out.println("memberShowDefaultData()");
        scheduleList = loginMember.getScheduleArrayList();
        if (scheduleList == loginMember.getScheduleArrayList()) {
            System.out.println("Same this ScheduleArrayList");
        }
        if (scheduleList == null) {
            System.out.println("Member don't have ArrayList");
            scheduleList = new ArrayList<>();
        }
        if (imageViewList == null) {
            System.out.println("imageViewList is null");
            imageViewList = new ArrayList<>();
        }
        if (cameraImage == null) {
            System.out.println("cameraImage is null");
            cameraImage = new ArrayList<>();
        }
    }

    // 메뉴바 생성 : 추가, 수정, 삭제, 로그아웃
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 메뉴 추가, 수정, 삭제, 로그아웃이 선택되었을 때
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createSchedule:
//                setCreateSchedule();

                // Method Refactoring
                // 리팩터링은 됐으나 문제가 있음 무조건 리턴값 받아서 지알아서 생성하는게 문제임 5.4
                // 차라리 settingSchedule도 Service로 보내면 문제가 없지 않을까?
                scheduleService.setCreateSchedule(this, loginMember, scheduleAdapter);
                break;
            case R.id.editSchedule:
                break;
            case R.id.deleteSchedule:
//                deleteCreateSchedule();
                scheduleService.deleteCreateSchedule(this);
                break;
            case R.id.logout:
//                memberService.logout(getApplicationContext(),loginMember);
                logout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // 초기화면 로그아웃
    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        // 로그인한 멤버는 이제 없음
        loginMember = null;
        // 다시 로그인 화면으로 변환
        startActivity(intent);
    }
}