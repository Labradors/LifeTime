package org.jiangtao.lifetime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.jiangtao.networkutils.WelcomeLoadPicture;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;

/**
 * 应用欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private ImageView mWelcomeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mWelcomeImageView = (ImageView) findViewById(R.id.iv_welcome_activity_welcome);
        hideActionBar();
        if (LifeApplication.hasNetWork) {
            updateImageView();
        } else {
            mWelcomeImageView.setImageResource(R.drawable.welcome);
        }
        init();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME: {
                    goHome();
                    break;
                }
                case GO_GUIDE: {
                    goGuide();
                    break;
                }
            }
        }
    };

    /**
     * 多次使用，主界面
     */
    private void goHome() {
        Intent intent = new Intent(WelcomeActivity.this, IndexActivity.class);
        startActivity(intent);
    }

    /**
     * 第一次使用，引导界面
     */
    private void goGuide() {
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(intent);
    }

    /**
     * 从SharedPreferences中获取数据，查看是否是第一次登陆
     */
    private void init() {
        SharedPreferences perPreferences = getSharedPreferences("Jiangtao", MODE_PRIVATE);
        boolean isFirstIn = perPreferences.getBoolean("isFirstIn", true);
        if (!isFirstIn) {
            handler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.apply();
        }

    }

    /**
     * 隐藏actionbar
     */
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    /**
     * 联网的情况下从从网络中更新图片
     */
    private void updateImageView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WelcomeLoadPicture welcomeLoadPicture = WelcomeLoadPicture.getInstance();
                try {
                    final Bitmap bitmap = welcomeLoadPicture.loadImageFromLifeTime(ConstantValues
                            .welcomeUrl);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mWelcomeImageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
