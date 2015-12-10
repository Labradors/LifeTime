package org.jiangtao.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jiangtao.bean.User;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr-jiang on 15-11-27.
 * 用户保存用户数据
 */
public class UserDaoImpl implements UserDao {
    public LifeTimeSQLiteOpenHelper lifeTimeSQLiteOpenHelper;
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
     * @param user_email
     * @return
     * @throws Exception
     */
    @Override
    public User selectUser(String user_email) throws Exception {
        User user = new User();
        String sql = "SELECT * FROM " + LifeTimeSQLiteOpenHelper.TAB_USER + " WHERE user_email = ?";
        LogUtils.d(TAG, "sql表达式是否正确：" + sql);
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        lifeTimeSQLiteOpenHelper.onOpen(db);
        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[]{user_email});
            if (c != null) {
                while (c.moveToNext()) {
                    user.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                    user.setUser_email(c.getString(c.getColumnIndex("user_email")));
                    user.setUser_name(c.getString(c.getColumnIndex("user_name")));
//                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//                    user.setUser_jointime(format.parse(c.getString(c.getColumnIndex("user_jointime"))));
                    user.setUser_jointime(null);
                    user.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
                    user.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                    user.setUser_password(c.getString(c.getColumnIndex("user_sex")));
                    user.setUser_phone(c.getString(c.getColumnIndex("user_phone")));
                }
            } else {
                LogUtils.d(TAG, "没有取出数据");
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

    /**
     * 查询除所有的用户信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<User> selectAllUser() throws Exception {
        ArrayList<User> userArrayList = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.TAB_USER;
        lifeTimeSQLiteOpenHelper.onOpen(db);
        Cursor c = null;
        try {
            c = db.rawQuery(sql, null);
            if (c != null) {
                while (c.moveToNext()) {
                    User user = new User();
                    user.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                    user.setUser_email(c.getString(c.getColumnIndex("user_email")));
                    user.setUser_name(c.getString(c.getColumnIndex("user_name")));
//                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//                    user.setUser_jointime(format.parse(c.getString(c.getColumnIndex("user_jointime"))));
                    user.setUser_jointime(null);
                    user.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
                    user.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                    user.setUser_password(c.getString(c.getColumnIndex("user_sex")));
                    user.setUser_phone(c.getString(c.getColumnIndex("user_phone")));
                    LogUtils.d(TAG, user.toString());
                    userArrayList.add(user);
                }
            } else {
                LogUtils.d(TAG, "没有取出数据");
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return userArrayList;
    }

    /**
     * 清空表中数据
     *
     * @throws Exception delete from TableName;
     */
    @Override
    public void deleteTable() throws Exception {
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "delete from " + LifeTimeSQLiteOpenHelper.TAB_USER;
        db.execSQL(sql);
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

}
