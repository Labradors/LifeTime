package org.jiangtao.networkutils;

import android.os.AsyncTask;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by mr-jiang on 15-11-29.
 */
public class UploadDynamic {
    public static final String TAG = UploadDynamic.class.getSimpleName();
    static String file_name;
    public UploadArticleResult mUploadArticleResult;
    private static UploadDynamic mUploadDynamic;

    private UploadDynamic() {
    }

    public static UploadDynamic getInstance() {
        if (mUploadDynamic == null) {
            mUploadDynamic = new UploadDynamic();
        }
        return mUploadDynamic;
    }

    public class uploadArticleAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String url = params[0];
            String article_content = params[1];
            String uri = params[2];
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            file_name = getUserName(uri);
            int user_id = LifeApplication.user_id;
            LogUtils.d(TAG, uri);
            final RequestBody body = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"article_content\"" + "\r\n"),
                            RequestBody.create(null, article_content))
                    .addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"user_id\"" + "\r\n"),
                            RequestBody.create(null, String.valueOf(user_id)))
                    .addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"file_name\"" + "\r\n"),
                            RequestBody.create(null, file_name))
                    .addPart(Headers.of("Content-Disposition", "form-data;" +
                                    " name=\"article_image\"" + "\r\n"),
                            RequestBody.create(MEDIA_TYPE_PNG, new File(uri)))
                    .build();
            Callback callback = new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseFlag = response.body().string();
                    LogUtils.d(TAG, responseFlag);
                    try {
                        JSONObject object = new JSONObject(responseFlag);
                        boolean responsePost = object.getBoolean("flag");
                        LogUtils.d(TAG, responsePost + ">>>>");
                        mUploadArticleResult.sendResult(responsePost);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            try {
                LifeApplication.getNetWorkResponse(callback, body, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String getUserName(String uri) {
        String b = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        System.out.println(b);
        return b;
    }

    public interface UploadArticleResult {
        public void sendResult(boolean result);
    }

    public void setUploadArticleResult(UploadArticleResult uploadArticleResult) {
        mUploadArticleResult = uploadArticleResult;
    }
}
