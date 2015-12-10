package org.jiangtao.sql;

import android.content.Context;

import org.jiangtao.bean.Friend;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-10.
 * 业务层
 */
public class FriendBusinessImpl implements FriendBusiness {
    FriendDaoImpl friendDao;

    public FriendBusinessImpl(Context context) {
        friendDao = new FriendDaoImpl(context);
    }

    @Override
    public ArrayList<Friend> selectAllFriend() throws Exception {
        return friendDao.selectAllFriend();
    }

    @Override
    public void insertFriend(ArrayList<Friend> friends) throws Exception {
        friendDao.insertFriend(friends);
    }

    @Override
    public boolean deleteAllFriend() throws Exception {
        return friendDao.deleteAllFriend();
    }

    @Override
    public boolean deleteFriendAccordingToID(int user_id) throws Exception {
        return  friendDao.deleteFriendAccordingToID(user_id);
    }
}
