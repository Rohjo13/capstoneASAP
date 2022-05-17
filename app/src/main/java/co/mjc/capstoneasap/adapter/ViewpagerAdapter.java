package co.mjc.capstoneasap.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.mjc.capstoneasap.R;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewpagerAdapter.ViewHolder>{

    private List<Bitmap> Items;

    public ViewpagerAdapter(List<Bitmap> Items) {
        this.Items = Items;
    }

    @NonNull
    @Override
    public ViewpagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.galleries_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewpagerAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(Items.get(position));
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.galleriesImage);
        }
    }

}