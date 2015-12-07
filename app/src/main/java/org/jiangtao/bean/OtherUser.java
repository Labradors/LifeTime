package org.jiangtao.bean;

/**
 * Created by mr-jiang on 15-12-7.
 */
public class OtherUser {
    private int user_id;
    private String user_name;
    private String user_headpicture;
    private String user_time;
    private String user_sex;

    public OtherUser() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getUser_time() {
        return user_time;
    }

    public void setUser_time(String user_time) {
        this.user_time = user_time;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    @Override
    public String toString() {
        return "OtherUser{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_headpicture='" + user_headpicture + '\'' +
                ", user_time='" + user_time + '\'' +
                ", user_sex='" + user_sex + '\'' +
                '}';
    }
}
