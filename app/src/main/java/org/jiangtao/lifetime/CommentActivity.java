package org.jiangtao.lifetime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jiangtao.adapter.CommentAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Comment;
import org.jiangtao.networkutils.CommentOperater;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.TurnActivity;

import java.util.ArrayList;

/**
 * 下拉请求数据
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CommentActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private EditText mEditTextWriteComment;
    private Button mSendButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mArticleID;
    private ArrayList<Comment> mCommentLists;
    private CommentAdapter mCommentAdapter;
    private CommentOperater.CommentCallBack commentCallBack;
    private Handler mHandler;
    private String mCommentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        hideActionBar();
        obtainIntentValue();
        obtainComent(mArticleID);
        controlsInstance();
        adapterFilling();
        showNetData();

        //联网评论数据

    }

    private void showNetData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x555: {
                        adapterFilling();
                        mCommentAdapter.notifyDataSetChanged();
                        break;
                    }
                    case 0x556: {
                        Toast.makeText(CommentActivity.this, R.string.comment_null,
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 0x557: {
                        Toast.makeText(CommentActivity.this, R.string.comment_success,
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 0x558: {
                        Toast.makeText(CommentActivity.this, R.string.comment_fail,
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        };
    }

    public void getMCommentContent() {
        mCommentContent = mEditTextWriteComment.getText().toString().trim();
    }

    private void obtainComent(int mArticleID) {
        CommentOperater.getInstance().new CommentAsyncTask().execute(mArticleID);
        commentCallBack = new CommentOperater.CommentCallBack() {
            @Override
            public void sendCommentList(ArrayList<Comment> commentsList) {
                //得到数据后
                if (commentsList != null && commentsList.size() != 0) {
                    Message msg = new Message();
                    msg.what = 0x555;
                    mCommentLists = commentsList;
                    mHandler.sendMessage(msg);
                } else {
                    mHandler.sendEmptyMessage(0x556);
                }
            }
        };
        CommentOperater.getInstance().CommentCallBackInstance(commentCallBack);
    }

    private void adapterFilling() {
        mCommentAdapter = new CommentAdapter(mCommentLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCommentAdapter);
    }

    private void controlsInstance() {
        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recylerview);
        mEditTextWriteComment = (EditText) findViewById(R.id.comment_edittext);
        mSendButton = (Button) findViewById(R.id.comment_send_comment);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.comment_swipe);
        mCommentLists = new ArrayList<>();
        mSendButton.setOnClickListener(this);
    }

    private void obtainIntentValue() {
        Intent intent = getIntent();
        mArticleID = intent.getIntExtra("article_id", 0);

    }

    private void hideActionBar() {
        ActionBar bar = getSupportActionBar();
        bar.hide();
    }

    @Override
    public void onClick(View v) {
        if (LifeApplication.isLogin) {
            if (LifeApplication.getInstance().isNetworkAvailable()) {
                getMCommentContent();
                LogUtils.d(TAG,mCommentContent);
                if (mCommentContent != null && mCommentContent.length() != 0) {
                    //开启异步任务
                    CommentOperater.getInstance().new AddCommentAsyncTask().execute(
                            String.valueOf(LifeApplication.user_id), String.valueOf(mArticleID),
                            mCommentContent
                    );
                    CommentOperater.getInstance().UploadCommentResultListener(new CommentOperater.UploadCommentResult() {
                        @Override
                        public void sendCommentResult(boolean isSuccess) {
                            if (isSuccess) {
                                mHandler.sendEmptyMessage(0x557);
                            } else {
                                mHandler.sendEmptyMessage(0x558);
                            }
                        }
                    });
                } else {
                    Toast.makeText(CommentActivity.this,
                            R.string.comment_not_null, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CommentActivity.this,
                        R.string.network_error, Toast.LENGTH_LONG).show();
            }
        } else {
            TurnActivity.turnLoginActivity(CommentActivity.this);
        }
    }
}
