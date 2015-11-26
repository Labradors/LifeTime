package org.jiangtao.lifetime;

import android.os.Bundle;
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
import org.jiangtao.utils.TurnActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 找回密码界面
 */
public class RetrievePasswordActivity extends AppCompatActivity {
    private EditText mEditText;
    private RelativeLayout mRelativeLayout;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        mEditText = (EditText) findViewById(R.id.activity_retrieve_password_editText);
        Button mButton = (Button) findViewById(R.id.activity_retrieve_password_btn);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container_retieve_password);
        initValue();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initValue();
                if (userEmail != null) {
                    Snackbar.make(mRelativeLayout,
                            R.string.activity_retrieve_password_to_email,
                            Snackbar.LENGTH_LONG).show();
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("userEmail", userEmail);
                    Request request = new Request.Builder().url(
                            ConstantValues.retrievePasswordUrl
                    ).post(builder.build()).build();
                    LifeApplication.getCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Snackbar.make(mRelativeLayout,
                                    R.string.network_error,
                                    Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            boolean flag = false;
                            try {
                                String result = response.body().toString();
                                LogUtils.d("",">>>>>>>>>"+ result);
                                JSONObject object = new JSONObject(result);
                                flag = object.getBoolean("flag");
                                LogUtils.d("", ">>>>>>>" + flag);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (flag) {
                                Snackbar.make(mRelativeLayout,
                                        R.string.send_password_email,
                                        Snackbar.LENGTH_LONG).show();
                                finish();
                            } else {
                                Snackbar.make(mRelativeLayout,
                                        R.string.please_register,
                                        Snackbar.LENGTH_LONG).show();
                                TurnActivity.turnRegisterActivity(RetrievePasswordActivity.this);
                            }

                        }
                    });
                } else {
                    Snackbar.make(mRelativeLayout,
                            R.string.activity_retrieve_empty_input,
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initValue() {
        userEmail = mEditText.getText().toString().trim();
    }
}
