package co.mjc.capstoneasap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.mjc.capstoneasap.adapter.NoteAdapter;
import co.mjc.capstoneasap.adapter.OnNoteSelectListener;
import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.dto.NoteData;

public class NoteFolderActivity extends AppCompatActivity implements OnNoteSelectListener {

    private List<NoteData> noteList;
    ImageView note_return_lsmain;
    ImageView createNote;
    ImageView trashNote;


    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    Intent intent;
    Member loginMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_folder);

        note_return_lsmain = findViewById(R.id.note_return_lsmain);
        createNote = findViewById(R.id.createNote);
        trashNote = findViewById(R.id.trashNote);

        intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginAccess");
        noteList = loginMember.getNoteDataArrayList();

        noteActivate();

        // 노트 생성
        createNote.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.create_notedata);
            dialog.setTitle("노트 생성");

            EditText createNote_name_input = dialog.findViewById(R.id.createNote_name_input);

            Button createNoteBtn = dialog.findViewById(R.id.createNote_btn);
            Button cancelNoteBtn = dialog.findViewById(R.id.cancelNote_btn);

            createNoteBtn.setOnClickListener(create -> {
                NoteData noteData = new NoteData();
                noteData.setNoteName(createNote_name_input.getText().toString());
                if (createNote_name_input.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "노트 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                noteList.add(noteData);
                Toast.makeText(getApplicationContext(), "생성되었습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            cancelNoteBtn.setOnClickListener(cancel -> {
                Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            adapter.notifyDataSetChanged();
            dialog.show();
        });

        // LsMain으로 리턴
        note_return_lsmain.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                LsMainActivity.class).putExtra("loginAccess", loginMember)));

    }

    public void noteActivate() {
        recyclerView = findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this, 2));
        // 어답터 인스턴스 생성
        adapter = new NoteAdapter(getApplicationContext(), noteList,
                this);
        // 어답터 set
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteSelected() {
        Toast.makeText(getApplicationContext(), "구현하지 않았음", Toast.LENGTH_SHORT).show();
    }
}