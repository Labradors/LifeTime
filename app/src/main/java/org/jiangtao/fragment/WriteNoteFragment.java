package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteNoteFragment extends android.support.v4.app.Fragment {

    private View mView;
    private static final int msgKey1 = 1;
    private TextView mTimeTv;

    public WriteNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_write_note,container,false);

        mTimeTv= (TextView) mView.findViewById(R.id.tv_writenote_time);
        SetTime();
        return mView;
    }
public void SetTime(){
    Time time = new Time("GMT+8");
    time.setToNow();
    int year = time.year;
    int month = time.month;
    int day = time.monthDay;
    int minute = time.minute;
    int hour = time.hour;
    int sec = time.second;
    mTimeTv.setText( year +
            "年 " + month +
            "月 " + day +
            "日 " + hour +
            "时 " + minute +
            "分 " + sec +
            "秒");
}

}
