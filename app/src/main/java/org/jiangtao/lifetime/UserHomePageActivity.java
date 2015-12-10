package org.jiangtao.lifetime;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jiangtao.adapter.DynamicAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.bean.OtherUser;
import org.jiangtao.networkutils.ArticleOperation;
import org.jiangtao.networkutils.ObtainOtherUser;
import org.jiangtao.sql.DynamicArticleDaoImpl;
import org.jiangtao.sql.FriendBusinessImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.TurnActivity;

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
    public Button mAttentionButton;
    private int user_id;
    private DynamicArticleDaoImpl dynamicArticleDao;
    private DynamicAdapter dynamicAdapter;
    public LinearLayoutManager manager;
    private ArrayList<ArticleAllDynamic> mLists = new ArrayList<>();
    public Context context;
    public FriendBusinessImpl business;
    UserAsyncTask asyncTask = new UserAsyncTask();
    UserArticleAsyncTask userAsyncTask = new UserArticleAsyncTask();
    public boolean mAttentionFlag;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111: {
                    mAttentionFlag = true;
                    mAttentionButton.setText(R.string.reset_attention);
                    break;
                }
                case 0x112: {
                    mAttentionFlag = false;
                    mAttentionButton.setText(R.string.attention);
                    break;
                }
                case 0x113: {
                    mAttentionFlag = true;
                    mAttentionButton.setText(R.string.reset_attention);
                    break;
                }
                case 0x114: {
                    mAttentionFlag = false;
                    mAttentionButton.setText(R.string.attention);
                    break;
                }
                case 0x115: {
                    mAttentionFlag = false;
                    mAttentionButton.setText(R.string.attention);
                    break;
                }
                case 0x116: {
                    mAttentionFlag = true;
                    mAttentionButton.setText(R.string.reset_attention);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        obtainUserInfo();
        judgmentFriend();
        openAsyncTask();
        controlsInitialization();
        attentionOnClick();
        fillingRecylerView();


    }

    /**
     * 判断用户是否登陆
     */
    private void judgmentFriend() {
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            //联网判断用户之间是否为好友
            ArticleOperation.getInstance().new JudgmentAttentionAsyncTask().execute(
                    LifeApplication.user_id, user_id
            );
            ArticleOperation.getInstance().interfaceInstance(new ArticleOperation.AttentionOperate() {
                @Override
                public void sendResult(int result) {
                    if (result == 0x111) {
                        //发送信息，操作界面
                        handler.sendEmptyMessage(0x111);
                    } else if (result == 0x112) {
                        handler.sendEmptyMessage(0x112);
                    }
                }
            });
        } else {
            Toast.makeText(UserHomePageActivity.this,
                    R.string.article_network_error, Toast.LENGTH_LONG).show();
        }
    }

    private void attentionOnClick() {
        mAttentionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LifeApplication.isLogin) {
                    if (LifeApplication.getInstance().isNetworkAvailable()) {
                        if (mAttentionFlag) {
                            new CancelAttentionAsyncTask().execute();
                            //一关注，取消关注的操作
                            ArticleOperation.getInstance().new deleteAttentionAsyncTask().execute(
                                    LifeApplication.user_id, user_id
                            );
                            ArticleOperation.getInstance().interfaceInstance(new ArticleOperation.AttentionOperate() {
                                @Override
                                public void sendResult(int result) {
                                    if (result == 0x115) {
                                        handler.sendEmptyMessage(0x115);
                                    } else if (result == 0x116) {
                                        handler.sendEmptyMessage(0x116);
                                    }
                                }
                            });

                        } else {
                            //关注操作
                            ArticleOperation.getInstance().new AttentionAsyncTask().execute(
                                    LifeApplication.user_id, user_id
                            );
                            ArticleOperation.getInstance().interfaceInstance(new ArticleOperation.AttentionOperate() {
                                @Override
                                public void sendResult(int result) {
                                    if (result == 0x113) {
                                        handler.sendEmptyMessage(0x113);
                                    } else if (result == 114) {
                                        handler.sendEmptyMessage(0x114);
                                    }
                                }
                            });

                        }
                    } else {
                        Toast.makeText(UserHomePageActivity.this,
                                R.string.article_network_error, Toast.LENGTH_LONG).show();
                    }
                } else {
                    TurnActivity.turnLoginActivity(UserHomePageActivity.this);
                }
            }
        });
    }

    private void fillingRecylerView() {
        mRecylerView.setLayoutManager(manager);
        dynamicAdapter = new DynamicAdapter(
                mLists, context);
        DynamicAdapter.mHeadIsClick = false;
        mRecylerView.setAdapter(dynamicAdapter);
    }

    private void openAsyncTask() {
        asyncTask.execute();
        userAsyncTask.execute();
    }

    private void obtainUserInfo() {
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        LogUtils.d(TAG, user_id + "获取的值");
    }

    private void controlsInitialization() {
        mHeadImageCircleImageView = (CircleImageView) findViewById(R.id.homepage_user_headimage);
        mUserNameTextView = (TextView) findViewById(R.id.homepage_user_name);
        mRecylerView = (RecyclerView) findViewById(R.id.homepage_recycleview);
        mUserJoinTime = (TextView) findViewById(R.id.homepage_jointime);
        mUserSex = (TextView) findViewById(R.id.homepage_sex);
        mAttentionButton = (Button) findViewById(R.id.dynamic_button);
        dynamicArticleDao = new DynamicArticleDaoImpl(this);
        manager = new LinearLayoutManager(this);
        context = this;
        business = new FriendBusinessImpl(this);
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
            for (int i = 0; i < mLists.size(); i++) {
                LogUtils.d(TAG, "?????????" + mLists.get(i).toString());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DynamicAdapter.mHeadIsClick = true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class CancelAttentionAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                boolean a = business.deleteFriendAccordingToID(user_id);
                LogUtils.d(TAG, "删除是否成功" + a);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
