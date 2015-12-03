package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jiangtao.adapter.DynamicAdapter;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.R;
import org.jiangtao.networkutils.RequestArticleData;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 请求服务器。做适配器，返回数据的定式：
 * author信息
 * 文章内容
 * 附带信息，评论等，多表查询
 * ---------------------------------------
 * 返回规则。
 */
public class DynamicFragment extends android.support.v4.app.Fragment implements OnRefreshListener {

    private static final String TAG = DynamicFragment.class.getSimpleName();
    private View mView;
    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ArrayList<ArticleAllDynamic> mList;
    private RecyclerView.Adapter<DynamicAdapter.ViewHolder> mDynamicAdapter;
    private RecyclerView.LayoutManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initControl();
        hideImageView();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        requestData();
        mRecyclerViewDataFilling();
        return mView;
    }

    private void requestData() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mList = RequestArticleData.getInstance().
                getArticleData(ConstantValues.getAllArticleUrl);
        if (mList != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mRecyclerViewDataFilling();
        } else {
            LogUtils.d(TAG, "返回为空");
        }
    }

    private void mRecyclerViewDataFilling() {
        mRecyclerView.setLayoutManager(manager);
        mDynamicAdapter = new DynamicAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mDynamicAdapter);
    }


    /**
     * 初始化控件
     */
    private void initControl() {
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_dynamic);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(
                R.id.refresh_fragment_dynamic);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dynamic_recycleview);
        manager = new LinearLayoutManager(getActivity());
        mList = new ArrayList<>();
    }

    /**
     * 设置刷新界面的颜色
     */
    private void swipeColorListener() {

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);

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
        swipeColorListener();
    }
}
