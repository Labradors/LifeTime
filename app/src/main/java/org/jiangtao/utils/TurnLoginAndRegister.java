package org.jiangtao.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.jiangtao.lifetime.LoginActivity;

/**
 * Created by mr-jiang on 15-11-21.
 * 页面中自由跳转登陆界面
 */
public class TurnLoginAndRegister {
    public static void turnLoginActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }
}
