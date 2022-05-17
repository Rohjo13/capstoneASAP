package co.mjc.capstoneasap;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfActivity extends AppCompatActivity {
    Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        // github 오픈소스
        PDFView pdfView = findViewById(R.id.pdfView);
        // path 로 지정한 데이터를 얻어와서 filePath 에 뿌려준다.
//        filePath = getIntent().getStringExtra("path");
        filePath = getIntent().getParcelableExtra("uri");
        // Uri 는 인터넷 자원을 나타내는 주소입니다. file 을 Uri 로 변환한다.
        // Uri 로부터 로드하여 pdfView 에 뿌려준다.
        pdfView.fromUri(filePath).load();
    }
}