package org.jiangtao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.R;
import org.jiangtao.networkutils.RequestArticleData;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

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
    private LayoutInflater mLayoutInflater;
    public static final String TAG = DynamicAdapter.class.getSimpleName();

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
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DynamicAdapter.ViewHolder holder, int position) {
        LogUtils.d(TAG, "是否进入这个界面");
        holder.mArticleTextView.setText(mList.get(position).getArticle_content());
        holder.mHotTextView.setText("热度" +
                String.valueOf(mList.get(position).getArticle_love_number()));
        holder.mUserNameTextView.setText(mList.get(position).getUser_name());
        holder.mArticleImageView.setImageBitmap(
                RequestArticleData.getInstance().getBitmap(ConstantValues
                        .getArticleImageUrl, mList.get(position).getArticle_image())
        );
        holder.mHeadImageCircleImageView.setImageBitmap(RequestArticleData.getInstance().
                getBitmap(ConstantValues.getArticleImageUrl, mList.get(position).getUser_headpicture()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView mHeadImageCircleImageView;
        public TextView mUserNameTextView;
        public Button mAttentionButton;
        public ImageView mArticleImageView;
        public TextView mArticleTextView;
        public TextView mHotTextView;
        public TextView mCommentTextView;
        public TextView mCollectionTextView;
        public TextView mLoveTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeadImageCircleImageView = (CircleImageView) itemView.findViewById(
                    R.id.profile_image_listview);
            mUserNameTextView = (TextView) itemView.findViewById(dynamic_textview_userName);
            mAttentionButton = (Button) itemView.findViewById(R.id.dynamic_button);
            mArticleImageView = (ImageView) itemView.findViewById(R.id.dynamic_imageview);
            mArticleTextView = (TextView) itemView.findViewById(R.id.dynamic_article_content);
            mHotTextView = (TextView) itemView.findViewById(R.id.dynamic_textview_listview);
            mCommentTextView = (TextView) itemView.findViewById(R.id.dynamic_comment_listview);
            mCollectionTextView = (TextView) itemView.findViewById(R.id.dynamic_collection_listview);
            mLoveTextView = (TextView) itemView.findViewById(R.id.dynamic_love_listview);
        }
    }
}
