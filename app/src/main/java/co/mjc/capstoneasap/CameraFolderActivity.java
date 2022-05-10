package co.mjc.capstoneasap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import co.mjc.capstoneasap.adapter.ViewpagerAdapter;
import co.mjc.capstoneasap.dto.Member;


// 이 액티비티는 ReadOnly
// 수정같은거 없음
public class CameraFolderActivity extends AppCompatActivity {


    // 새 액티비티에서 가져온거 사용할려고
    Member loginMember;
    // 멤버 파일 경로
    ArrayList<String> filePaths;
    ArrayList<Bitmap> viewGallery;

    // 원래 액티비티로 돌아가는 텍스트뷰 클릭
    TextView returnTextView;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("OK","CameraActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_folder);
        viewGallery = new ArrayList<>();
        returnTextView = findViewById(R.id.returnLsMain);
        // 직렬화로 데이터 긁어옴
        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccessData");
        // 파일 저장 String 은 loginMember 에 있어서 얻어온다.
        filePaths = loginMember.getFilePaths();

        // 카메라 이미지 세팅 경로만 얻어왔으므로, 비트맵으로 전환 해줘야함
        settingCameraImage();

        // 뷰페이저 '=. 리스트 뷰와 비슷하나 좌우로 스크롤 하여 볼 수 있다.
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(viewGallery);		// 어댑터 생성. 아이템 리스트를 파라미터로 넣어준다.
        viewPager.setAdapter(adapter);	// 뷰페이저에 어댑터 등록
        // 찍었던 사진들 끄집어냄

        // 다시 lsMain 으로 return
        returnTextView.setOnClickListener(view -> {
            Intent returnIntent = new Intent(getApplicationContext(), LsMainActivity.class);
            returnIntent.putExtra("loginAccess", loginMember);
            startActivity(returnIntent);
        });
    }

    // bitmap 으로 변환하는 메서드
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void settingCameraImage() {
        // 사진 찍은 개수 for
        for (int i = 0; i < filePaths.size(); i++) {
            // 파일로 변환하여
            File file = new File(filePaths.get(i));
            Bitmap bitmap;
            // 이미지 해독
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
            try {
                // 해독된 비트맵이 null 값이 아니면 viewGallery 에 추가함 ViewPager 로 볼 수 있다.
                bitmap = ImageDecoder.decodeBitmap(source);
                if (bitmap != null) {
                    viewGallery.add(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}