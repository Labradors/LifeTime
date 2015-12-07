package org.jiangtao.sql;

import org.jiangtao.bean.ArticleAllDynamic;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-4.
 */
public interface DynamicArticleDao {
    public int insertDynamic(ArrayList<ArticleAllDynamic> articleAllDynamics) throws Exception;

    public ArrayList<ArticleAllDynamic> getDynamicArticle() throws Exception;

    public int getMaxDynamicArticleID() throws Exception;

    public ArrayList<ArticleAllDynamic> getDynamicArticleFromID(int user_id) throws Exception;
}
