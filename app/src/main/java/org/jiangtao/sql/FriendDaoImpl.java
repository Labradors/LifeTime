package org.jiangtao.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jiangtao.bean.Friend;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-10.
 * 朋友表实现
 */
public class FriendDaoImpl implements FriendDao {
    LifeTimeSQLiteOpenHelper openHelper;
    private static final String TAG = FriendDaoImpl.class.getSimpleName();
    private Context mContext;

    public FriendDaoImpl(Context context) {
        mContext = context;
        openHelper = new LifeTimeSQLiteOpenHelper(mContext);
    }

    /**
     * 查询所有朋友
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Friend> selectAllFriend() throws Exception {
        ArrayList<Friend> mFriendList = new ArrayList<>();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.TABLE_FRIEND;
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Friend friend = new Friend();
            friend.setUser_id(c.getInt(c.getColumnIndex("user_id")));
            friend.setUser_name(c.getString(c.getColumnIndex("user_name")));
            friend.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
            mFriendList.add(friend);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return mFriendList;
    }

    /**
     * 插入朋友表
     *
     * @param friends
     * @throws Exception
     */
    @Override
    public void insertFriend(ArrayList<Friend> friends) throws Exception {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        for (Friend friend : friends) {
            ContentValues values = new ContentValues();
            values.put("user_id", friend.getUser_id());
            values.put("user_name", friend.getUser_name());
            values.put("user_headpicture", friend.getUser_headpicture());
            db.replace(LifeTimeSQLiteOpenHelper.TABLE_FRIEND, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 删除表中所有数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteAllFriend() throws Exception {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String sql = "delete from " + LifeTimeSQLiteOpenHelper.TABLE_FRIEND;
        db.execSQL(sql);
        if (db != null && db.isOpen()) {
            db.close();
        }
        return true;
    }

    /**
     * 根据值删除表中的数据
     *
     * @param user_id
     * @throws Exception
     */
    @Override
    public boolean deleteFriendAccordingToID(int user_id) throws Exception {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String sql = "delete from " + LifeTimeSQLiteOpenHelper.TABLE_FRIEND +
                " where user_id = " + user_id;
        db.execSQL(sql);
        if (db != null && db.isOpen()) {
            db.close();
        }
        return false;
    }
}
