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
public class RecommendFragment extends android.support.v4.app.Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = RecommendFragment.class.getSimpleName();
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


    public RecommendFragment() {

    }

    public RecommendFragment(Context context) {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recommend, container, false);
        variableInitialization();
        requestData();
        adapterFilling();
        return mView;
    }

    private void requestData() {
        new CommentAsyncTask().execute();
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            RequestArticleData.getInstance().
                    getArticleData(ConstantValues.getAllArticleUrl, mContext);
        }
        new CommentAsyncTask().execute();
    }


    private void adapterFilling() {
        mDynamicAdapter = new DynamicAdapter(commentArticle, mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDynamicAdapter);
    }

    private void variableInitialization() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById
                (R.id.container_swipelayout_recomment);
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_recomment);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recomment_recycleview);
        commentArticle = new ArrayList<>();
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


    public class CommentAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArticleAllDynamic> mlists;
            try {
                mlists = mBusiness.getDynamicArticleByComment();
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
}
