package org.jiangtao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Friend;
import org.jiangtao.lifetime.R;
import org.jiangtao.lifetime.UserHomePageActivity;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-jiang on 15-12-10.
 * 朋友界面Adapter
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Friend> mFriendLists;
    public ViewHolder.ViewHolderOnClick mViewHolderOnClick;
    public static Bitmap bitmap = null;
    private static final String TAG = FriendAdapter.class.getSimpleName();

    public FriendAdapter(Context mContext, ArrayList<Friend> mFriendLists) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(this.mContext);
        if (mFriendLists.size() == 0 || mFriendLists == null) {
            this.mFriendLists = new ArrayList<>();
        } else {
            this.mFriendLists = mFriendLists;
        }

    }

    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_friend, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setOnItemClickListener(new ViewHolder.ViewHolderOnClick() {
            @Override
            public void onItemClicked(View v, int position) {
                switch (v.getId()) {
                    case R.id.layout_circle_headImage: {
                        LogUtils.d(TAG, "你好");
                        openHomePage(position);
                        break;
                    }
                }
            }
        });
        return holder;
    }

    public void openHomePage(int position) {
        Intent intent = new Intent(mContext, UserHomePageActivity.class);
        int article_user_id = mFriendLists.get(position).getUser_id();
        intent.putExtra("user_id", article_user_id);
        mContext.startActivity(intent);
    }

    public Bitmap getCacheBitmap(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = LifeApplication.picasso.load(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if (bitmap != null) {
            return bitmap;
        }
        return null;
    }

    public void alpplyHeadImage(Bitmap bitmap, FriendAdapter.ViewHolder holder) {
        holder.mCircleImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBindViewHolder(FriendAdapter.ViewHolder holder, int position) {
        Friend friend = mFriendLists.get(position);
        holder.mUserNameTextView.setText(friend.getUser_name());
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                    friend.getUser_headpicture()).into(holder.mCircleImageView);
        } else {
            Bitmap headImageBitmap = getCacheBitmap(ConstantValues.getArticleImageUrl +
                    mFriendLists.get(position).getUser_headpicture());
            alpplyHeadImage(headImageBitmap, holder);
        }

    }

    @Override
    public int getItemCount() {
        return mFriendLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public CircleImageView mCircleImageView;
        public TextView mUserNameTextView;
        private ViewHolderOnClick mViewHolderOnClick;

        public ViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView)
                    itemView.findViewById(R.id.layout_circle_headImage);
            mUserNameTextView = (TextView) itemView.findViewById
                    (R.id.layout_textview_username);
            mCircleImageView.setOnClickListener(this);
        }


        public void setOnItemClickListener(ViewHolderOnClick viewHolderOnClick) {
            mViewHolderOnClick = viewHolderOnClick;
        }

        @Override
        public void onClick(View v) {
            if (mViewHolderOnClick != null) {
                mViewHolderOnClick.onItemClicked(v, getLayoutPosition());
            }
        }

        public interface ViewHolderOnClick {
            void onItemClicked(View view, int position);
        }
    }
}
