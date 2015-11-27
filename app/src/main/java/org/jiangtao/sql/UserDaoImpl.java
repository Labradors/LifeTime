package org.jiangtao.sql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jiangtao.bean.User;
import org.jiangtao.utils.LogUtils;

import java.text.SimpleDateFormat;

/**
 * Created by mr-jiang on 15-11-27.
 * 用户保存用户数据
 */
public class UserDaoImpl implements UserDao {
    private LifeTimeSQLiteOpenHelper lifeTimeSQLiteOpenHelper;
    private static final String TAG = UserDaoImpl.class.getSimpleName();

    public UserDaoImpl(Context context) {
        lifeTimeSQLiteOpenHelper = new LifeTimeSQLiteOpenHelper(context);
    }

    /**
     * 插入数据
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void insetUser(User user) throws Exception {
        LogUtils.d(TAG, user.getUser_email());
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "INSERT INTO " + LifeTimeSQLiteOpenHelper.TAB_USER
                + "  VALUES (?,?,?,?,?,?,?,?)";
        LogUtils.d(TAG, sql);
        try {
            db.execSQL(sql, new Object[]{user.getUser_id(), user.getUser_email(),
                    user.getUser_name(), user.getUser_headpicture(), user.getUser_jointime(),
                    user.getUser_sex(), user.getUser_phone(), user.getUser_password()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * 更新数据
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUser(User user) throws Exception {
        LogUtils.d(TAG, user.getUser_email());
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "UPDATE " + LifeTimeSQLiteOpenHelper.TAB_USER
                + " SET user_headpicture = ? where user_id = ?";
        LogUtils.d(TAG, sql);
        try {
            db.execSQL(sql, new Object[]{user.getUser_headpicture(), user.getUser_id()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * 查询用户的方法
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public User selectUser(int id) throws Exception {
        User user = null;
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM " + LifeTimeSQLiteOpenHelper.TAB_USER + " WHERE user_id = ?";
        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[]{String.valueOf(id)});
            while (c.moveToNext()) {
                user = new User();
                user.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                user.setUser_email(c.getString(c.getColumnIndex("user_email")));
                user.setUser_name(c.getString(c.getColumnIndex("user_name")));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                user.setUser_jointime(format.parse(c.getString(c.getColumnIndex("user_jointime"))));
                user.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
                user.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                user.setUser_password(c.getString(c.getColumnIndex("user_sex")));
                user.setUser_phone(c.getString(c.getColumnIndex("user_phone")));
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return user;
    }
}
