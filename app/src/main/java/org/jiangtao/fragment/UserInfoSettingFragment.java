package org.jiangtao.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jiangtao.lifetime.R;
import org.jiangtao.sql.UserBusinessImpl;

/**
 * A simple {@link Fragment} subclass.
 * 用户信息设置面板
 */
public class UserInfoSettingFragment extends android.support.v4.app.Fragment {

    private UserBusinessImpl business;
    public UserInfoSettingFragment(Context context) {
    business=new UserBusinessImpl(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info_setting, container, false);
    }


}
