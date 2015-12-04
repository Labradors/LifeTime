package org.jiangtao.sql;

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
        String sql = "INSERT INTO " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE
                + "  VALUES (?,?,?,?,?,?,?,?,?)";
        LogUtils.d(TAG, "插入sql语句");
        LogUtils.d(TAG, sql);
        for (int i = 0; i < articleAllDynamics.size(); i++) {
            LogUtils.d(TAG, "打印次数");
            ArticleAllDynamic articleAllDynamic = articleAllDynamics.get(i);
            db.execSQL(sql, new Object[]{articleAllDynamic.getUser_name(),
                    articleAllDynamic.getUser_headpicture(), articleAllDynamic.getArticle_id(),
                    articleAllDynamic.getArticle_user_id(), articleAllDynamic.getArticle_time(),
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
        String sql = "select * from " + LifeTimeSQLiteOpenHelper.DYNAMIC_ARTICLE;
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
//                String myDate = c.getString(c.getColumnIndex("article_time"));
//                Timestamp timestamp = JavaTimeToSqlite.parseTimestamp(myDate);
                dynamic.setArticle_time(null);
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
}
