package co.mjc.capstoneasap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;

import co.mjc.capstoneasap.dto.Member;


// 이 액티비티는 ReadOnly
// 수정같은거 없음
public class CameraFolderActivity extends AppCompatActivity {


    // 새 액티비티에서 가져온거 사용할려고
    Member loginMember;
    ArrayList<ImageView> loginMemberImageData;
    ImageView haveImage;

    // 원래 액티비티로 돌아가는 텍스트뷰
    TextView returnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("CameraActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_folder);

        haveImage = findViewById(R.id.activityCamera_Image);
        returnTextView = findViewById(R.id.returnTextView);


        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccessData");
        loginMemberImageData = loginMember.getCameraDataList();

//        settingCameraImage();

        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), LsMainActivity.class);
                returnIntent.putExtra("loginAccess", loginMember);
                startActivity(returnIntent);
            }
        });
    }

    public void settingCameraImage() {
        // 일단 하나
        for (int i = 0; i < loginMemberImageData.size(); i++) {
//        haveImage.setImageResource(loginMemberImageData.get(i).getId());
        }
    }
}