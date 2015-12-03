package org.jiangtao.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.User;
import org.jiangtao.lifetime.IndexActivity;
import org.jiangtao.lifetime.R;
import org.jiangtao.sql.UserBusinessImpl;
import org.jiangtao.utils.BitmapUtils;
import org.jiangtao.utils.CircleImageView;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends android.support.v4.app.Fragment implements IndexActivity.FragmentCallback {
    private static final String TAG = PersonalFragment.class.getSimpleName();
    private TextView personalNoLoginTv;
    private View view;
    private UserBusinessImpl business;
    private de.hdodenhof.circleimageview.CircleImageView mCircleImageView;
    private Button mCancelButton;

    public PersonalFragment() {
    }

    public PersonalFragment(Context context) {

        business = new UserBusinessImpl(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        controlsInit();
        mCircleImageView.setScaleType(CircleImageView.ScaleType.CENTER_CROP);
        try {
            setUi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showButton();
        return view;
    }

    private void showButton() {
        if (LifeApplication.isLogin) {
            mCancelButton.setVisibility(Button.VISIBLE);
        } else {
            mCancelButton.setVisibility(Button.GONE);
        }
    }

    private void controlsInit() {
        personalNoLoginTv = (TextView) view.findViewById(R.id.tv_fragment_username);
        mCircleImageView = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);
        mCancelButton = (Button) view.findViewById(R.id.btn_cancel_login);
    }

    /**
     * 设置界面
     *
     * @throws Exception
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setUi() throws Exception {
        if (LifeApplication.isLogin) {
            User user = business.selectUser(LifeApplication.user_email);
            LogUtils.d(TAG, "查看是否取出文件" + user.toString());
            if (user != null) {
                LogUtils.d(TAG, "---------" + user.toString());
                personalNoLoginTv.setText(user.getUser_name());
                LogUtils.d(TAG, user.getUser_headpicture());
                Bitmap bitmap = BitmapUtils.getBitmap(ConstantValues.saveImageUri +
                        LifeApplication.user_name + ".png");

                BitmapDrawable bd = new BitmapDrawable(bitmap);
                personalNoLoginTv.setText(user.getUser_name());
                mCircleImageView.setImageBitmap(bitmap);
                showButton();
            }
        }
    }


    /**
     * 回调activity，传值
     *
     * @param flag
     */
    @Override
    public void onMainAction(boolean flag) {
        if (flag) {
            //读取数据库，将数据呈现到fragment，刷新界面
            try {
                LogUtils.d(TAG, ">>>>>>" + LifeApplication.user_email);
                User user = business.selectUser(LifeApplication.user_email);
                LogUtils.d(TAG, "查看是否取出文件" + user.toString());
                if (user != null) {
                    LogUtils.d(TAG, "---------" + user.toString());
                    personalNoLoginTv.setText(user.getUser_name());
                    if (user.getUser_headpicture() != null) {
                        /**
                         * 将地址转化为bitmap
                         */
                        Bitmap bitmap = BitmapUtils.getBitmap(ConstantValues.saveImageUri +
                                LifeApplication.user_name + ".png");
                        mCircleImageView.setImageBitmap(bitmap);
                        showButton();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LifeApplication.isLogin = false;
            personalNoLoginTv.setText("未登录");
            mCircleImageView.setImageResource(R.drawable.welcome);
            showButton();
        }
    }
}
