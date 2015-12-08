package org.jiangtao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.CommentActivity;
import org.jiangtao.lifetime.R;
import org.jiangtao.lifetime.UserHomePageActivity;
import org.jiangtao.networkutils.ArticleOperation;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.TurnActivity;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.jiangtao.lifetime.R.id.dynamic_textview_userName;

/**
 * Created by mr-jiang
 * on 15-12-2.
 * DynamicFragment recylerView适配
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {
    public ArrayList<ArticleAllDynamic> mList;
    public Context mContext;
    public Context mCommentContext;
    private LayoutInflater mLayoutInflater;
    public static final String TAG = DynamicAdapter.class.getSimpleName();
    public static Bitmap bitmap = null;
    public DynamicAdapter.ViewHolder mHolder;
    public static boolean mHeadIsClick = true;
    public android.os.Handler handler;

    /**
     * 构造函数
     */
    public DynamicAdapter(ArrayList<ArticleAllDynamic> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public DynamicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(
                R.layout.layout_dynamic_listview, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.setOnItemClickListener(new ViewHolder.ViewHolderOnClick() {
            @Override
            public void onItemClicked(View view, int position) {
                switch (view.getId()) {
                    case R.id.profile_image_listview: {
                        Toast.makeText(view.getContext(), position + "=====", Toast.LENGTH_LONG).show();
                        openHomePage(position);
                        break;
                    }
                    case R.id.dynamic_button: {
                        attentionFuction(position);
                        break;
                    }
                    case R.id.dynamic_comment_listview: {
                        openComment(position);
                        break;
                    }
                }
            }
        });
        handlerMessage();
        return holder;
    }

    /**
     * 打开评论界面
     */
    private void openComment(int position) {
        int article_id = mList.get(position).getArticle_id();
        Intent intent = new Intent(mContext, CommentActivity.class);
        intent.putExtra("article_id", article_id);
        mContext.startActivity(intent);
    }

    private void handlerMessage() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x999: {
                        Toast.makeText(mContext, R.string.attention_success, Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 0x998: {
                        Toast.makeText(mContext, R.string.attention_fail, Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 0x997: {
                        Toast.makeText(mContext, R.string.article_network_error, Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        };
    }

    @Override
    public void onBindViewHolder(DynamicAdapter.ViewHolder holder, int position) {
        mHolder = holder;
        holder.mArticleTextView.setText(mList.get(position).getArticle_content());
        holder.mHotTextView.setText(+
                mList.get(position).getArticle_love_number() + "");
        holder.mCommentTextView.setText(+
                mList.get(position).getArticle_comment_number() + "");
        holder.mCollectionTextView.setText(
                mList.get(position).getArticle_comment_number() + "");
        holder.mUserNameTextView.setText(mList.get(position).getUser_name());
        holder.mTimeTextView.setText((mList.get(position).getArticle_time()));
        if (LifeApplication.hasNetWork) {

            LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                    mList.get(position).getArticle_image()).
                    into(holder.mArticleImageView);
            LifeApplication.picasso
                    .load(ConstantValues.getArticleImageUrl +
                            mList.get(position).getUser_headpicture())
                    .into(holder.mHeadImageCircleImageView);
        } else {
            Bitmap articleBitmap = getCacheBitmap(ConstantValues.getArticleImageUrl +
                    mList.get(position).getArticle_image());
            applyImageView(articleBitmap, holder);
            Bitmap headImageBitmap = getCacheBitmap(ConstantValues.getArticleImageUrl +
                    mList.get(position).getUser_headpicture());
            alpplyHeadImage(headImageBitmap, holder);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 获取用户的位置，并且根据位置获得article_user_id
     *
     * @param position
     */
    public void openHomePage(int position) {
        Intent intent = new Intent(mContext, UserHomePageActivity.class);
        int article_user_id = mList.get(position).getArticle_user_id();
        intent.putExtra("user_id", article_user_id);
        mContext.startActivity(intent);
    }

    /**
     * 关注功能
     *
     * @param position
     */
    public void attentionFuction(int position) {
        if (LifeApplication.isLogin) {
            if (LifeApplication.getInstance().isNetworkAvailable()) {
                int friend_user_id = LifeApplication.user_id;
                int friend_another_id = mList.get(position).getArticle_user_id();
                ArticleOperation.getInstance().new AttentionAsyncTask()
                        .execute(friend_user_id, friend_another_id);
                ArticleOperation.getInstance().interfaceInstance(new ArticleOperation.AttentionOperate() {
                    @Override
                    public void sendResult(boolean result) {
                        if (result) {
                            Message message = new Message();
                            message.what = 0x999;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 0x998;
                            handler.sendMessage(message);
                        }
                    }
                });
            } else {
                Message msg = new Message();
                msg.what = 0x997;
                handler.sendMessage(msg);
            }
        } else {
            TurnActivity.turnLoginActivity(mContext);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView mHeadImageCircleImageView;
        public TextView mUserNameTextView;
        public Button mAttentionButton;
        public TextView mTimeTextView;
        public ImageView mArticleImageView;
        public TextView mArticleTextView;
        public TextView mHotTextView;
        public TextView mCommentTextView;
        public TextView mCollectionTextView;
        public TextView mLoveTextView;
        public ViewHolderOnClick viewHolderOnClick;

        public ViewHolder(final View itemView) {
            super(itemView);
            mHeadImageCircleImageView = (CircleImageView) itemView.findViewById(
                    R.id.profile_image_listview);
            mUserNameTextView = (TextView) itemView.findViewById(dynamic_textview_userName);
            mAttentionButton = (Button) itemView.findViewById(R.id.dynamic_button);
            mTimeTextView = (TextView) itemView.findViewById(R.id.dynamic_time_listview);
            mArticleImageView = (ImageView) itemView.findViewById(R.id.dynamic_imageview);
            mArticleTextView = (TextView) itemView.findViewById(R.id.dynamic_article_content);
            mHotTextView = (TextView) itemView.findViewById(R.id.dynamic_textview_listview);
            mCommentTextView = (TextView) itemView.findViewById(R.id.dynamic_comment_listview);
            mCollectionTextView = (TextView) itemView.findViewById(R.id.dynamic_collection_listview);
            mLoveTextView = (TextView) itemView.findViewById(R.id.dynamic_love_listview);
            setOnclickListener();
        }

        public void setOnclickListener() {
            if (mHeadIsClick) {
                mHeadImageCircleImageView.setOnClickListener(this);
            }
            mAttentionButton.setOnClickListener(this);
            mArticleImageView.setOnClickListener(this);
            mCommentTextView.setOnClickListener(this);
            mCollectionTextView.setOnClickListener(this);
            mLoveTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (viewHolderOnClick != null) {
                viewHolderOnClick.onItemClicked(v, getLayoutPosition());
            }
        }

        public void setOnItemClickListener(ViewHolderOnClick viewHolderOnClick) {
            this.viewHolderOnClick = viewHolderOnClick;
        }

        public interface ViewHolderOnClick {
            void onItemClicked(View view, int position);
        }
    }

    /**
     * 根据地址获取缓存图片
     *
     * @param url
     * @return
     */
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

    /**
     * 显示文章的图片
     *
     * @param bitmap
     * @param holder
     */
    public void applyImageView(Bitmap bitmap, DynamicAdapter.ViewHolder holder) {
        holder.mArticleImageView.setImageBitmap(bitmap);
    }

    /**
     * 显示头像
     *
     * @param bitmap
     * @param holder
     */
    public void alpplyHeadImage(Bitmap bitmap, DynamicAdapter.ViewHolder holder) {
        holder.mHeadImageCircleImageView.setImageBitmap(bitmap);
    }

    public void refresh(ArrayList<ArticleAllDynamic> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
