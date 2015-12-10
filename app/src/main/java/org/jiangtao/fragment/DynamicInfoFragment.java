package org.jiangtao.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jiangtao.adapter.DynamicAdapter;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.R;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 所有的动态信息
 */
public class DynamicInfoFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = DynamicInfoFragment.class.getSimpleName();
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private ArrayList<ArticleAllDynamic> mArticleLists;
    private DynamicAdapter mDynamicAdapter;
    private Context mContext;
    private DynamicArticleBusinessImpl businessImpl;
    private LinearLayoutManager manager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111: {
                    adapterFilling();
                    mDynamicAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
                case 0x112: {
                    adapterFilling();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
            }
        }
    };

    public DynamicInfoFragment() {

    }

    public DynamicInfoFragment(Context context) {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_info, container, false);
        controlsInit();
        requestSqliteData();
        adapterFilling();
        return mView;
    }

    private void adapterFilling() {
        mDynamicAdapter = new DynamicAdapter(mArticleLists, mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDynamicAdapter);
    }

    private void requestSqliteData() {
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setRefreshing(true);
       new ObtainFriendArticle().execute();
    }

    private void controlsInit() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)
                mView.findViewById(R.id.container_dynamicinfofragment);
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_friend_info);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleview_fragment_friend_article);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        manager = new LinearLayoutManager(mContext);
        mArticleLists = new ArrayList<>();
        businessImpl = new DynamicArticleBusinessImpl(mContext);


    }


    @Override
    public void onRefresh() {
        requestSqliteData();
        LogUtils.d(TAG, "你好啊 啊啊啊  ");
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

    public class ObtainFriendArticle extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArticleAllDynamic> mArticleList = new ArrayList<>();
            try {
                mArticleList = businessImpl.getFriendArticle();
                if (mArticleList.size() != 0 && mArticleList != null) {
                    mArticleLists = mArticleList;
                    mHandler.sendEmptyMessage(0x111);
                } else {
                    mHandler.sendEmptyMessage(0x112);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
