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
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.R;
import org.jiangtao.networkutils.RequestArticleData;
import org.jiangtao.sql.DynamicArticleBusiness;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.ConstantValues;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = LoveFragment.class.getSimpleName();
    private View mView;
    private Context mContext;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private ArrayList<ArticleAllDynamic> commentArticle;
    private DynamicAdapter mDynamicAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager manager;
    private DynamicArticleBusiness mBusiness;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x333: {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mDynamicAdapter.notifyDataSetChanged();
                    adapterFilling();
                    break;
                }
            }
        }
    };

    public LoveFragment() {
    }

    public LoveFragment(Context context) {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_love, container, false);
        variableInitialization();
        requestData();
        adapterFilling();
        return mView;
    }

    private void variableInitialization() {
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_love);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.love_recycleview);
        commentArticle = new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById
                (R.id.contain_swipe_love);
        manager = new LinearLayoutManager(mContext);
        mBusiness = new DynamicArticleBusinessImpl(mContext);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);
        requestData();
    }

    private void requestData() {
        new LoveAsyncTask().execute();
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            RequestArticleData.getInstance().
                    getArticleData(ConstantValues.getAllArticleUrl, mContext);
        }
        new LoveAsyncTask().execute();
    }


    private void adapterFilling() {
        mDynamicAdapter = new DynamicAdapter(commentArticle, mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDynamicAdapter);
    }

    public class LoveAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArticleAllDynamic> mlists;
            try {
                mlists = mBusiness.getDynamicArticleByLove();
                if (mlists.size() != 0 && mlists != null) {
                    commentArticle = mlists;
                    mHandler.sendEmptyMessage(0x333);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
