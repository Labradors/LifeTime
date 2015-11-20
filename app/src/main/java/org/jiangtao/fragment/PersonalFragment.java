package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jiangtao.lifetime.LoginActivity;
import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private TextView personalNoLoginTv;
    View view;


    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);


        personalNoLoginTv= (TextView)view. findViewById(R.id.personal_tv_nologin);
        return view;
    }


    @Override
    public void onClick(View v) {
        android.content.Intent intent=new android.content.Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
