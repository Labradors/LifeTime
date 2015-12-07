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


}
