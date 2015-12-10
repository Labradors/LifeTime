package org.jiangtao.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jiangtao.utils.LogUtils;

/**
 * Created by mr-jiang on 15-11-27.
 * 数据库帮助类
 */
public class LifeTimeSQLiteOpenHelper extends SQLiteOpenHelper {

    //打印
    public static final String TAG = LifeTimeSQLiteOpenHelper.class.getSimpleName();

    // 数据库名称
    public static final String DB_NAME = "lifetime.db";

    // 数据库版本号
    public static final int DB_VERSION = 1;

    // 定义表名
    public static final String TAB_USER = "tab_user";
    //动态文章表
    public static final String DYNAMIC_ARTICLE = "dynamic_article";
    //喜欢文章表
    public static final String LOVE_ARTICLE = "love_article";
    //评论文章表
    public static final String COMMENT_ARTICLE = "comment_article";
    //所有的朋友表
    public static final String TABLE_FRIEND = "tab_friend";

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
        String sql1 = "drop table if exists " + DYNAMIC_ARTICLE;
        String friend_sql = "drop table if exists " + TABLE_FRIEND;
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(friend_sql);

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
        String dynamic_article = "create table " + DYNAMIC_ARTICLE
                + "("
                + "user_name VARCHAR(50) NOT NULL, "
                + "user_headpicture VARCHAR(100), "
                + "article_id INTEGER NOT NULL PRIMARY KEY, "
                + "article_user_id INTEGER, "
                + "article_time VARCHAR(50), "
                + "article_content TEXT, "
                + "article_image VARCHAR(100), "
                + "article_love_number INTEGER, "
                + "article_comment_number INTEGER"
                + ")";
        String sql_friend = "create table " + TABLE_FRIEND
                + "("
                + "user_id INTEGER NOT NULL PRIMARY KEY, "
                + "user_name VARCHAR(100) NOT NULL, "
                + "user_headpicture VARCHAR(100) "
                + ")";
        db.execSQL(sql2);
        db.execSQL(dynamic_article);
        db.execSQL(sql_friend);
        LogUtils.d(TAG, dynamic_article);


    }

    /**
     * 只有当版本更新才会使用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 查看表存在
     *
     * @param tableName
     * @return
     */
    public boolean tabbleIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return result;
    }
}
