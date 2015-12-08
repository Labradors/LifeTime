package org.jiangtao.networkutils;

import android.os.AsyncTask;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Comment;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-8.
 */
public class CommentOperater {
    private static CommentOperater mCommentOperater;
    private static final String TAG = CommentOperater.class.getSimpleName();
    public CommentCallBack mCommentCallBack;

    private CommentOperater() {
    }

    public static CommentOperater getInstance() {
        if (mCommentOperater == null) {
            mCommentOperater = new CommentOperater();
        }
        return mCommentOperater;
    }

    /**
     * 获取文章评论数据
     */
    public class CommentAsyncTask extends AsyncTask<Integer, Void, ArrayList<Comment>> {

        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {
            int article_id = params[0];
            RequestBody body = new FormEncodingBuilder()
                    .add("article_id", article_id + "").build();
            Callback callback = new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    LogUtils.d(TAG, "请求失败");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String commentValues = response.body().string();
                    ArrayList<Comment> commentsList = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(commentValues);
                        if (array != null && array.length() != 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Comment comment = new Comment();
                                comment.setComment_id(object.getInt("comment_id"));
                                comment.setComment_user_id(object.getInt("comment_user_id"));
                                comment.setComment_user_headImage(object.getString("comment_user_headImage"));
                                comment.setComment_user_name(object.getString("comment_user_name"));
                                comment.setComment_content(object.getString("comment_content"));
                                commentsList.add(comment);
                            }
                            mCommentCallBack.sendCommentList(commentsList);
                        } else {
                            mCommentCallBack.sendCommentList(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            try {
                LifeApplication.getNetWorkResponse(callback, body, ConstantValues.CommentOperater);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface CommentCallBack {
        public void sendCommentList(ArrayList<Comment> commentsList);
    }

    public void CommentCallBackInstance(CommentCallBack commentCallBack) {
        mCommentCallBack = commentCallBack;
    }
}
