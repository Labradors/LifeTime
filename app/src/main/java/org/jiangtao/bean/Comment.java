package org.jiangtao.bean;

/**
 * Created by mr-jiang on 15-12-8.
 * comment bean
 */
public class Comment {
    private int comment_id;
    private int comment_user_id;
    private String comment_user_headImage;
    private String comment_user_name;
    private String comment_content;

    public Comment() {
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public String getComment_user_headImage() {
        return comment_user_headImage;
    }

    public void setComment_user_headImage(String comment_user_headImage) {
        this.comment_user_headImage = comment_user_headImage;
    }

    public String getComment_user_name() {
        return comment_user_name;
    }

    public void setComment_user_name(String comment_user_name) {
        this.comment_user_name = comment_user_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", comment_user_id=" + comment_user_id +
                ", comment_user_headImage='" + comment_user_headImage + '\'' +
                ", comment_user_name='" + comment_user_name + '\'' +
                ", comment_content='" + comment_content + '\'' +
                '}';
    }
}
