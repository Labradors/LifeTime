package org.jiangtao.networkutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
    public BitmapCallBack mBitmapCallBack;
    private static LoadHeadImage loadHeadImage;

    private LoadHeadImage() {
    }

    public static LoadHeadImage getInstance() {
        if (loadHeadImage == null) {
            loadHeadImage = new LoadHeadImage();
        }
        return loadHeadImage;
    }

    public interface BitmapCallBack {
        public void sendBitmap(Bitmap bitmap);
    }

    public void BitmapListener(BitmapCallBack bitmapCallBack) {
        mBitmapCallBack = bitmapCallBack;
    }


    public class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String uri = params[0];
            String url = params[1];
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
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mBitmapCallBack.sendBitmap(bitmap);
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


}
