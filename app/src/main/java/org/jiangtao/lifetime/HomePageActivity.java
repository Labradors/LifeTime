package org.jiangtao.lifetime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.jiangtao.adapter.HomePageAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.bean.OtherUser;
import org.jiangtao.networkutils.ObtainOtherUser;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends AppCompatActivity {

    private static final String TAG = HomePageActivity.class.getSimpleName();
    private CircleImageView mMyselfCircleImageView;
    private TextView mMyselfNameTextView;
    private TextView mMyselfJoinTimeTextView;
    private TextView mMyselfSexTextView;
    private RecyclerView mMyselfReecycleView;
    private HomePageAdapter mHomePageAdapter;
    private LinearLayoutManager mManager;
    private ArrayList<ArticleAllDynamic> mArticleList;
    private DynamicArticleBusinessImpl business;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111: {
                    fillingAdapter();
                    mHomePageAdapter.notifyDataSetChanged();
                    break;
                }
                case 0x112: {
                    LogUtils.d(TAG, "数据库中没有数据");
                    break;
                }
                case 0x144: {
                    requestAdapter();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().hide();
        controlsInstacnce();
        requestData();
        fillingAdapter();
        mHomePageAdapter.instanceNoticeActivityRefreshAdapter(new HomePageAdapter.NoticeActivityRefreshAdapter() {
            @Override
            public void refreshAdapter(boolean result) {
                mHandler.sendEmptyMessage(0x144);
            }
        });
    }

    public void requestData() {
        new UserAsyncTask().execute();
        new MyselfArticleAsyncTask().execute();
    }

    public void requestAdapter() {
        new MyselfArticleAsyncTask().execute();
    }

    private void fillingAdapter() {
        mHomePageAdapter = new HomePageAdapter(mArticleList, this);
        mMyselfReecycleView.setItemAnimator(new DefaultItemAnimator());
        mMyselfReecycleView.setLayoutManager(mManager);
        mMyselfReecycleView.setAdapter(mHomePageAdapter);
    }

    private void controlsInstacnce() {
        mMyselfCircleImageView = (CircleImageView) findViewById(R.id.homepage_myself_headimage);
        mMyselfNameTextView = (TextView) findViewById(R.id.homepage_myself_name);
        mMyselfJoinTimeTextView = (TextView) findViewById(R.id.homepage_myself_jointime);
        mMyselfSexTextView = (TextView) findViewById(R.id.homepage_myself_sex);
        mMyselfReecycleView = (RecyclerView) findViewById(R.id.homepage_myself_recycleview);
        mManager = new LinearLayoutManager(this);
        mArticleList = new ArrayList<>();
        business = new DynamicArticleBusinessImpl(this);
    }

    public class MyselfArticleAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mArticleList = business.getDynamicFromID(LifeApplication.user_id);
                if (mArticleList.size() != 0 && mArticleList != null) {
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


    public class UserAsyncTask extends AsyncTask<Integer, Void, OtherUser> {

        @Override
        protected OtherUser doInBackground(Integer... params) {
            return ObtainOtherUser.getOtherUserInfo(LifeApplication.user_id, ConstantValues.getOtherUserInfoUrl);
        }

        @Override
        protected void onPostExecute(final OtherUser user) {
            super.onPostExecute(user);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMyselfSexTextView.setText(user.getUser_sex());
                    LogUtils.d(TAG, user.getUser_headpicture());
                    mMyselfJoinTimeTextView.setText(user.getUser_time());
                    mMyselfNameTextView.setText(user.getUser_name());
                    LifeApplication
                            .picasso.load(ConstantValues.getArticleImageUrl +
                            user.getUser_headpicture()).into(mMyselfCircleImageView);

                }
            });
        }
    }
}
