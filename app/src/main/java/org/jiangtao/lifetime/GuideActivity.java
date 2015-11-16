package org.jiangtao.lifetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jiangtao.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<View> mGudieList;
    private ViewPager mGuideViewPager;
    private ViewPagerAdapter mGudieViewPagerAdapter;
    private ImageView[] mImageView;
    private int[] mIds = {R.id.iv_one, R.id.iv_two, R.id.iv_three};
    private Button mComeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        hideActionBar();
        initViews();
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mGuideViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        mGudieList = new ArrayList<>();
        mGudieList.add(inflater.inflate(R.layout.view_guide_one, null));
        mGudieList.add(inflater.inflate(R.layout.view_guide_two, null));
        mGudieList.add(inflater.inflate(R.layout.view_guide_three, null));
        mGudieViewPagerAdapter = new ViewPagerAdapter(mGudieList, this);
        mGuideViewPager.setAdapter(mGudieViewPagerAdapter);
        initmImageView();
        mGuideViewPager.setOnPageChangeListener(this);
        mComeButton = (Button) mGudieList.get(2).findViewById(R.id.start_btn);
        mComeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initmImageView() {
        mImageView = new ImageView[mGudieList.size()];
        for (int i = 0; i < mGudieList.size(); i++) {
            mImageView[i] = (ImageView) findViewById(mIds[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mIds.length; i++) {
            if (position == i) {
                mImageView[i].setImageResource(R.drawable.login_point_selected);
            } else {
                mImageView[i].setImageResource(R.drawable.login_point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
