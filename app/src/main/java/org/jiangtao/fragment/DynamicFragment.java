package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jiangtao.lifetime.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * 请求服务器。做适配器，返回数据的定式：
 * author信息
 * 文章内容
 * 附带信息，评论等，多表查询
 * ---------------------------------------
 * 返回规则。
 */
public class DynamicFragment extends android.support.v4.app.Fragment implements OnRefreshListener, View.OnClickListener {

    private View mView;
    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListViewDynamic;
    /**
     * listview中的布局
     */
    private CircleImageView mCircleImageView;
    private TextView mUserNameTextView;
    private Button mAttentionButton;
    private ImageView mArticleImageView;
    private TextView mArticleTextView;
    private TextView mHotTextView;
    private TextView mCommentTextView;
    private TextView mCollectionTextView;
    private TextView mLoveTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initControl();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        declareOnclikListener();
        return mView;
    }

    /**
     * 声明各个控件的监听实现
     */
    private void declareOnclikListener() {
        mAttentionButton.setOnClickListener(this);
        mHotTextView.setOnClickListener(this);
        mCommentTextView.setOnClickListener(this);
        mCollectionTextView.setOnClickListener(this);
        mLoveTextView.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_dynamic);
        mListViewDynamic = (ListView) mView.findViewById(R.id.listview_fragment_dynamic);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(
                R.id.refresh_fragment_dynamic);
        //listview
        mCircleImageView = (CircleImageView) mView.findViewById(R.id.profile_image_listview);
        mUserNameTextView = (TextView) mView.findViewById(R.id.dynamic_textview_userName);
        mAttentionButton = (Button) mView.findViewById(R.id.dynamic_button);
        mArticleImageView = (ImageView) mView.findViewById(R.id.dynamic_imageview);
        mArticleTextView = (TextView) mView.findViewById(R.id.dynamic_article_content);
        mHotTextView = (TextView) mView.findViewById(R.id.dynamic_textview_listview);
        mCommentTextView = (TextView) mView.findViewById(R.id.dynamic_comment_listview);
        mCollectionTextView = (TextView) mView.findViewById(R.id.dynamic_collection_listview);
        mLoveTextView = (TextView) mView.findViewById(R.id.dynamic_love_listview);
    }

    /**
     * 设置刷新界面的颜色
     */
    private void swipeColorListener() {

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light);

    }

    /**
     * 有网或者有缓存
     */
    private void hideImageView() {

        mImageView.setVisibility(View.GONE);
    }

    /**
     * 没网，没缓存
     */
    private void showImageView() {
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 实现刷新操作
     */
    @Override
    public void onRefresh() {
        swipeColorListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamic_button: {

                break;
            }
            case R.id.dynamic_textview_listview: {

                break;
            }
            case R.id.dynamic_comment_listview: {

                break;
            }
            case R.id.dynamic_collection_listview: {

                break;
            }
            case R.id.dynamic_love_listview: {

                break;
            }
        }

    }
}
