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

    @Override
    public void updateArticle(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception {
        dynamicArticleDao.updateDynamic(articleAllDynamics);
    }

    @Override
    public ArrayList<ArticleAllDynamic> getFriendArticle() throws Exception {
        return dynamicArticleDao.getFriendArticle();
    }

    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticleByComment() throws Exception {
        return dynamicArticleDao.getDynamicArticleByComment();
    }

    @Override
    public ArrayList<ArticleAllDynamic> getDynamicArticleByLove() throws Exception {
        return dynamicArticleDao.getDynamicArticleByLove();
    }

    @Override
    public boolean deleteArticleFromArticleID(int article_id) throws Exception {
        return dynamicArticleDao.deleteArticleFromArticleID(article_id);
    }

    @Override
    public ArrayList<String> getGallery(int user_id) throws Exception {
        return dynamicArticleDao.getGallery(user_id);
    }
}
