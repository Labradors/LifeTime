package org.jiangtao.networkutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mr-jiang on 15-11-28.
 * 发起网络请求，请求图片
 */
public class LoadHeadImage {
    private static final String TAG = LoadHeadImage.class.getSimpleName();
    public static boolean isNetWorkError = true;
    public static Bitmap bitmap;

    /**
     * 根据地址和位置发起网络请求
     *
     * @param uri
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap loadNetWorkHeadImage(String uri, String url) throws Exception {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("headImageUri", uri);
        RequestBody body = builder.build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                isNetWorkError = false;
                LogUtils.d(TAG, "网络错误");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                File file = new File(Environment.getExternalStorageDirectory() +
                        "/lifetime/headImage/");
                InputStream inputStream;
                inputStream = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        };
        LifeApplication.getNetWorkResponse(callback, body, url);
        return bitmap;
    }
}
