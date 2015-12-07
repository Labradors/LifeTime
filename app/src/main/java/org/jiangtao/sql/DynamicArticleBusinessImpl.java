package org.jiangtao.sql;

import android.content.Context;

import org.jiangtao.bean.ArticleAllDynamic;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-4.
 */
public class DynamicArticleBusinessImpl implements DynamicArticleBusiness {
    public DynamicArticleDao dynamicArticleDao;

    public DynamicArticleBusinessImpl(Context context) {
        dynamicArticleDao = new DynamicArticleDaoImpl(context);
    }

    @Override
    public int insertDynamicArticles(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception {
        int insert = dynamicArticleDao.insertDynamic(articleAllDynamics);
        return insert;
    }

    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticles() throws Exception {
        return dynamicArticleDao.getDynamicArticle();
    }

    @Override
    public int getDynamicMaxID() throws Exception {
        return dynamicArticleDao.getMaxDynamicArticleID();
    }

    @Override
    public ArrayList<ArticleAllDynamic> getDynamicFromID(int id) throws Exception {
        return dynamicArticleDao.getDynamicArticleFromID(id);
    }
}
