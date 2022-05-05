package co.mjc.capstoneasap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.Schedule;

public class ScheduleExpandableAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<Schedule> scheduleArrayList;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ImageView> imageViews;

    public ScheduleExpandableAdapter(Context mContext,
                                     ArrayList<Schedule> scheduleArrayList, ArrayList<ImageView> imageViews) {
        this.mContext = mContext;
        this.scheduleArrayList = scheduleArrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.imageViews = imageViews;
    }

    @Override
    public int getGroupCount() {
        return scheduleArrayList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        System.out.println("getChildrenCount");
        // 제발 1 바꾸지마 -> 숫자 올리면 중복해서 여러 개 생김
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        System.out.println("getGroup");
        return scheduleArrayList.get(i);
    }

    @Override
    public Object getChild(int gPosition, int cPosition) {
        System.out.println("getChild");
        return imageViews.get(cPosition);
    }

    @Override
    public long getGroupId(int gPosition) {
        return gPosition;
    }

    @Override
    public long getChildId(int gPosition, int cPosition) {
        return cPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View notView, ViewGroup viewGroup) {
        System.out.println("getGroupView");
        View view = mLayoutInflater.inflate(R.layout.schedulelistparent, null);
        TextView textView = view.findViewById(R.id.parentLectureName);
        textView.setText(scheduleArrayList.get(i).getLecName());
        return view;
    }

    @Override
    public View getChildView(int gPos, int cPos, boolean b, View notView, ViewGroup viewGroup) {
        System.out.println("getChildView");
        View view = mLayoutInflater.inflate(R.layout.schedulelistchild, null);
        ImageView camera = view.findViewById(R.id.cameraFunc);
        ImageView folder = view.findViewById(R.id.folderFunc);
        camera.setImageResource(R.drawable.cameraicon);
        folder.setImageResource(R.drawable.foldericon);
        imageViews.add(camera);
        imageViews.add(folder);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
