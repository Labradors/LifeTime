package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.jiangtao.utils.ZoomImageView;

/**
 * 注册完成，选择头像界面
 */
public class ChooseHeadPictureActivity extends AppCompatActivity {

    private Button mOkButton;
    private ZoomImageView mZoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_head_picture);
        initControl();
    }

    private void initControl() {
        mOkButton = (Button) findViewById(R.id.btn_activity_ok);
        mZoomImageView = (ZoomImageView) findViewById(R.id.ziv_activity_choose_picture);
    }

    /**
     * 确认按钮的监听器
     * @param view
     */
    public void okOnClick(View view) {

    }
}
