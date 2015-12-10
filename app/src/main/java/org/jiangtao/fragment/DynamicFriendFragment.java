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

import org.jiangtao.adapter.FriendAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Friend;
import org.jiangtao.lifetime.R;
import org.jiangtao.networkutils.ObtainFriend;
import org.jiangtao.sql.FriendBusinessImpl;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.TurnActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 所有关注的好友
 */
public class DynamicFriendFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = DynamicFriendFragment.class.getSimpleName();
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private ArrayList<Friend> mFriendList;
    private FriendAdapter mFriendAdapter;
    private Context mContext;
    private LinearLayoutManager manager;
    private FriendBusinessImpl businessImpl;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x222: {
                    FriendAdapterFilling();
                    mFriendAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
                case 0x223: {
                    FriendAdapterFilling();
                    mFriendAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
                case 0x224: {
                    mFriendAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    TurnActivity.turnLoginActivity(mContext);
                    break;
                }
            }
        }
    };

    public DynamicFriendFragment() {

    }

    public DynamicFriendFragment(Context context) {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_friend, container, false);
        controlsInit();
        requestFriendData();
        hideImageView();
        FriendAdapterFilling();
        return mView;
    }

    public void requestFriendData() {
        if (LifeApplication.isLogin) {
            mSwipeRefreshLayout.setRefreshing(true);
            new ReadFriendSqliteAsyncTask().execute();
            ObtainFriend.getInstance().WriteFriendData(mContext,
                    LifeApplication.user_id);
            new ReadFriendSqliteAsyncTask().execute();
        } else {
            mHandler.sendEmptyMessage(0x224);
        }

    }

    private void FriendAdapterFilling() {
        mFriendAdapter = new FriendAdapter(mContext, mFriendList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mFriendAdapter);
    }

    private void controlsInit() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)
                mView.findViewById(R.id.container_dynamicfriendfragment);
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_friend_all);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleview_fragment_friend);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mFriendList = new ArrayList<>();
        manager = new LinearLayoutManager(mContext);
        businessImpl = new FriendBusinessImpl(mContext);
    }


    @Override
    public void onRefresh() {
        sRfOnReFreshListener();
    }

    public void sRfOnReFreshListener() {
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);
        requestFriendData();
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
     * 开启异步任务读取本地数据库
     */
    public class ReadFriendSqliteAsyncTask extends AsyncTask<Void, Void, ArrayList<Friend>> {
        @Override
        protected ArrayList<Friend> doInBackground(Void... params) {
            try {
                ArrayList<Friend> friends = businessImpl.selectAllFriend();
                if (friends.size() != 0 && friends != null) {
                    mFriendList = friends;
                    LogUtils.d(TAG,"本地朋友表部位空");
                    mHandler.sendEmptyMessage(0x222);
                } else {
                    mFriendList = new ArrayList<>();
                    LogUtils.d(TAG,"本地朋友表为空");
                    mHandler.sendEmptyMessage(0x223);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
