package co.mjc.capstoneasap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.mjc.capstoneasap.adapter.ChildHolder;
import co.mjc.capstoneasap.adapter.ExpandableChildAdapter;
import co.mjc.capstoneasap.adapter.ScheduleExpandableAdapter;
import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.Schedule;
import co.mjc.capstoneasap.myexeption.HandlerThread;
import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryScheduleRepository;
import co.mjc.capstoneasap.repository.ScheduleRepository;
import co.mjc.capstoneasap.service.CameraService;
import co.mjc.capstoneasap.service.MemberService;
import co.mjc.capstoneasap.service.ScheduleService;


public class LsMainActivity extends AppCompatActivity {

    // 카메라 기능에 사용할 것
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;


    // 로그 찍어 볼려고
    static final String LogTAG = "ASAP 실행 결과";

    // 오늘 날짜
    TextView dayOfWeek;
    // 로그인 한 사람 아이디
    TextView loginData;
    // 실시간 시간
    TextView clockTextView;
    private static Handler mHanlder;


    // 이건 schedule 에 있는거고
    ArrayList<Integer> funcImageViewList;

    // member 에 있는거고
    List<Schedule> scheduleList;
    ArrayList<String> filePaths;


    // 익스팬더블 리스트뷰 && 어댑터
    ExpandableListView expandableListView;
    ScheduleExpandableAdapter scheduleAdapter;
    ChildHolder childHolder;
    View goToAdapterView;
    // 로그인 한 회원을 알아야 한다.
    ScheduleRepository scheduleRepository;
    ScheduleService scheduleService;
    MemberRepository memberRepository;
    MemberService memberService;
    Member loginMember;
    HandlerThread handlerThread;
    CameraService cameraService;

    // 생성자 주입
    public LsMainActivity() {
        Log.d(LogTAG, "LsMainAc Constructor");
        handlerThread = new HandlerThread(this);

        scheduleRepository = new MemoryScheduleRepository();
        scheduleService = new ScheduleService(scheduleRepository);
        cameraService = new CameraService();
        memberRepository = MainActivity.memberRepository;
        memberService = MainActivity.memberService;
    }


    // 중요한 것 객체를 찾아오기 전 부터 자꾸 객체에 접근하지 말자. 오류 많이 난다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LogTAG, "lsMain onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls_main);

        // 로그인 성공시 loginAccess 로 멤버 객체 받아옴
        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccess");
        // 오늘 날짜
        loginData = findViewById(R.id.loginData);
        dayOfWeek = findViewById(R.id.dayofweek);
        dayOfWeek.setText(scheduleService.dateCheck());
        // 회원님 아이디 (hashMap에 default 로 들어가 있는 값이 id : 123 pwd : 123)
        loginData.setText(loginMember.getMemId() + "님");
        // Member 안에 있는 Schedule 을 끄집어 낼려고 CameraData 도 끄집어낼거임
        // 참고로 MemberRepository 에 내장해서 저장해놨음
        scheduleList = loginMember.getScheduleArrayList();
        filePaths = loginMember.getFilePaths();
        Log.d(LogTAG, "onCreate() by LsMain size? " + scheduleList.size());

        // 업데이트
        memberShowDefaultData();

        // 5.18 실험실 on
        goToAdapterView = null;
        childHolder = null;
        if (childHolder == null) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            goToAdapterView = inflater.inflate(R.layout.schedule_list_child_item_group, null, false);
            childHolder = new ChildHolder();
            // View 에 Tag 를 달아 식별한다.
            goToAdapterView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) goToAdapterView.getTag();
        }
        childHolder.horizontalListView = (RecyclerView) goToAdapterView.findViewById(R.id.child_group);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        childHolder.horizontalListView.setLayoutManager(linearLayoutManager);

        ExpandableChildAdapter adapter = new ExpandableChildAdapter(getApplicationContext(), funcImageViewList);
        childHolder.horizontalListView.setAdapter(adapter);
        // 실험실 off

        //5.19 실시간
        clockTextView = findViewById(R.id.clock);
        mHanlder = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strTime = sdf.format(cal.getTime());
                clockTextView = findViewById(R.id.clock);
                clockTextView.setText(strTime);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHanlder.sendEmptyMessage(0);
                }
            }
        }
        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
        //5.19 실시간

        // 익스팬더블 리스트 뷰
        expandableListView = findViewById(R.id.lsScheduleListExpandable);
//        expandableListView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//        expandableListView.layout
        // 어뎁터 인스턴스 생성 여기다가 넘겨준게 loginMember 거라 요일별로 저장하려면 다른 것을 넣거나 해야 됌
        scheduleAdapter = new ScheduleExpandableAdapter(getApplicationContext(),
                childHolder,loginMember.getScheduleArrayList()
                , funcImageViewList,goToAdapterView);
        // 어뎁터 설정
        expandableListView.setAdapter(scheduleAdapter);

        // 업데이트
        scheduleAdapter.notifyDataSetChanged();

        // 스케쥴을 누른다면
        expandableListView.setOnGroupClickListener((expandableListView, view, gPos, l) -> {
            System.out.println("onGroupClick");
            Toast.makeText(getApplicationContext(), scheduleList.get(gPos).getLecName() +
                    "를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            return false;
        });
        // 좀 수정, Recycler 는 Click 을 Main 으로 빼기 힘들어서 adapter 를 밖으로 뺏음
        adapter.setOnItemClick((v, pos) -> {
            switch (pos) {
                case 0: // 0은 카메라
                    takeAPicture();
                    break;
                case 1: // 카메라 폴더로 전환
                    memberCameraFolder();
                    break;
                case 2: // pdf 폴더로 전환
                    startActivity(new Intent(getApplicationContext(),
                            PdfFolderActivity.class).putExtra("loginAccess",loginMember));
                    break;
                case 3:
                    startActivity(new Intent(getApplicationContext(),
                            NoteFolderActivity.class).putExtra("loginAccess",loginMember));
                    break;
            }
        });
    }

    // 사진 찍는 메서드 ------------------------------------------------------------------------------

    // 3번 호출 그 결과(파일 경로 String 여기서 이미지 형식으로 저장하는 것이 아님)를
    // filePaths 리스트에 넣어서 CameraActivity 에 뿌려줄려고
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    // 요청코드 결과 값이 OK 면 Paths에 경로 추가
                    if (resultCode == RESULT_OK) {
                        // 파일 경로명 추가
                        filePaths.add(mCurrentPhotoPath);
                    }
                    break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    // 구현해야 할게 강의별 사진을 따로 저장하는거랑 뷰페이저로 바꿔서 날짜별로 강의가 뜨게 해야 하는 것
    // takeAPicture 에서 호출 2번
    private File createImageFile() throws IOException {
        // 파일 저장 형식
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // 외부 저장소 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // 임시 파일을 만드는데, 형식을 특정 이름으로 저장하고, jpg 파일 형식으로, 내부 저장소 경로로 파일 생성하고,
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // 이렇게 만들어진 저장경로를 String 에 저장함
        mCurrentPhotoPath = image.getAbsolutePath();
//        JPEG_20220508_175021_3479621252757571591.jpg
        Log.d(LogTAG, "현재 경로는 다음과 같습니다 : "+mCurrentPhotoPath);
        return image;
    }

    // 1번 호출
    private void takeAPicture() {
        // 버전이 낮을 경우에 권한 요청해야 됌
//        권한 요청 했다면
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(LogTAG, "권한 설정 완료 됌");
            }
//                아직 권한 요청 안하면
            else {
                Log.d(LogTAG, "카메라 권한 요청");
                ActivityCompat.requestPermissions(LsMainActivity.this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        // 카메라 앱으로 넘어가는 인텐트
        Intent takePictureIntent =
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        if (photoFile != null) {
            // 사진 파일 경로명 지정
            Uri photoURI = FileProvider.
                    getUriForFile(this,
                            "co.mjc.capstoneasap.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
//        }
    }

    // 직렬화로 데이터 넘겨서 사진 보여줄려고
    public void memberCameraFolder() {
        // 사진이 들어있는 액티비티로 전환
        Intent intent = new Intent(this,
                CameraFolderActivity.class);
        // 로그인 정보 넘겨주기
        intent.putExtra("loginAccessData", loginMember);
        // 인텐트에서 생성한 LsMainActivity 로 전환한다.
        startActivity(intent);
    }
    // 여기까지가 카메라 기능 ------------------------------------------------------------------------------
    
    // 멤버가 가지고 있는 Data 를 화면에 뿌림 (현재 안됨 5.5)
    public void memberShowDefaultData() {
        Log.d(LogTAG, "memberShowDefaultData()");
        scheduleList = loginMember.getScheduleArrayList();
        if (scheduleList == loginMember.getScheduleArrayList()) {
            Log.d(LogTAG, "Member Same this ScheduleArrayList");
        }
        if (scheduleList == null) {
            Log.d(LogTAG, "Member don't have ArrayList");
            scheduleList = new ArrayList<>();
        }
        if (funcImageViewList == null) {
            Log.d(LogTAG, "funcImageViewList is null");
            funcImageViewList = new ArrayList<>();
            funcImageViewList.add(R.drawable.cameraicon);
            funcImageViewList.add(R.drawable.foldericon);
            funcImageViewList.add(R.drawable.pdficon);
            funcImageViewList.add(R.drawable.notesicon);
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
                scheduleService.setCreateSchedule(this, loginMember, scheduleAdapter);
                break;
            case R.id.editSchedule:
                break;
            case R.id.deleteSchedule:
                scheduleService.deleteCreateSchedule(this);
                break;
            case R.id.logout:
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
        intent.putExtra("data", loginMember);
        // 다시 로그인 화면으로 변환
        startActivity(intent);
    }
}