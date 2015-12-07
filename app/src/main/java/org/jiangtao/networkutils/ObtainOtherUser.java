package org.jiangtao.networkutils;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.OtherUser;
import org.jiangtao.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mr-jiang on 15-12-7.
 */
public class ObtainOtherUser {
    private static final String TAG = ObtainOtherUser.class.getSimpleName();

    public ObtainOtherUser() {
    }

    public static OtherUser getOtherUserInfo(int id, String url) {

        OtherUser user = new OtherUser();
        Request request = new Request.Builder().url(url + "?user_id=" + id).build();
        try {
            Response response = LifeApplication.mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String user_info = response.body().string();
                JSONObject object_user = new JSONObject(user_info);
                JSONObject object = object_user.getJSONObject("user");
                user.setUser_id(object.getInt("user_id"));
                user.setUser_headpicture(object.getString("user_headpicture"));
                user.setUser_name(object.getString("user_name"));
                user.setUser_sex(object.getString("user_sex"));
                user.setUser_time(object.getString("user_time"));
                LogUtils.d(TAG, object.toString());
                return user;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
