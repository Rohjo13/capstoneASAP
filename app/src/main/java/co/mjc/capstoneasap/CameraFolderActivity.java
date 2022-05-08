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

    // 원래 액티비티로 돌아가는 텍스트뷰
    TextView returnTextView;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("CameraActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_folder);
        viewGallery = new ArrayList<>();
        returnTextView = findViewById(R.id.returnLsMain);
        // 직렬화로 데이터 긁어옴
        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccessData");
        filePaths = loginMember.getFilePaths();

        settingCameraImage();

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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void settingCameraImage() {
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            Bitmap bitmap;
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
                if (bitmap != null) {
//                    bitmap.setHeight();
                    viewGallery.add(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}