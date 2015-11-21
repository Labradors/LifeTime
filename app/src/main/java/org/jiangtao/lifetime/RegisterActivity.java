package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.jiangtao.utils.ValidateEmailAndNumber;

/**
 * 根据邮箱获得注册信息
 */
public class RegisterActivity extends AppCompatActivity {

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
    private String netWorkValidatevalue;
    private RelativeLayout container;
    private DelayRegisterButtonThread registerThread;
    private DelaySendValidateButtonThread sendValidateThread;


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
        sendValidateThread = new DelaySendValidateButtonThread();
        registerThread = new DelayRegisterButtonThread();
    }

    /**
     * 两个按钮的单击事件
     *
     * @param view
     */
    public void registerOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_register: {
                if (validateValue()) {
                    //网络请求
                }
                registerThread.start();
                break;
            }
            case R.id.activity_register_btn_sentcheckemil: {
                if (validateValue()) {
                    //网络请求
                }
                flag = 2;
                sendValidateThread.start();
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

    /**
     * 新建一个线程，用于让用户不能一直点击发送验证码按钮
     * 没有开启线程
     */
    private class DelaySendValidateButtonThread extends Thread {
        @Override
        public void run() {
            try {

                if (mSendValidateButton.isEnabled()) {
                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            mSendValidateButton.setEnabled(false);
                        }
                    });
                } else {
                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            mSendValidateButton.setEnabled(true);
                        }
                    });
                }
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 新建一个线程，用于让用户不能一直点击注册按钮、
     * 没有开启线程
     */
    private class DelayRegisterButtonThread extends Thread {
        @Override
        public void run() {
            try {

                if (mSendValidateButton.isEnabled()) {
                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            mRegisterButton.setEnabled(false);
                        }
                    });
                } else {
                    runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            mRegisterButton.setEnabled(true);
                        }
                    });
                }
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendValidateThread.interrupt();
        registerThread.interrupt();
        finish();
    }
}
