package org.jiangtao.sql;

import org.jiangtao.bean.User;

/**
 * Created by mr-jiang on 15-11-28.
 */
public interface UserBusiness {
    public void insertUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public User selectUser(String user_email) throws Exception;
}
