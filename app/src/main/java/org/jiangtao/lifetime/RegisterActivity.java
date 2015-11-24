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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.ValidateEmailAndNumber;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private long timeRemain;
    private JsonObjectRequest request;
    private JsonObjectRequest saveUserInformation;


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
                        saveUserInformation = new JsonObjectRequest(Request.Method
                                .POST, ConstantValues.registerInformationUrl,
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try {
                                            /**
                                             * 参数1：flag string类型的变量，表示true或者false
                                             * 参数2:id string类型的变量，传入下一个activity，根据值插入用户表中，插入的是文件的地址
                                             */
                                            if (jsonObject.getString("flag").equals("true")) {
                                                /**
                                                 * 开启另外一个activity
                                                 * 然后让用户选择自己的头像
                                                 */
                                                Intent intent = new Intent(RegisterActivity.this, ChooseHeadPictureActivity.class);
                                                intent.putExtra("id", jsonObject.getString("id"));
                                                startActivity(intent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("userName", mUserName);
                                params.put("passWord", mPassWord);
                                params.put("email", mEmail);
                                return params;
                            }
                        };
                    } else {
                        Snackbar.make(container, R.string.validate_error,
                                Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(container, R.string.validate_error,
                            Snackbar.LENGTH_SHORT).show();
                }
                request.setTag("saveUserInformation");
                LifeApplication.getRequestQueue().add(saveUserInformation);
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
                    request = new JsonObjectRequest(Request.Method.GET,
                            ConstantValues.verificationCodeUrl + "?email=" + mEmail, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        netWorkValidatevalue = jsonObject.getString("email");
                                        LogUtils.d(TAG, ">>>>>>>" + netWorkValidatevalue);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    LogUtils.d(TAG, jsonObject.toString());
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            LogUtils.d(TAG, volleyError.toString());
                        }
                    });
                    request.setTag("verificationCode");
                    LifeApplication.getRequestQueue().add(request);
                }
                break;
            }
        }
    }

    /**
     * 根据email获得其json对象
     *
     * @param mEmail
     * @return
     * @throws JSONException
     */
    public JSONObject getJsonObject(String mEmail) throws JSONException {
        JSONObject email = new JSONObject();
        email.put("email", mEmail);
        LogUtils.d(TAG, mEmail);
        return email;
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
        LifeApplication.getRequestQueue().cancelAll("verificationCode");
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
