package org.jiangtao.lifetime;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.jiangtao.adapter.DynamicAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.bean.OtherUser;
import org.jiangtao.networkutils.ObtainOtherUser;
import org.jiangtao.sql.DynamicArticleDaoImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 查看别人用户的主页
 */
public class UserHomePageActivity extends AppCompatActivity {

    private static final String TAG = UserHomePageActivity.class.getSimpleName();
    private CircleImageView mHeadImageCircleImageView;
    private TextView mUserNameTextView;
    public RecyclerView mRecylerView;
    private TextView mUserJoinTime;
    private TextView mUserSex;
    private int user_id;
    private DynamicArticleDaoImpl dynamicArticleDao;
    private DynamicAdapter dynamicAdapter;
    public LinearLayoutManager manager;
    private ArrayList<ArticleAllDynamic> mLists = new ArrayList<>();
    public Context context;
    UserAsyncTask asyncTask = new UserAsyncTask();
    UserArticleAsyncTask userAsyncTask = new UserArticleAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        obtainUserInfo();
        openAsyncTask();
        controlsInitialization();
        fillingRecylerView();

    }

    private void fillingRecylerView() {
        mRecylerView.setLayoutManager(manager);
        dynamicAdapter = new DynamicAdapter(
                mLists, context);
        mRecylerView.setAdapter(dynamicAdapter);
    }

    private void openAsyncTask() {
        asyncTask.execute();
        userAsyncTask.execute();
    }

    private void obtainUserInfo() {
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        LogUtils.d(TAG, user_id + "");
        //查询本地用户的文章
        //联网下载用户信息
    }

    private void controlsInitialization() {
        mHeadImageCircleImageView = (CircleImageView) findViewById(R.id.homepage_user_headimage);
        mUserNameTextView = (TextView) findViewById(R.id.homepage_user_name);
        mRecylerView = (RecyclerView) findViewById(R.id.homepage_recycleview);
        mUserJoinTime = (TextView) findViewById(R.id.homepage_jointime);
        mUserSex = (TextView) findViewById(R.id.homepage_sex);
        dynamicArticleDao = new DynamicArticleDaoImpl(this);
        manager = new LinearLayoutManager(this);
        context = this;
    }

    /**
     * 重写一个异步方法
     * 查询用户信息
     */
    public class UserAsyncTask extends AsyncTask<Integer, Void, OtherUser> {

        @Override
        protected OtherUser doInBackground(Integer... params) {
            return ObtainOtherUser.getOtherUserInfo(user_id, ConstantValues.getOtherUserInfoUrl);
        }

        @Override
        protected void onPostExecute(final OtherUser user) {
            super.onPostExecute(user);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUserSex.setText(user.getUser_sex());
                    LogUtils.d(TAG, user.getUser_headpicture());
                    mUserJoinTime.setText(user.getUser_time());
                    mUserNameTextView.setText(user.getUser_name());
                    LifeApplication
                            .picasso.load(ConstantValues.getArticleImageUrl +
                            user.getUser_headpicture()).into(mHeadImageCircleImageView);

                }
            });
        }
    }

    /**
     * 查找所有文章
     */
    public class UserArticleAsyncTask extends AsyncTask<Void, Void, ArrayList<ArticleAllDynamic>> {

        @Override
        protected ArrayList<ArticleAllDynamic> doInBackground(Void... params) {
            ArrayList<ArticleAllDynamic> dynamics = new ArrayList<>();
            try {
                dynamics = dynamicArticleDao.getDynamicArticleFromID(user_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dynamics;
        }

        @Override
        protected void onPostExecute(ArrayList<ArticleAllDynamic> articleAllDynamics) {
            super.onPostExecute(articleAllDynamics);
            mLists = articleAllDynamics;
            for (int i=0;i<mLists.size();i++){
                LogUtils.d(TAG,"?????????"+mLists.get(i).toString());
            }
            //向适配器传值
            //函数实现，页面公用
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fillingRecylerView();
                }
            });
        }
    }

}
