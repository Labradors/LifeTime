package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 * 用户信息设置面板
 */
public class UserInfoSettingFragment extends Fragment {


    public UserInfoSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info_setting, container, false);
    }


}
