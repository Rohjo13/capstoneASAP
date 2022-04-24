package co.mjc.capstoneasap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.mjc.capstoneasap.R;
import co.mjc.capstoneasap.dto.Schedule;

public class ScheduleAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        mContext = context;
        this.schedules = schedules;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Schedule getItem(int i) {
        return schedules.get(i);
    }

    @Override
    public View getView(int i, View notView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.schedulelistview, null);
        TextView lectureName = view.findViewById(R.id.lectureName);

        lectureName.setText(schedules.get(i).getLecName());
        return view;
    }
}
