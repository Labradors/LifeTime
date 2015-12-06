package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.lifetime.R;
import org.jiangtao.lifetime.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * 软件设置界面
 */
public class AppSettingFragment extends android.support.v4.app.Fragment implements SettingActivity.UserCallBack {

    public float cacheSize;
    private View mView;
    private TextView mCacheSizeTextView;

    public AppSettingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_app_setting, container, false);
        controlsInistance();
        computeCacheSize();
        return mView;
    }

    /**
     * 计算缓存大小
     *
     * @return
     */
    private float computeCacheSize() {
        int cacheSizeB = LifeApplication.lruCache.size();
        cacheSize = cacheSizeB / 1024 / 1024;
        mCacheSizeTextView.setText(cacheSize + "Mb");
        return cacheSize;
    }

    private void controlsInistance() {
        mCacheSizeTextView = (TextView) mView.findViewById(R.id.setting_app_cache_size);
    }

    @Override
    public void sendMessage(boolean flag) {
        if (flag) {
            mCacheSizeTextView.setText("");
        }
    }
}
