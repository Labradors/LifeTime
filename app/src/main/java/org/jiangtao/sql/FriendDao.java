package org.jiangtao.sql;

import org.jiangtao.bean.Friend;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-10.
 * 朋友列表的操作
 */
public interface FriendDao {
    public ArrayList<Friend> selectAllFriend() throws Exception;

    public void insertFriend(ArrayList<Friend> friends) throws Exception;

    public boolean deleteAllFriend() throws Exception;

    public boolean  deleteFriendAccordingToID(int user_id) throws Exception;
}
