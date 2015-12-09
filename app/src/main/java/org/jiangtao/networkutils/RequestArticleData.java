package org.jiangtao.networkutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.LogUtils;
import org.json.JSONArray;
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
    private ArrayList<ArticleAllDynamic> mUpdateLists;
    private static RequestArticleData data;
    public static Bitmap bitmap = null;
    private DynamicArticleBusinessImpl business;
    private int MAX_ID;

    private RequestArticleData() {
    }

    public static RequestArticleData getInstance() {
        if (data == null) {
            data = new RequestArticleData();
        }
        return data;
    }


    /**
     * 开启异步线程
     * 查看网络数据与本地数据之间的不同
     * 更新本地数据库
     *
     * @param url
     * @return
     */
    public void getArticleData(final String url, final Context context) {
        mList = new ArrayList<>();
        mUpdateLists = new ArrayList<>();
        business = new DynamicArticleBusinessImpl(context);
        //开启一个线程，将sqlite最大值取出
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    MAX_ID = business.getDynamicMaxID();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return MAX_ID;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                MAX_ID = integer;
                LogUtils.d(TAG, "oooooooo" + integer.toString());
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        Callback callback = new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                LogUtils.d(TAG, "响应失败");
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                if (LifeApplication.getInstance().isNetworkAvailable()) {
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
                                                dynamic.setArticle_time(data.getString("article_time"));
                                                dynamic.setArticle_content(data.getString("article_content"));
                                                dynamic.setArticle_image(data.getString("article_image"));
                                                dynamic.setArticle_love_number(data.getInt("article_love_number"));
                                                dynamic.setArticle_comment_number(data.getInt("article_comment_number"));
                                                mUpdateLists.add(dynamic);
                                                LogUtils.d(TAG, dynamic.toString());
                                            }
                                            //这部分代码没有用处，待
                                            for (int i = MAX_ID; i < array.length(); i++) {

                                                JSONObject data = array.getJSONObject(i);
                                                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                                                dynamic.setUser_name(data.getString("user_name"));
                                                dynamic.setUser_headpicture(data.getString("user_headpicture"));
                                                dynamic.setArticle_id(data.getInt("article_id"));
                                                dynamic.setArticle_user_id(data.getInt("article_user_id"));
                                                dynamic.setArticle_time(data.getString("article_time"));
                                                dynamic.setArticle_content(data.getString("article_content"));
                                                dynamic.setArticle_image(data.getString("article_image"));
                                                dynamic.setArticle_love_number(data.getInt("article_love_number"));
                                                dynamic.setArticle_comment_number(data.getInt("article_comment_number"));
                                                mList.add(dynamic);
                                                LogUtils.d(TAG, dynamic.toString());
                                            }
                                        }
                                        business.updateArticle(mUpdateLists);
//                                        business.insertDynamicArticles(mList);
                                        LogUtils.d(TAG, mList.size() + "&&&&&&&");
                                        LogUtils.d(TAG, "====写入数据库" + ((mList.size()) - MAX_ID) + "------");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        LifeApplication.getResponse(callback, null, url);
                        return null;
                    }
                }.execute();
            }
        }.execute();
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
