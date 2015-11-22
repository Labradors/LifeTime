package org.jiangtao.NetWorkUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mr-jiang on 15-11-22.
 * 获取验证码
 */
public class VerificationCode {
    private static VerificationCode verCode;
    private JsonObjectRequest jsonRequest;
    private JSONObject mEmail;
    public static String result;
    private static final String TAG = VerificationCode.class.getSimpleName();


    private VerificationCode() {
    }

    /**
     * 设置为单例模式，防止随意初始化
     *
     * @return
     */
    public static VerificationCode getInstance() {
        if (verCode == null) {
            verCode = new VerificationCode();
        }
        return verCode;
    }

    public String getVerificationCode(String mEmail) {
        jsonRequest = new JsonObjectRequest(Request.Method.GET,
                ConstantValues.verificationCodeUrl, getJsonObject(mEmail),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            result = jsonObject.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(TAG, volleyError.toString());
            }
        });
        jsonRequest.setTag("verificationCode");
        LifeApplication lifeApplication = LifeApplication.getInstance();
        LifeApplication.getRequestQueue().add(jsonRequest);
        return result;
    }

    /**
     * 获得单个数据的json请求
     *
     * @param mEmail
     * @return
     */
    public JSONObject getJsonObject(String mEmail) {
        try {
            this.mEmail = new JSONObject(mEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.mEmail;
    }
}
