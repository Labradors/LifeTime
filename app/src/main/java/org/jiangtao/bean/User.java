package org.jiangtao.bean;

import java.util.Date;

/**
 * 用户基础类
 *
 * @author mr-jiang
 */
public class User {
    private int user_id;
    private String user_email;
    private String user_name;
    private String user_headpicture;
    private Date user_jointime;
    private String user_sex;
    private String user_phone;
    private String user_password;


    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
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

    public Date getUser_jointime() {
        return user_jointime;
    }

    public void setUser_jointime(Date user_jointime) {
        this.user_jointime = user_jointime;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", user_email=" + user_email
                + ", user_name=" + user_name + ", user_headpicture="
                + user_headpicture + ", user_jointime=" + user_jointime
                + ", user_sex=" + user_sex + ", user_phone=" + user_phone
                + ", user_password=" + user_password + "]";
    }
}
