package org.jiangtao.NetWorkUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mr-jiang on 15-11-13.
 */
public class WelcomeLoadPicture {
    private static WelcomeLoadPicture welcomeLoadPicture;

    private WelcomeLoadPicture() {

    }

    public static WelcomeLoadPicture getInstance() {
        if (welcomeLoadPicture == null) {
            welcomeLoadPicture = new WelcomeLoadPicture();
        }
        return welcomeLoadPicture;
    }

    public Bitmap loadImageFromLifeTime(String path) throws Exception {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream bis = null;
        try {
            URL url = null;
            url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("请求服务器错误.");
            }
            bis = new BufferedInputStream(urlConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return bitmap;
    }
}
