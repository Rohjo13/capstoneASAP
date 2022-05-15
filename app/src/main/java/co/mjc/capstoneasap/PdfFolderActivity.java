package co.mjc.capstoneasap;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.mjc.capstoneasap.adapter.MainAdapter;
import co.mjc.capstoneasap.adapter.OnPdfSelectListener;
import co.mjc.capstoneasap.dto.Member;

public class PdfFolderActivity extends AppCompatActivity
        implements OnPdfSelectListener {

    // pdf 기능에 사용할 거
    private List<File> pdfList;
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    // 아
    TextView return_lsMain;

    Intent intent;
    Member loginMember;

    // 파일 외부 저장소 /downloads
    File externalFilesDir;

    // 폴더 액세스
    File mCurrentPdfPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_folder);
        externalFilesDir = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsoluteFile();
        return_lsMain = findViewById(R.id.pdf_return_lsmain);

        // 직렬화 데이터 받아옴
        intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccess");


        pdfActivate();
        // 돌아가기 누르면 다시 원래 화면으로 전환
        return_lsMain.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                LsMainActivity.class).putExtra("loginAccess", loginMember)));


    }

    // 리사이클러(리스트) 뷰를 실행해서 외부 저장소에서 pdf파일 찾아와서 저장하는 방식
    public void pdfActivate() {
        recyclerView = findViewById(R.id.pdfRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this, 3));
        pdfList = new ArrayList<>();
        // 파일 경로
        Log.e("FILE", externalFilesDir.toString());
        // .pdf 만을 걸러서 추가
        pdfList.addAll(findPdf(externalFilesDir));
        // 어답터 인스턴스 생성
        adapter = new MainAdapter(this, pdfList,
                this);
        // 어답터 set
        recyclerView.setAdapter(adapter);
    }

    // pdf 선택되면 pdfViewer 를 통해서 보여준다. github openSource
    @Override
    public void onPdfSelected(File file) {
        // PDFViewer 로 인텐트 전환
        startActivity(new Intent(getApplicationContext(),
                PdfActivity.class).putExtra("path", file.getAbsoluteFile()));
    }

    // 다운로드 파일에서 pdf 걸러서 찾아준다.
    // 현재 절대경로는 찾았지만 파일을 찾을 수 없다.
    public ArrayList<File> findPdf(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        // 절대 경로
        String PATH = file.getAbsolutePath()+"/";
        // 경로 체크
        Log.e("PdfFolderActivity Info",PATH);
        // 파일 생성
        File orienFile = new File(PATH);
        // 해당 파일 현재는 파일이 없으므로, 아무것도 표시되지 않는다.
        File[] files = orienFile.listFiles();
        Log.e("Info", String.valueOf(orienFile.list().length));

        // 파일의 이름에서 얻어온 이름에 마지막이 .pdf 일 경우 List에 추가
        for (File singleFile : files) {
            if (singleFile.getName().endsWith(".pdf")) {
                Log.e("isDirectory", "Exist");
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

}