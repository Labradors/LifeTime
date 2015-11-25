package org.jiangtao.lifetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.jiangtao.utils.Code;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLinearLayout;
    private EditText mEditTextUserEmal;
    private EditText mEditTextPassWord;
    private String userName;
    private String passWord;

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
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.activity_login_forget_password: {
                /**
                 * 获得结果后验证
                 * 验证后提交服务器请求。
                 * 返回的结果为空，说明不存在
                 * 返回结果不为空，获得解析其结果
                 * 回调到personalFragment
                 */

                break;
            }
            case R.id.personal_login_FrameLayout_buttom_btn_login: {
                Intent intent = new Intent(LoginActivity.this,RetrievePasswordActivity.class);
                startActivityForResult(intent, Code.FORGOT_PASSWORD_REQUESTCODE);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
