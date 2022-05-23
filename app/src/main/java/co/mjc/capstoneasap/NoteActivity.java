package co.mjc.capstoneasap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.service.DrawingService;

public class NoteActivity extends AppCompatActivity {

    ImageView backRecord;
    ImageView forwardRecord;
    ImageView note_return_notefolder;

    ImageView redPencil;
    ImageView blackPencil;
    ImageView eraser;
    ImageView textFunc;

    TextView note_name_view_noteActivity;

    Intent intent;

    String noteName;

    Member loginMember;

    private DrawingService service;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // 빨간 펜
        redPencil = findViewById(R.id.redPencil);
        redPencil.setColorFilter(Color.parseColor("#ff0000"));
        // 검정 펜
        blackPencil = findViewById((R.id.blackPencil));
        // 지우개
        eraser = findViewById(R.id.eraser);
        // 텍스트
        textFunc = findViewById(R.id.textFunc);

        note_name_view_noteActivity = findViewById(R.id.note_name_view_noteActivity);

        intent = getIntent();
        noteName = intent.getStringExtra("noteName");
        loginMember = (Member) intent.getSerializableExtra("loginAccess");

        // 노트 이름 받아서 보여준다.
        note_name_view_noteActivity.setText(noteName);

        // 한 draw 뒤로
        backRecord = findViewById(R.id.backRecord);
        // 한 draw 앞으로
        forwardRecord = findViewById(R.id.forwardRecord);
        // 돌아가기
        note_return_notefolder = findViewById(R.id.note_return_notefolder);

        note_return_notefolder.setOnClickListener(view -> {
            // put extra 넣어서 그림 그린거 전달해야함.
            startActivity(new Intent(getApplicationContext(), NoteFolderActivity.class));
        });

        service = new DrawingService(getApplicationContext());
        // 뷰 추가
        ((LinearLayout) findViewById(R.id.drawingLayout)).addView(service);

        // 터치리스너 넣은게 누르면 좀 바뀐 모션을 줘야할 것 같음

        // 페인트 색상을 빨강으로 변경
        redPencil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                service.getmPaint().setColor(Color.RED);
                // 굵기
                service.getmPaint().setStrokeWidth(5);
                return false;
            }
        });

        // 페인트 색상을 검정으로 변경
        blackPencil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                service.getmPaint().setColor(Color.BLACK);
                // 굵기
                service.getmPaint().setStrokeWidth(5);
                return false;
            }
        });

        // 지우개
        eraser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                service.getmPaint().setColor(Color.WHITE);
                // 굵기
                service.getmPaint().setStrokeWidth(25);
                return false;
            }
        });

        // 텍스트 적을 수 있게 해준다.
        // 아직 구현 안했음
        textFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(getApplicationContext());
            }
        });

    }
}