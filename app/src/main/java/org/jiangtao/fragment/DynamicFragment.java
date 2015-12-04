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
    private RecyclerView.Adapter<DynamicAdapter.ViewHolder> mDynamicAdapter;
    private RecyclerView.LayoutManager manager;
    public Context context;
    public DynamicArticleBusiness dynamicArticleBusiness;
    public Handler handler;

    public DynamicFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initControl();
        hideImageView();
        mSwipeRefreshLayout.setOnRefreshListener(this);
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

                        } else {
                            LogUtils.d(TAG, "返回为空");
                        }
                    }
                    case 0x456: {
                        if (msg.obj != null && mList != null) {
                            mRecyclerViewDataFilling();

                        } else {
                            LogUtils.d(TAG, "---------" + mList.size());
                        }

                    }
                }
            }
        };
    }

    private void requestData() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        //判断有无网络
        if (LifeApplication.hasNetWork) {
            getNetMListThread netMListThread = new getNetMListThread();
            netMListThread.start();
        } else {

            getCacheMListThread cacheMListThread = new getCacheMListThread();
            cacheMListThread.start();
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

    /**
     * 有网条件下加载数据
     */
    public class getNetMListThread extends Thread {
        @Override
        public void run() {
            super.run();
            mList = RequestArticleData.getInstance().
                    getArticleData(ConstantValues.getAllArticleUrl, context);
            if (mList != null) {
                Message message = new Message();
                message.what = 0x345;
                message.obj = mList;
                handler.sendMessage(message);
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
            dynamicArticleBusiness = new DynamicArticleBusinessImpl(context);
            try {
                mList = dynamicArticleBusiness.getDynamicArticles();
                if (mList != null) {
                    Message message = new Message();
                    message.what = 0x456;
                    message.obj = mList;
                    handler.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
