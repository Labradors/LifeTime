package org.jiangtao.networkutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.utils.LogUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-2.
 */
public class RequestArticleData {
    public static final String TAG = RequestArticleData.class.getSimpleName();
    private ArrayList<ArticleAllDynamic> mList;
    private static RequestArticleData data;
    public static Bitmap bitmap = null;

    private RequestArticleData() {
    }

    public static RequestArticleData getInstance() {
        if (data == null) {
            data = new RequestArticleData();
        }
        return data;
    }


    /**
     * 取得所有文章的内容
     *
     * @param url
     * @return
     */
    public ArrayList<ArticleAllDynamic> getArticleData(String url) {
        mList = new ArrayList<>();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (LifeApplication.hasNetWork) {
                    String articleData = response.body().string();
                    try {
                        JSONArray array = new JSONArray(articleData);
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject data = array.getJSONObject(i);
                                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                                dynamic.setUser_name(data.getString("user_name"));
                                dynamic.setUser_headpicture(data.getString("user_headpicture"));
                                dynamic.setArticle_id(data.getInt("article_id"));
                                dynamic.setArticle_user_id(data.getInt("article_user_id"));
                                dynamic.setArticle_time(null);
                                dynamic.setArticle_content(data.getString("article_content"));
                                dynamic.setArticle_image(data.getString("article_image"));
                                dynamic.setArticle_love_number(data.getInt("article_love_number"));
                                dynamic.setArticle_comment_number(data.getInt("article_comment_number"));
                                mList.add(dynamic);
                                LogUtils.d(TAG, dynamic.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String articleData = response.cacheResponse().body().toString();
                    try {
                        JSONArray array = new JSONArray(articleData);
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject data = array.getJSONObject(i);
                                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                                dynamic.setUser_name(data.getString("user_name"));
                                dynamic.setUser_headpicture(data.getString("user_headpicture"));
                                dynamic.setArticle_id(data.getInt("article_id"));
                                dynamic.setArticle_user_id(data.getInt("article_user_id"));
                                dynamic.setArticle_time(null);
                                dynamic.setArticle_content(data.getString("article_content"));
                                dynamic.setArticle_image(data.getString("article_image"));
                                dynamic.setArticle_love_number(data.getInt("article_love_number"));
                                dynamic.setArticle_comment_number(data.getInt("article_comment_number"));
                                mList.add(dynamic);
                                LogUtils.d(TAG, dynamic.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        LifeApplication.getResponse(callback, null, url);
        return mList;
    }

    /**
     * 获取文章的图片
     * 获取用户的头像
     *
     * @param url
     * @param address
     * @return
     */
    public Bitmap getBitmap(String url, String address) {

        FormEncodingBuilder builder = new FormEncodingBuilder().add("user_image", address);
        RequestBody body = builder.build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.d("", "错误1");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (LifeApplication.hasNetWork) {
                    InputStream stream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(stream);
                } else {
                    InputStream stream = response.cacheResponse().body().byteStream();
                    bitmap = BitmapFactory.decodeStream(stream);
                }
            }
        };
        LifeApplication.getResponse(callback, body, url);
        return bitmap;
    }

}
