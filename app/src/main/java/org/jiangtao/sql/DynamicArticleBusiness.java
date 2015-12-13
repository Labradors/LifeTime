package org.jiangtao.sql;

import org.jiangtao.bean.ArticleAllDynamic;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-4.
 */
public interface DynamicArticleBusiness {

    public int insertDynamicArticles(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception;


    public ArrayList<ArticleAllDynamic> getDynamicArticles() throws Exception;

    public int getDynamicMaxID() throws Exception;

    public ArrayList<ArticleAllDynamic> getDynamicFromID(int id) throws Exception;

    public void updateArticle(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception;

    public ArrayList<ArticleAllDynamic> getFriendArticle() throws Exception;

    public ArrayList<ArticleAllDynamic> getDynamicArticleByComment() throws Exception;

    public ArrayList<ArticleAllDynamic> getDynamicArticleByLove() throws Exception;

    public ArrayList<String> getGallery(int user_id) throws Exception;

    public boolean deleteArticleFromArticleID(int article_id) throws Exception;


}
