package co.mjc.capstoneasap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.Schedule;

public class ScheduleExpandableAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<Schedule> scheduleArrayList;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Integer> imageViews;
    View goTohereView;
    ChildHolder childHolder;

    public ScheduleExpandableAdapter(Context mContext, ChildHolder childHolder,
                                     ArrayList<Schedule> scheduleArrayList, ArrayList<Integer> imageViews
            , View goTohereView) {
        this.mContext = mContext;
        this.scheduleArrayList = scheduleArrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.imageViews = imageViews;
        this.childHolder = childHolder;
        this.goTohereView = goTohereView;
    }

    @Override
    public int getGroupCount() {
        return scheduleArrayList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        System.out.println("getChildrenCount");
        // 제발 1 바꾸지마 -> 숫자 올리면 중복해서 여러 개 생김
//        return imageViews.size();
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
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View notView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.schedule_list_parent, null);
        TextView textView = view.findViewById(R.id.parentLectureName);
        TextView scheduleDate = view.findViewById(R.id.scheduleDate);
        scheduleDate.setText(scheduleArrayList.get(i).getDayOTW().name());
        textView.setText(scheduleArrayList.get(i).getLecName());
        return view;
    }

    @Override
    public View getChildView(int gPos, int cPos, boolean b, View notView, ViewGroup viewGroup) {
        return goTohereView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
