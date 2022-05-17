package co.mjc.capstoneasap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.PdfData;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {


    private Context context;
    private List<PdfData> pdfFiles;
    private OnPdfSelectListener listener;

    public MainAdapter(Context context, List<PdfData> pdfFiles, OnPdfSelectListener listener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.
                pdfrv_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtName.setText(pdfFiles.get(position).getPdfName());
        holder.txtName.setSelected(true);

        holder.cardView.setOnClickListener(view ->
                // Uri 병렬화라서 직렬화 가능한 String 으로 바꾼다음에 다시 Uri 로 변환했음
                listener.onPdfSelected(Uri.parse(pdfFiles.get(position).getPdfUri())));
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }

}
