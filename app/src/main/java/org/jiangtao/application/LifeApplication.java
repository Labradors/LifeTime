package org.jiangtao.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jiangtao.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by mr-jiang on 15-11-13.
 * 全局application
 */
public class LifeApplication extends Application {
    private static final String TAG = LifeApplication.class.getSimpleName();
    //判断手机是否联网
    public static boolean hasNetWork = false;
    //判断用户网络---3G or 4G
    public static boolean isPhoneNetWork = false;
    //判断手机是否连接wifi
    public static boolean isWiFiNetWork = false;
    //全局LifeApplication
    private static LifeApplication lifeApplication;

    public static Call call;
    //判断是否登陆
    public static boolean isLogin;
    //设置OkHttpClient
    public static OkHttpClient mOkHttpClient = new OkHttpClient();
    //设置response
    public static Response response;

    /**
     * 单例application
     *
     * @return
     */
    public static LifeApplication getInstance() {
        return lifeApplication;
    }

    /**
     * 获得响应值
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response getResponse(Request request) throws IOException {
        response = mOkHttpClient.newCall(request).execute();
        return response;
    }



    /**
     * 获取用户回调
     *
     * @param request
     * @return
     */
    public static Call getCall(Request request) {

        call = mOkHttpClient.newCall(request);
        return call;
    }

    public void CacheResponse(File cacheDirectory) throws Exception {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCache(cache);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        lifeApplication = this;
        //设置缓存目录
        File sdcache = getExternalCacheDir();
        try {
            CacheResponse(sdcache.getAbsoluteFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        hasNetWork = isNetworkAvailable();
        LogUtils.d(TAG, "1.>>>>" + hasNetWork);
        isWiFiNetWork = isWiFiEnabled();
        LogUtils.d(TAG, "2.>>>>" + isWiFiNetWork);
        isPhoneNetWork = is3GNetWork();
        LogUtils.d(TAG, "3.>>>>" + isPhoneNetWork);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 手机是否联网
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager networkManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkManager != null) {
            NetworkInfo[] info = networkManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断wifi是否打开
     *
     * @return
     */
    private boolean isWiFiEnabled() {
        ConnectivityManager networkManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService
                (Context.TELEPHONY_SERVICE);
        if (networkManager != null) {
            if ((networkManager.getActiveNetworkInfo() != null)) {
                if (networkManager.getActiveNetworkInfo()
                        .getState() == NetworkInfo.State.CONNECTED) {
                    if (telephonyManager.
                            getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是3G网络
     *
     * @return
     */
    public boolean is3GNetWork() {
        ConnectivityManager networkManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = networkManager.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
}
