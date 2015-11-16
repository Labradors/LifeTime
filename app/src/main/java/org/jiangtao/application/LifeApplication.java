package org.jiangtao.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import org.jiangtao.utils.LogUtils;

/**
 * Created by mr-jiang on 15-11-13.
 */
public class LifeApplication extends Application {
    private static final String TAG = LifeApplication.class.getSimpleName();
    //判断手机是否联网
    public static boolean hasNetWork = false;
    //判断用户网络---3G or 4G
    public static boolean isPhoneNetWork = false;
    //判断手机是否连接wifi
    public static boolean isWiFiNetWork = false;

    @Override
    public void onCreate() {
        super.onCreate();
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
//        return ((networkManager != null) && (networkManager.getActiveNetworkInfo()
//                .getState() == NetworkInfo.State.CONNECTED) || (telephonyManager.
//                getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS));

        if (networkManager != null) {
            if ((networkManager.getActiveNetworkInfo()!=null)) {
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
