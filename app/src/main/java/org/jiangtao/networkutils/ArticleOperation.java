package org.jiangtao.networkutils;

import android.os.AsyncTask;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mr-jiang on 15-12-8.
 */
public class ArticleOperation {
    private static ArticleOperation articleOperation;
    private static final String TAG = ArticleOperation.class.getSimpleName();
    public AttentionOperate attentionOperate;

    private ArticleOperation() {
    }

    public static ArticleOperation getInstance() {
        if (articleOperation == null) {
            articleOperation = new ArticleOperation();
        }
        return articleOperation;
    }

    /**
     * 关注异步任务
     */
    public class AttentionAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int friend_user_id = params[0];
            int friend_another_id = params[1];
            //联网
            RequestBody body = new FormEncodingBuilder()
                    .add("friend_user_id", friend_user_id + "")
                    .add("friend_another_id", friend_another_id + "")
                    .build();
            Callback callback = new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    LogUtils.d(TAG, "请求失败");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String result = response.body().string();
                    try {
                        JSONObject object = new JSONObject(result);
                        String isSuccess = object.getString("isSuccess");
                        LogUtils.d(TAG, isSuccess);
                        if (isSuccess.equals("true")) {
                            attentionOperate.sendResult(true);
                        } else {
                            attentionOperate.sendResult(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                LifeApplication.getNetWorkResponse(callback, body, ConstantValues.attentionUserUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface AttentionOperate {
        public void sendResult(boolean result);
    }

    public void interfaceInstance(AttentionOperate mAttentionOperate) {
        attentionOperate = mAttentionOperate;
    }

}
