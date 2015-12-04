package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 * 软件设置界面
 */
public class AppSettingFragment extends android.support.v4.app.Fragment {

    private View mView;

    public AppSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_app_setting, container, false);
        return mView;
    }


}
