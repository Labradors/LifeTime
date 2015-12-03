package org.jiangtao.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mr-jiang
 * on 15-12-2.
 * 动态fragment基类
 */
public class ArticleAllDynamic implements Serializable {
    private String user_name;
    private String user_headpicture;
    private int article_id;
    private int article_user_id;
    private Timestamp article_time;
    private String article_content;
    private String article_image;
    private int article_love_number;
    private int article_comment_number;

    public ArticleAllDynamic() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_headpicture() {
        return user_headpicture;
    }

    public void setUser_headpicture(String user_headpicture) {
        this.user_headpicture = user_headpicture;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getArticle_user_id() {
        return article_user_id;
    }

    public void setArticle_user_id(int article_user_id) {
        this.article_user_id = article_user_id;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public String getArticle_image() {
        return article_image;
    }

    public void setArticle_image(String article_image) {
        this.article_image = article_image;
    }

    public int getArticle_love_number() {
        return article_love_number;
    }

    public void setArticle_love_number(int article_love_number) {
        this.article_love_number = article_love_number;
    }

    public int getArticle_comment_number() {
        return article_comment_number;
    }

    public void setArticle_comment_number(int article_comment_number) {
        this.article_comment_number = article_comment_number;
    }

    public Timestamp getArticle_time() {
        return article_time;
    }

    public void setArticle_time(Timestamp article_time) {
        this.article_time = article_time;
    }

    @Override
    public String toString() {
        return "IndexDynamic [user_name=" + user_name + ", user_headpicture="
                + user_headpicture + ", article_id=" + article_id
                + ", article_user_id=" + article_user_id + ", article_time="
                + article_time + ", article_content=" + article_content
                + ", article_image=" + article_image + ", article_love_number="
                + article_love_number + ", article_comment_number="
                + article_comment_number + "]";
    }
}
