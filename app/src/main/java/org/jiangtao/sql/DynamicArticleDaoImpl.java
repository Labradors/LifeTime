package org.jiangtao.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-4.
 */
public class DynamicArticleDaoImpl implements DynamicArticleDao {

    public LifeTimeSQLiteOpenHelper lifeTimeSQLiteOpenHelper;
    public int returnInsert;
    private static final String TAG = DynamicArticleDaoImpl.class.getSimpleName();

    public DynamicArticleDaoImpl(Context context) {
        lifeTimeSQLiteOpenHelper = new LifeTimeSQLiteOpenHelper(context);
    }

    /**
     * 从服务器获取数据后直接添加到本地数据库
     *
     * @param articleAllDynamics
     * @throws Exception
     */
    @Override
    public int insertDynamic(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception {
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "INSERT INTO " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE + "(user_name,user_headpicture,article_id,article_user_id," +
                " article_time,article_content,article_image,article_love_number," +
                " article_comment_number) VALUES (?,?,?,?,?,?,?,?,?)";
        LogUtils.d(TAG, "插入sql语句");
        LogUtils.d(TAG, sql);
        for (int i = 0; i < articleAllDynamics.size(); i++) {
            LogUtils.d(TAG, "打印次数");
            ArticleAllDynamic articleAllDynamic = articleAllDynamics.get(i);
            db.execSQL(sql, new Object[]{articleAllDynamic.getUser_name(),
                    articleAllDynamic.getUser_headpicture(), articleAllDynamic.getArticle_id(),
                    articleAllDynamic.getArticle_user_id(), articleAllDynamic.getArticle_time().toString(),
                    articleAllDynamic.getArticle_content(), articleAllDynamic.getArticle_image(),
                    articleAllDynamic.getArticle_love_number(),
                    articleAllDynamic.getArticle_comment_number()});
            returnInsert = i;
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
        return this.returnInsert;
    }

    /**
     * 查询所有的动态
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticle() throws Exception {
        ArrayList<ArticleAllDynamic> articleAllDynamics = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE + " ORDER BY article_id DESC";
        LogUtils.d(TAG, sql);
        Cursor c = null;
        c = db.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                LogUtils.d(TAG, "进入查找");
                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                dynamic.setUser_name(c.getString(c.getColumnIndex("user_name")));
                dynamic.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                dynamic.setArticle_id(c.getInt(c.getColumnIndex("article_id")));
                dynamic.setArticle_user_id(c.getInt(c.getColumnIndex("article_user_id")));
                dynamic.setArticle_time(c.getString(c.getColumnIndex("article_time")));
                dynamic.setArticle_content(c.getString(c.getColumnIndex("article_content")));
                dynamic.setArticle_image(c.getString(c.getColumnIndex("article_image")));
                dynamic.setArticle_love_number(c.getInt(c.getColumnIndex("article_love_number")));
                dynamic.setArticle_comment_number(c.getInt(c.getColumnIndex("article_comment_number")));
                articleAllDynamics.add(dynamic);
                LogUtils.d(TAG, ">>>>>>>>>>>>>>>" + dynamic.toString());
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return articleAllDynamics;
    }

    /**
     * 根据评论排序
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticleByComment() throws Exception {
        ArrayList<ArticleAllDynamic> articleAllDynamics = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE + " ORDER BY article_comment_number DESC";
        LogUtils.d(TAG, sql);
        Cursor c = null;
        c = db.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                LogUtils.d(TAG, "进入查找");
                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                dynamic.setUser_name(c.getString(c.getColumnIndex("user_name")));
                dynamic.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                dynamic.setArticle_id(c.getInt(c.getColumnIndex("article_id")));
                dynamic.setArticle_user_id(c.getInt(c.getColumnIndex("article_user_id")));
                dynamic.setArticle_time(c.getString(c.getColumnIndex("article_time")));
                dynamic.setArticle_content(c.getString(c.getColumnIndex("article_content")));
                dynamic.setArticle_image(c.getString(c.getColumnIndex("article_image")));
                dynamic.setArticle_love_number(c.getInt(c.getColumnIndex("article_love_number")));
                dynamic.setArticle_comment_number(c.getInt(c.getColumnIndex("article_comment_number")));
                articleAllDynamics.add(dynamic);
                LogUtils.d(TAG, ">>>>>>>>>>>>>>>" + dynamic.toString());
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return articleAllDynamics;
    }

    /**
     * 根据喜欢排序
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticleByLove() throws Exception {
        ArrayList<ArticleAllDynamic> articleAllDynamics = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE + " ORDER BY article_love_number DESC";
        LogUtils.d(TAG, sql);
        Cursor c = null;
        c = db.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                LogUtils.d(TAG, "进入查找");
                ArticleAllDynamic dynamic = new ArticleAllDynamic();
                dynamic.setUser_name(c.getString(c.getColumnIndex("user_name")));
                dynamic.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
                dynamic.setArticle_id(c.getInt(c.getColumnIndex("article_id")));
                dynamic.setArticle_user_id(c.getInt(c.getColumnIndex("article_user_id")));
                dynamic.setArticle_time(c.getString(c.getColumnIndex("article_time")));
                dynamic.setArticle_content(c.getString(c.getColumnIndex("article_content")));
                dynamic.setArticle_image(c.getString(c.getColumnIndex("article_image")));
                dynamic.setArticle_love_number(c.getInt(c.getColumnIndex("article_love_number")));
                dynamic.setArticle_comment_number(c.getInt(c.getColumnIndex("article_comment_number")));
                articleAllDynamics.add(dynamic);
                LogUtils.d(TAG, ">>>>>>>>>>>>>>>" + dynamic.toString());
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return articleAllDynamics;
    }

    @Override
    public int getMaxDynamicArticleID() throws Exception {
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getReadableDatabase();
        String sql = "select MAX(article_id) AS article_id FROM "
                + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE;
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex("article_id"));
        LogUtils.d(TAG, id + "********");
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return id;
    }

    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticleFromID(int user_id) throws Exception {
        ArrayList<ArticleAllDynamic> dynamics = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE +
                " where article_user_id=" + user_id +
                " order by article_id desc";
        LogUtils.d(TAG, sql);
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            ArticleAllDynamic dynamic = new ArticleAllDynamic();
            dynamic.setUser_name(c.getString(c.getColumnIndex("user_name")));
            dynamic.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
            dynamic.setArticle_id(c.getInt(c.getColumnIndex("article_id")));
            dynamic.setArticle_user_id(c.getInt(c.getColumnIndex("article_user_id")));
            dynamic.setArticle_content(c.getString(c.getColumnIndex("article_content")));
            dynamic.setArticle_image(c.getString(c.getColumnIndex("article_image")));
            dynamic.setArticle_time(c.getString(c.getColumnIndex("article_time")));
            dynamic.setArticle_comment_number(c.getInt(c.getColumnIndex("article_comment_number")));
            dynamic.setArticle_love_number(c.getInt(c.getColumnIndex("article_love_number")));
            dynamics.add(dynamic);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return dynamics;
    }

    /**
     * 更新数据库
     *
     * @param articleAllDynamics
     * @throws Exception
     */
    @Override
    public void updateDynamic(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception {
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < articleAllDynamics.size(); i++) {
            ArticleAllDynamic articleAllDynamic = articleAllDynamics.get(i);
            ContentValues values = new ContentValues();
            values.put("article_id", articleAllDynamic.getArticle_id());
            values.put("user_name", articleAllDynamic.getUser_name());
            values.put("user_headpicture", articleAllDynamic.getUser_headpicture());
            values.put("article_user_id", articleAllDynamic.getArticle_user_id());
            values.put("article_time", articleAllDynamic.getArticle_time());
            values.put("article_content", articleAllDynamic.getArticle_content());
            values.put("article_image", articleAllDynamic.getArticle_image());
            values.put("article_love_number", articleAllDynamic.getArticle_love_number());
            values.put("article_comment_number", articleAllDynamic.getArticle_comment_number());
            db.replace(LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE, null,
                    values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * 查询朋友的文章
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<ArticleAllDynamic> getFriendArticle() throws Exception {
        ArrayList<ArticleAllDynamic> dynamics = new ArrayList<>();
        SQLiteDatabase db = lifeTimeSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE +
                "," + LifeTimeSQLiteOpenHelper.TABLE_FRIEND + " where article_user_id= user_id order by article_id desc";
        LogUtils.d(TAG, sql);
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            ArticleAllDynamic dynamic = new ArticleAllDynamic();
            dynamic.setUser_name(c.getString(c.getColumnIndex("user_name")));
            dynamic.setUser_headpicture(c.getString(c.getColumnIndex("user_headpicture")));
            dynamic.setArticle_id(c.getInt(c.getColumnIndex("article_id")));
            dynamic.setArticle_user_id(c.getInt(c.getColumnIndex("article_user_id")));
            dynamic.setArticle_content(c.getString(c.getColumnIndex("article_content")));
            dynamic.setArticle_image(c.getString(c.getColumnIndex("article_image")));
            dynamic.setArticle_time(c.getString(c.getColumnIndex("article_time")));
            dynamic.setArticle_comment_number(c.getInt(c.getColumnIndex("article_comment_number")));
            dynamic.setArticle_love_number(c.getInt(c.getColumnIndex("article_love_number")));
            dynamics.add(dynamic);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        return dynamics;
    }
}
