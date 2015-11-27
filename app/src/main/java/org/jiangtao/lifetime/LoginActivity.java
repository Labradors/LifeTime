package org.jiangtao.lifetime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.User;
import org.jiangtao.utils.Code;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.JSONUtil;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.TurnActivity;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private EditText mEditTextUserEmal;
    private EditText mEditTextPassWord;
    private String userName;
    private String passWord;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
        getEditTextValue();
    }

    /**
     * 获得EditText的值
     */
    private void getEditTextValue() {
        userName = mEditTextUserEmal.getText().toString().trim();
        passWord = mEditTextPassWord.getText().toString().trim();
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_login_container);
        mEditTextUserEmal = (EditText) findViewById(R.id.personal_login_username);
        mEditTextPassWord = (EditText) findViewById(R.id.personal_login_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //新用户注册
            case R.id.personal_login_newuser: {
                TurnActivity.turnRegisterActivity(LoginActivity.this);
                break;
            }
            //跳转到找回密码界面
            case R.id.activity_login_forget_password: {

                Intent intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                startActivityForResult(intent, Code.FORGOT_PASSWORD_REQUESTCODE);
                break;
            }

            /**
             * 获得结果后验证
             * 验证后提交服务器请求。
             * 返回的结果为空，说明不存在
             * 返回结果不为空，获得解析其结果
             * 回调到personalFragment
             */
            case R.id.personal_login_FrameLayout_buttom_btn_login: {
                LogUtils.d(TAG, "提示。。。。。");
                responseLoginInformation();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void responseLoginInformation() {
        getEditTextValue();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        LogUtils.d(TAG, ">>>>>>" + userName);
        LogUtils.d(TAG, ">>>>>>" + passWord);
        builder.add("userEmail", userName);
        builder.add("passWord", passWord);
        Request request = new Request.Builder().url(
                ConstantValues.loginUrl
        ).post(builder.build()).build();
        LifeApplication.getCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                throw new RuntimeException(String.valueOf(R.string.runtime_error));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String userInformation = response.body().string();
                try {
                    JSONObject object = new JSONObject(userInformation);
                    User user = (User) JSONUtil.JSONToObj(userInformation, User.class);
                    /**
                     * 用户头像的路径不为null
                     * 开启请求得到其bitmao
                     */
                    if (user != null) {
                        if (user.getUser_headpicture() != null) {
                            String imageAddress = user.getUser_headpicture();
                            LifeApplication.isLogin = true;
                            LogUtils.d(TAG, userInformation);
                            LogUtils.d(TAG, response.toString());
                            LogUtils.d(TAG, userInformation);
                        } else {
                            LifeApplication.isLogin = true;
                            LogUtils.d(TAG, ">>><<<<" + user.toString());

                        }
                    } else {
                        Snackbar.make(mLinearLayout, R.string.please_register,
                                Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
