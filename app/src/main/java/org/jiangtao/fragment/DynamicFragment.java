package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends android.support.v4.app.Fragment implements OnRefreshListener {

    private View mView;
    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListViewDynamic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initControl();
        swipeColorListener();
        return mView;
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_dynamic);
        mListViewDynamic = (ListView) mView.findViewById(R.id.listview_fragment_dynamic);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(
                R.id.refresh_fragment_dynamic);
    }

    /**
     * 设置刷新界面的颜色
     */
    private void swipeColorListener() {
//        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color
//                .holo_green_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 有网或者有缓存
     */
    private void hideImageView() {

        mImageView.setVisibility(View.GONE);
    }

    /**
     * 没网，没缓存
     */
    private void showImageView() {
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 实现刷新操作
     */
    @Override
    public void onRefresh() {

    }
}
