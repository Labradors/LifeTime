package org.jiangtao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.Comment;
import org.jiangtao.lifetime.R;
import org.jiangtao.utils.ConstantValues;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-jiang on 15-12-8.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> mCommentLists;
    private Context mContext;
    private LayoutInflater inflater;

    public CommentAdapter(ArrayList<Comment> mCommentLists, Context mContext) {
        this.mCommentLists = mCommentLists;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_comment_recylerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment = mCommentLists.get(position);
        LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                comment.getComment_user_headImage()).into(holder.mCircleImageView);
        holder.mUserNameTextView.setText(comment.getComment_user_name());
        holder.mContentTextView.setText(comment.getComment_content());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView mCircleImageView;
        public TextView mUserNameTextView;
        public TextView mContentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.comment_headimage);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.comment_username);
            mContentTextView = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }
}
