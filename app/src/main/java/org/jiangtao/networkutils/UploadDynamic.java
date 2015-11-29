package org.jiangtao.networkutils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by mr-jiang on 15-11-29.
 */
public class UploadDynamic {
    public static boolean isRight = true;
    public static boolean responsePost;

    /**
     * 上传用户所写的文章
     * 这个函数的作用是只管上传
     * 如果在没网的条件下，讲内容保存到本地数据库，在草稿箱里添加1
     * 然后在有网的情况下
     * 发送
     *
     * @param url
     * @param article_content
     * @param uri
     */
    public static boolean uploadDynamic(String url, String article_content, String uri) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        int user_id = LifeApplication.user_id;
        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(Headers.of("Content-Disposition",
                                "form-data; name=article_content"),
                        RequestBody.create(null, article_content))
                .addPart(Headers.of("Content-Disposition",
                                "form-data; name=user_id"),
                        RequestBody.create(null, String.valueOf(user_id)))
                .addPart(Headers.of("Content-Disposition", "form-data; name=article_image"),
                        RequestBody.create(MEDIA_TYPE_PNG, new File(uri))).build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                isRight = false;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseFlag = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseFlag);
                    responsePost = object.getBoolean("flag");
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
        return responsePost && isRight;
    }

}
