package org.jiangtao.sql;

import org.jiangtao.bean.User;

import java.util.List;

/**
 * Created by mr-jiang on 15-11-27.
 */
public interface UserDao {
    public void insetUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public User selectUser(String user_email) throws Exception;

    public List<User> selectAllUser() throws Exception;
}
