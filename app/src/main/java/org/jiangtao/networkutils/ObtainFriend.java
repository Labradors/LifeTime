package org.jiangtao.networkutils;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Friend;
import org.jiangtao.sql.FriendBusinessImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-10.
 */
public class ObtainFriend {
    private static ObtainFriend obtainFriend;
    public static final String TAG = ObtainFriend.class.getSimpleName();
    private FriendBusinessImpl friendBusiness;
    public Context mContext;
    public ArrayList<Friend> mFriendLists = new ArrayList<>();
    public int mUser_id;


    private ObtainFriend() {
    }

    public static ObtainFriend getInstance() {
        if (obtainFriend == null) {
            obtainFriend = new ObtainFriend();
        }
        return obtainFriend;
    }

    /**
     * 请求数据库中朋友的数据
     *
     * @param context
     * @param user_id
     */
    public void WriteFriendData(Context context, int user_id) {
        mUser_id = user_id;
        mContext = context;
        friendBusiness = new FriendBusinessImpl(mContext);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                RequestBody body = new FormEncodingBuilder()
                        .add("user_id", String.valueOf(mUser_id))
                        .build();
                LogUtils.d(TAG, "12345567");
                Callback callback = new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        LogUtils.d(TAG, "请求失败");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String allFriends = response.body().string();
                        try {
                            JSONArray array = new JSONArray(allFriends);
                            if (array.length() != 0 && array != null) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    Friend friend = new Friend();
                                    friend.setUser_id(object.getInt("user_id"));
                                    friend.setUser_name(object.getString("user_name"));
                                    friend.setUser_headpicture(object.getString("user_headpicture"));
                                    mFriendLists.add(friend);
                                    LogUtils.d(TAG, ">>>>>>" + friend.toString());
                                }
                                friendBusiness.deleteAllFriend();
                                friendBusiness.insertFriend(mFriendLists);
                            } else {
                                friendBusiness.deleteAllFriend();
                                LogUtils.d(TAG,"是否进入删除");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    LifeApplication.getNetWorkResponse(callback, body, ConstantValues.allFriendUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
