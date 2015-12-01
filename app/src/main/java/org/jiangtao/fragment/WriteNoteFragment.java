package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jiangtao.lifetime.R;
import org.jiangtao.utils.ConstantValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteNoteFragment extends android.support.v4.app.Fragment {

    private View mView;
    private static final int msgKey1 = 1;
    private TextView mTimeTv;
    private EditText mTitleEd;
    private EditText mContentEd;

    private String notePath = "/storage/sdcard0/lifetime/note/";

    private Button mBtnFinash;


    public WriteNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_write_note, container, false);

        mTimeTv = (TextView) mView.findViewById(R.id.tv_writenote_time);
        mBtnFinash = (Button) mView.findViewById(R.id.button_writenote_add);
        mTitleEd = (EditText) mView.findViewById(R.id.ed_writenote_title);
        mContentEd = (EditText) mView.findViewById(R.id.scrollview_writenote_edittext);


        SetTime();

        mBtnFinash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
                Toast.makeText(getActivity(), "存储成功" + ConstantValues.saveNoteUri, Toast.LENGTH_SHORT).show();
            }
        });
        return mView;
    }

    public void SetTime() {
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        mTimeTv.setText(year +
                "年 " + month +
                "月 " + day +
                "日 " + hour +
                "时 " + minute +
                "分 " + sec +
                "秒");
    }

    /**
     * 存储到SD卡中
     */
    public void Save() {
        try {
            String mTitle = mTitleEd.getText().toString();
            String mContent = mContentEd.getText().toString();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                File sdCardDir = Environment.getExternalStorageDirectory();
                File sdCardDir = new File(ConstantValues.saveNoteUri);
                File saveFile = new File(
                        sdCardDir, mTitle + ".txt");
                FileOutputStream outStream = new FileOutputStream(saveFile);
                outStream.write(mContent.getBytes());
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
