package org.jiangtao.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.ArticleAllDynamic;
import org.jiangtao.lifetime.ImageActivity;
import org.jiangtao.lifetime.R;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-jiang on 15-12-13.
 * 个人主页中的adapter
 */
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {

    private ArrayList<ArticleAllDynamic> mArticleList;
    private Context mContext;
    private LayoutInflater mInflater;
    private DynamicArticleBusinessImpl businessImpl;
    private NoticeActivityRefreshAdapter mRefresh;
    boolean result = false;
    private static final String TAG = HomePageAdapter.class.getSimpleName();

    public HomePageAdapter(ArrayList<ArticleAllDynamic> list, Context context) {
        if (list.size() != 0 && list != null) {
            mArticleList = list;
        } else {
            mArticleList = new ArrayList<>();
        }
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        businessImpl = new DynamicArticleBusinessImpl(mContext);
    }

    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_homepage_myself, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomePageAdapter.ViewHolder holder, int position) {
        final ArticleAllDynamic dynamic = mArticleList.get(position);
        holder.mUserNameTextView.setText(dynamic.getUser_name());
        holder.mArtcleContentTextView.setText(dynamic.getArticle_content());
        holder.mShareTextView.setText("0");
        holder.mCollectionTextView.setText("0");
        holder.mLoveTextView.setText(dynamic.getArticle_love_number() + "");
        holder.mCommentTextView.setText(dynamic.getArticle_comment_number() + "");
        if (LifeApplication.getInstance().isNetworkAvailable()) {
            LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                    dynamic.getUser_headpicture()).into(holder.mCircleImageView);
            LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                    dynamic.getArticle_image()).into(holder.mArticleImageView);
        }
        holder.setOnClickListener(new ViewHolder.ControlsOnClick() {
            @Override
            public void controlsOnClickListener(View v, int position) {
                switch (v.getId()) {
                    case R.id.layout_myself_article_image: {
                        Intent intent = new Intent(mContext, ImageActivity.class);
                        intent.putExtra("image_address", dynamic.getArticle_image());
                        mContext.startActivity(intent);
                        break;
                    }
                    case R.id.layout_myself_delete: {
                        dialog();
                        if (result) {
                            new DeleteLocalArticleAsyncTask().execute(mArticleList.get(position).getArticle_id());
                            LogUtils.d(TAG, "打印是否正确");
                        }
                        break;
                    }
                }
            }
        });
    }

    /**
     * 先打开一个对话框
     * 让用户确定删除
     *
     * @return
     */
    public boolean dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确认删除这篇文章吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                result = true;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        return result;
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView mCircleImageView;
        public TextView mUserNameTextView;
        public TextView mTimeTextView;
        public ImageView mArticleImageView;
        public TextView mArtcleContentTextView;
        public TextView mLoveTextView;
        public TextView mCommentTextView;
        public TextView mCollectionTextView;
        public TextView mShareTextView;
        public TextView mDeleteTextView;
        public ControlsOnClick mControlsOnClick;

        public ViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.layout_myself_headimage);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.layout_myself_userName);
            mTimeTextView = (TextView) itemView.findViewById(R.id.layout_myself_time);
            mArticleImageView = (ImageView) itemView.findViewById(R.id.layout_myself_article_image);
            mArtcleContentTextView = (TextView) itemView.findViewById(R.id.layout_myself_article_content);
            mLoveTextView = (TextView) itemView.findViewById(R.id.layout_myself_love);
            mCommentTextView = (TextView) itemView.findViewById(R.id.layout_myself_comment);
            mCollectionTextView = (TextView) itemView.findViewById(R.id.layout_myself_connection);
            mShareTextView = (TextView) itemView.findViewById(R.id.layout_myself_share);
            mDeleteTextView = (TextView) itemView.findViewById(R.id.layout_myself_delete);
            instanceOnClickListener();
        }

        private void instanceOnClickListener() {
            mArticleImageView.setOnClickListener(this);
            mLoveTextView.setOnClickListener(this);
            mCommentTextView.setOnClickListener(this);
            mCollectionTextView.setOnClickListener(this);
            mShareTextView.setOnClickListener(this);
            mDeleteTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mControlsOnClick != null) {
                mControlsOnClick.controlsOnClickListener(v, getLayoutPosition());
            }
        }

        public interface ControlsOnClick {
            public void controlsOnClickListener(View view, int position);
        }

        public void setOnClickListener(ControlsOnClick controlOnClick) {
            mControlsOnClick = controlOnClick;
        }
    }

    /**
     * 运用异步任务删除本地文章
     */
    public class DeleteLocalArticleAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int article_id = params[0];
            try {
                boolean localResult = businessImpl.deleteArticleFromArticleID(article_id);
                if (mRefresh != null) {
                    if (localResult) {
                        mRefresh.refreshAdapter(localResult);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     *
     */
    public class DeleteHostArticleAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }
    }

    public interface NoticeActivityRefreshAdapter {
        public void refreshAdapter(boolean result);
    }

    public void instanceNoticeActivityRefreshAdapter(NoticeActivityRefreshAdapter refreshAdapter) {
        mRefresh = refreshAdapter;
    }
}
