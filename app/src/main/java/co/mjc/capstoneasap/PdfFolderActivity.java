package co.mjc.capstoneasap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.mjc.capstoneasap.adapter.PdfAdapter;
import co.mjc.capstoneasap.adapter.OnPdfSelectListener;
import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.PdfData;

public class PdfFolderActivity extends AppCompatActivity
        implements OnPdfSelectListener {

    // pdf 기능에 사용할 거
    private List<PdfData> pdfList;
    private PdfAdapter adapter;
    private RecyclerView recyclerView;
    static final int REQUEST_TAKE_FILE = 2;
    // 아
//    TextView return_lsMain;
    ImageView return_lsMain;

    Intent intent;
    Member loginMember;

    // 파일 외부 저장소 /downloads
    File externalFilesDir;

    // 폴더 액세스
    File mCurrentPdfPath;

    ImageView importPdf;

    // findPdf, pdfActivate 수정해야 됌
    // Uri를 저장할 것임
    Uri pdfUri;
    String pdfName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_folder);
        externalFilesDir = getFilesDir().getAbsoluteFile();
        return_lsMain = findViewById(R.id.pdf_return_lsmain);

        importPdf = findViewById(R.id.importPdf);

        // 직렬화 데이터 받아옴
        intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccess");
        // member 안에 있는 pdfList
        pdfList = loginMember.getPdfDataArrayList();

        pdfActivate();
        // 돌아가기 누르면 다시 원래 화면으로 전환
        return_lsMain.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                LsMainActivity.class).putExtra("loginAccess", loginMember)));

        // pdf 임포트한다.
        importPdf.setOnClickListener(view -> {
            // 외부 저장소 경로
            String path = Environment.getExternalStorageDirectory().getPath()
                    + "/Download/";
            // 앱에서 참조할 수 있도록 ACTION_GET_CONTENT 액션 사용
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            // 리소스를 접근할 수 있도록 경로 조정
            Uri uri = Uri.parse(path);
            // uri 가 접근 할 수 있는 타입 지정 현재는 pdf 타입
            intent.setDataAndType(uri, "application/pdf");
            // 액티비티를 실행하고 결과 값을 받는다.
            startActivityForResult(Intent.createChooser(intent, "Open"), REQUEST_TAKE_FILE);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            // 요청한 값이 REQUEST_TAKE_FILE 이면
            case REQUEST_TAKE_FILE:
                // 거기에 결과 값이 OK면
                if(resultCode == RESULT_OK) {
                    PdfData pdfData = new PdfData();
                    // Intent 에서 Uri Data 를 받아오고,
                    pdfUri = data.getData();
                    // 파일 이름을 알기 위해 DB를 Cursor 로 찾는다.
                    // Cursor -> 데이터베이스에 저장되어있는 테이블의 행을 참조하여 결과 값 가져옴
                    Cursor returnCursor =
                            getContentResolver().query(pdfUri, null,
                                    null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    // Cursor 를 첫번째 행으로 옮긴다.
                    returnCursor.moveToFirst();
                    // uri 에서 파일 이름을 가져옴
                    pdfName = returnCursor.getString(nameIndex);

                    // pdfData 객체에 set
                    pdfData.setPdfUri(pdfUri.toString());
                    pdfData.setPdfName(pdfName);
                    // List 에 추가
                    pdfList.add(pdfData);
                    // 추가 된 것을 알려야 함
                    adapter.notifyDataSetChanged();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    // 리사이클러(리스트) 뷰를 실행해서 외부 저장소에서 pdf파일 찾아와서 저장하는 방식
    public void pdfActivate() {
        // 다 수정해야 됌
        recyclerView = findViewById(R.id.pdfRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this, 2));
        // 파일 경로
        // 어답터 인스턴스 생성
        adapter = new PdfAdapter(this, pdfList,
                this);
        // 어답터 set
        recyclerView.setAdapter(adapter);
    }

    // pdf 선택되면 pdfViewer 를 통해서 보여준다. github openSource
    @Override
    public void onPdfSelected(Uri uri) {
        // PDFViewer 로 인텐트 전환
        startActivity(new Intent(getApplicationContext(),
                PdfActivity.class).putExtra("uri", uri));
    }




    // 다운로드 파일에서 pdf 걸러서 찾아준다.
    // 현재 절대경로는 찾았지만 파일을 찾을 수 없다.
    // 안씀 5.17
    @Deprecated
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