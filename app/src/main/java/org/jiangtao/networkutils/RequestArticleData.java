package org.jiangtao.networkutils;

import android.annotation.TargetApi;
import android.os.Build;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-2.
 */
public class RequestArticleData {
    private ArrayList<ArticleAllDynamic> mList;
    private static RequestArticleData data;

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

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Response response) throws IOException {
                String articleData = response.body().string();
                try {
                    JSONObject object = new JSONObject(articleData);
                    JSONArray array = new JSONArray(object);
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {
                            mList.add((ArticleAllDynamic) array.get(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LifeApplication.getResponse(callback, null, url);
        return mList;
    }

}
