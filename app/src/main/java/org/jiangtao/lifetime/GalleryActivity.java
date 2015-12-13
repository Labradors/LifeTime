package org.jiangtao.lifetime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jiangtao.adapter.GalleryAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.sql.DynamicArticleBusinessImpl;
import org.jiangtao.utils.LogUtils;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = GalleryActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ArrayList<String> mGalleryArrayList;
    private GalleryAdapter mGalleryAdapter;
    private GridLayoutManager mGridLayoutManager;
    private DynamicArticleBusinessImpl business;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111: {
                    LogUtils.d(TAG, "结果正确");
                    for (int i = 0; i < mGalleryArrayList.size(); i++) {
                        LogUtils.d(TAG, mGalleryArrayList.get(i));
                    }
                    fillingAdapter();
                    break;
                }
                case 0x112: {
                    break;
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        controlsInstance();
        new GalleryAsyncTask().execute();
    }

    private void fillingAdapter() {
        mGalleryAdapter = new GalleryAdapter(mGalleryArrayList, this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mGalleryAdapter);
    }

    private void controlsInstance() {
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_recycleview);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        business = new DynamicArticleBusinessImpl(this);
        mGalleryArrayList = new ArrayList<>();
    }

    public class GalleryAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mGalleryArrayList = business.getGallery(LifeApplication.user_id);
                if (mGalleryArrayList != null && mGalleryArrayList.size() != 0) {
                    mHandler.sendEmptyMessage(0x111);
                } else {
                    mHandler.sendEmptyMessage(0x112);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
