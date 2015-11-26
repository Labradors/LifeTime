package org.jiangtao.lifetime;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.ValidateEmailAndNumber;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 根据邮箱获得注册信息
 */
public class RegisterActivity extends AppCompatActivity {
    private final static String TAG = RegisterActivity.class.getSimpleName();
    private EditText mUserNameEditText;
    private EditText mPassWordEditText;
    private EditText mRepeatPassWordEditText;
    private EditText mEmailEditText;
    private EditText mEmailValidateEditText;
    private Button mSendValidateButton;
    private Button mRegisterButton;
    private int flag;
    private String mUserName;
    private String mPassWord;
    private String mRepeatPassWord;
    private String mEmail;
    private String mValidatValue;
    //返回的验证码校验
    private String netWorkValidatevalue;
    private RelativeLayout container;
    private CountDownTimer mCountDownTimer;
    public long timeRemain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        flag = 1;
        initEditText();
        getEditTextValue();
    }

    /**
     * 初始化EditText控件
     */
    private void initEditText() {
        mUserNameEditText = (EditText) findViewById(R.id.activity_register_et_username);
        mPassWordEditText = (EditText) findViewById(R.id.activty_register_tv_password);
        mRepeatPassWordEditText = (EditText) findViewById(R.id.activity_register_tv_repassword);
        mEmailEditText = (EditText) findViewById(R.id.activity_register_tv_email);
        mEmailValidateEditText = (EditText) findViewById(R.id.activity_register_email_check);
        container = (RelativeLayout) findViewById(R.id.register_container);
        mSendValidateButton = (Button) findViewById(R.id.activity_register_btn_sentcheckemil);
        mRegisterButton = (Button) findViewById(R.id.btn_activity_register);
    }

    /**
     * 两个按钮的单击事件
     * 联网获取图片和json
     *
     * @param view
     */
    public void registerOnClick(View view) throws JSONException {
        switch (view.getId()) {
            /**
             * 点击注册
             */
            case R.id.btn_activity_register: {
                getEditTextValue();
                if (ValidateEmailAndNumber.isNumeric(mValidatValue)) {
                    if (netWorkValidatevalue.equals(mValidatValue)) {
                        /**
                         * 保存用户信息到数据库
                         *
                         */
                        FormEncodingBuilder builder = new FormEncodingBuilder();
                        builder.add("userName", mUserName);
                        builder.add("passWord", mPassWord);
                        builder.add("email", mEmail);
                        Request request = new Request.Builder().url(
                                ConstantValues.registerInformationUrl
                        ).post(builder.build()).build();
                        LifeApplication.getCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                LogUtils.d(TAG, request.toString());
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String emailJson = response.body().string();
                                LogUtils.d(TAG, emailJson);
                                try {
                                    JSONObject object = new JSONObject(emailJson);
                                    boolean flag = object.getBoolean("flag");
                                    int id = object.getInt("id");
                                    if (flag){
                                        Intent intent = new Intent(RegisterActivity.this,
                                                LoginActivity.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }else {
                        throw new RuntimeException("获取数据失败");
                    }
                }else {
                    throw new RuntimeException("获取数据失败");
                }
                break;
            }
            /**
             * 点击发送验证码
             */
            case R.id.activity_register_btn_sentcheckemil: {
                flag = 1;
                if (validateValue()) {
                    mSendValidateButton.setEnabled(false);
                    timeRemain(60l);
                    //网络请求s
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("email", mEmail);
                    Request request = new Request.Builder().url(
                            ConstantValues.verificationCodeUrl
                    ).post(builder.build()).build();
                    LifeApplication.getCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            LogUtils.d(TAG, request.toString());
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String emailJson = response.body().string();
                            LogUtils.d(TAG, emailJson);
                            try {
                                JSONObject object = new JSONObject(emailJson);
                                netWorkValidatevalue = object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
                break;
            }
        }

    }

    /**
     * 获取各个控件的值
     */
    public void getEditTextValue() {
        mUserName = mUserNameEditText.getText().toString().trim();
        mPassWord = mPassWordEditText.getText().toString().trim();
        mRepeatPassWord = mRepeatPassWordEditText.getText().toString().trim();
        mEmail = mEmailEditText.getText().toString().trim();
        mValidatValue = mEmailValidateEditText.getText().toString().trim();
    }

    /**
     * 发送到服务器之前的检查
     */
    private boolean validateValue() {
        if (flag == 1) {
            getEditTextValue();
            if (mUserName != null && mPassWord != null && mRepeatPassWord != null
                    && mEmail != null) {
                if (ValidateEmailAndNumber.isEmail(mEmail)) {

                    if (ValidateEmailAndNumber.isCommonValue(mPassWord, mRepeatPassWord)) {
                        return true;
                    } else {
                        mPassWordEditText.setText("");
                        mRepeatPassWordEditText.setText("");
                        flag = 1;
                        Snackbar.make(container, R.string.password_error, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Snackbar.make(container, R.string.email_error, Snackbar.LENGTH_SHORT)
                            .show();
                    flag = 1;
                }
            }
        }
        return false;
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * 按钮防止长点击
     *
     * @param time
     */
    public void timeRemain(long time) {
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long time) {
                mSendValidateButton.setText(time / 1000 + "秒");
                timeRemain = time / 1000;
                if (time <= 0) {
                    timeRemain = 60l;
                }
            }

            @Override
            public void onFinish() {
                mSendValidateButton.setText(R.string.regest_btn_sent_checkemail);
                mSendValidateButton.setEnabled(true);
            }
        }.start();
    }

}