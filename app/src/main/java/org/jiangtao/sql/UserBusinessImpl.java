package org.jiangtao.sql;

import android.content.Context;

import org.jiangtao.bean.User;

/**
 * Created by mr-jiang on 15-11-28.
 * 服务类
 */
public class UserBusinessImpl implements UserBusiness {
    private UserDao userDao;

    public UserBusinessImpl(Context context) {
        userDao = new UserDaoImpl(context);
    }

    @Override
    public void insertUser(User user) throws Exception {
        if (user.getUser_headpicture() != null) {
            /**
             * 保存照片，并且返回特定位置
             */

        } else {
            userDao.insetUser(user);
        }
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public User selectUser(int id) throws Exception {
        return userDao.selectUser(id);
    }
}
