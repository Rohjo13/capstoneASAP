package co.mjc.capstoneasap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.NoteData;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private Context context;
    private List<NoteData> noteDataList;
    private OnNoteSelectListener listener;

    public NoteAdapter(Context context, List<NoteData> noteDataList, OnNoteSelectListener listener) {
        this.context = context;
        this.noteDataList = noteDataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.
                noterv_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.txtName.setText(noteDataList.get(position).getNoteName());
//        holder. 사진도 넣어야 됌
        holder.txtName.setSelected(true);
        holder.cardView.setOnClickListener(view -> listener.onNoteSelected(noteDataList.get(position).getNoteName()));
    }

    @Override
    public int getItemCount() {
        return noteDataList.size();
    }
}
