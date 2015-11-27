package org.jiangtao.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.jiangtao.lifetime.CollectionActivity;
import org.jiangtao.lifetime.LoginActivity;
import org.jiangtao.lifetime.DynamicActivity;
import org.jiangtao.lifetime.RegisterActivity;
import org.jiangtao.lifetime.WriteDynamicActivity;

/**
 * Created by mr-jiang on 15-11-21.
 * 页面中自由跳转登陆界面
 */
public class TurnActivity {
    public static void turnLoginActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }
    public static void turnRegisterActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }

    /**
     * 跳转到我的动态activity
     * @param activity
     */
    public static void turnMyDynamicActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, DynamicActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }
    /**
     * 跳转到我的收藏activity
     */
    public static void turnMyCollectionActivity(AppCompatActivity activity){
        Intent intent = new Intent(activity, CollectionActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }
    /**
     * 跳转到 WriteDynamicActivity
     */
    public static void turnWrietDynamicActivity(AppCompatActivity activity){
        Intent intent = new Intent(activity, WriteDynamicActivity.class);
        activity.startActivityForResult(intent, 0x123);
    }
}
