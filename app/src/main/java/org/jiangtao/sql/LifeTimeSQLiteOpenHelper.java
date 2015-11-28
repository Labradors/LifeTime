package org.jiangtao.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mr-jiang on 15-11-27.
 * 数据库帮助类
 */
public class LifeTimeSQLiteOpenHelper extends SQLiteOpenHelper {

    // 数据库名称
    public static final String DB_NAME = "lifetime.db";

    // 数据库版本号
    public static final int DB_VERSION = 1;

    // 定义表名
    public static final String TAB_USER = "tab_user";

    /**
     * 构造数据库
     *
     * @param context
     */
    public LifeTimeSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "drop table if exists " + TAB_USER;
        db.execSQL(sql);

        String sql2 = "create table " + TAB_USER
                + "("
                + "user_id INTEGER NOT NULL UNIQUE, "
                + "user_email VARCHAR(50), "
                + "user_name VARCHAR(50), "
                + "user_headpicture VARCHAR(50), "
                + "user_jointime DATE, "
                + "user_sex VARCHAR(20), "
                + "user_phone VARCHAR(50), "
                + "user_password VARCHAR(50)"
                + ")";

        db.execSQL(sql2);
    }

    /**
     * 只有当版本好更新才会使用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
