package org.jiangtao.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jiangtao.adapter.DynamicAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.R;
import org.jiangtao.networkutils.RequestArticleData;
import org.jiangtao.sql.DynamicArticleBusiness;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

/**
 * @Date: 15-12-5
 * @Time: 上午1:56
 * @Author:
 * @Description:
 * @Method:
 * @Version:
 */
public class DynamicFragment extends android.support.v4.app.Fragment implements OnRefreshListener {

    private static final String TAG = DynamicFragment.class.getSimpleName();
    private View mView;
    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ArrayList<ArticleAllDynamic> mList;
    private RecyclerView.LayoutManager manager;
    public Context context;
    public DynamicArticleBusiness dynamicArticleBusiness;
    public DynamicAdapter mDynamicAdapter;
    public Handler handler;
    public boolean isFirstIn = true;

    public DynamicFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initControl();
        hideImageView();
        requestData();
        showUI();
        mRecyclerViewDataFilling();
        return mView;
    }

    /**
     * 根据返回的结果，对界面进行显示
     */
    public void showUI() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x345: {
                        if (msg.obj != null && mList != null) {
                            mRecyclerViewDataFilling();
                            mSwipeRefreshLayout.setRefreshing(false);
                            break;
                        }
                    }
                    case 0x456: {
                        if (msg.obj != null && mList != null) {
                            mRecyclerViewDataFilling();
                            mSwipeRefreshLayout.setRefreshing(false);
                            break;
                        }
                    }
                    case 0x567: {
                        showImageView();
                        LogUtils.d(TAG, "显示图片 ");
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                    }
                }
            }
        };
    }

    private void requestData() {
        mSwipeRefreshLayout.setRefreshing(true);
        //判断有无网络
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            getNetMListThread netMListThread = new getNetMListThread();
            netMListThread.start();
        } else {

            getCacheMListThread cacheMListThread = new getCacheMListThread();
            cacheMListThread.start();
        }
    }

    /**
     * 将adapter适配
     */
    private void mRecyclerViewDataFilling() {
        mRecyclerView.setLayoutManager(manager);
        mDynamicAdapter = new DynamicAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mDynamicAdapter);
        isFirstIn = false;
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
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 设置刷新界面的颜色
     */
    private void swipeColorListener() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);
        LogUtils.d(TAG, "下拉刷新");
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            new getNetMListThread().start();
            LogUtils.d(TAG, "更新界面");
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }

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

    /**
     * 有网条件下加载数据
     */
    public class getNetMListThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (isFirstIn) {
                mList = RequestArticleData.getInstance().
                        getArticleData(ConstantValues.getAllArticleUrl, context);
                if (mList != null) {
                    Message message = new Message();
                    message.what = 0x345;
                    message.obj = mList;
                    handler.sendMessage(message);
                }
            } else {
                //问题出在这儿
                mList = RequestArticleData.getInstance().
                        getArticleData(ConstantValues.getAllArticleUrl, context);

                if (mList.size() != 0 && mList != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.d(TAG, mList.size() + "=====");
                            mDynamicAdapter.refresh(mList);
                            mSwipeRefreshLayout.setRefreshing(false);
                            LogUtils.d(TAG, "进入这儿");
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            LogUtils.d(TAG, "进入mlist为空的地方");
                        }
                    });
                }
            }
        }
    }

    /**
     * 查询数据库的条件下
     */
    public class getCacheMListThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (isFirstIn) {
                dynamicArticleBusiness = new DynamicArticleBusinessImpl(context);
                try {
                    mList = dynamicArticleBusiness.getDynamicArticles();
                    if (mList != null && mList.size() != 0) {
                        Message message = new Message();
                        message.what = 0x456;
                        message.obj = mList;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 0x567;
                        message.obj = mList.size();
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mList = dynamicArticleBusiness.getDynamicArticles();
                    if (mList.size() != 0) {
                        mDynamicAdapter.refresh(mList);
                    } else {
                        showImageView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
