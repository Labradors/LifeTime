package org.jiangtao.lifetime;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.jiangtao.NetWorkUtils.VerificationCode;
import org.jiangtao.utils.ValidateEmailAndNumber;

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
    public void registerOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_register: {
                if (validateValue()) {
                    /**
                     * 发送网络请求
                     * 需要发送图片，volley必须添加multiparty方法。
                     * 重写这个方法后，就可以用了
                     *
                     */


                }


                break;
            }
            case R.id.activity_register_btn_sentcheckemil: {
                if (validateValue()) {
                    flag = 2;
                    mSendValidateButton.setEnabled(false);
                    timeRemain(60l);
                    //网络请求
                    VerificationCode verificationCode = VerificationCode.getInstance();
                    netWorkValidatevalue = verificationCode.getVerificationCode(mEmail);
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
        getEditTextValue();
        if (flag == 1) {
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
        } else if (flag == 2) {
            if (ValidateEmailAndNumber.isNumeric(mValidatValue)) {
                if (mValidatValue.equals(netWorkValidatevalue)) {
                    return true;
                } else {
                    Snackbar.make(container, R.string.validate_error,
                            Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(container, R.string.validate_error,
                        Snackbar.LENGTH_SHORT).show();
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
