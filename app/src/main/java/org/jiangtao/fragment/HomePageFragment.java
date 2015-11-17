package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jiangtao.adapter.HomePageFragmentAdapter;
import org.jiangtao.lifetime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends android.support.v4.app.Fragment {

    private TabLayout mTabLayout;
    private View mView;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<android.support.v4.app.Fragment> mViewList;
    private List<String> mTitleList;

    public HomePageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_page, container, false);
        instanceTablayout();
        initmViewList();
        viewPagerFuction();
        return mView;
    }

    /**
     * 初始化变量
     */
    private void instanceTablayout() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout_fragment_home_page);
    }

    /**
     * 为viewpager设置适配器
     */

    private void viewPagerFuction() {
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_fragment_home_age);
        mPagerAdapter = new HomePageFragmentAdapter(getChildFragmentManager(),
                mViewList, mTitleList);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    /**
     * 初始化mViewList
     */
    private void initmViewList() {
        mViewList = new ArrayList<>();
        mViewList.add(new DynamicFragment());
        mViewList.add(new RecommendFragment());
        mViewList.add(new LoveFragment());
        mTitleList = new ArrayList<>();
        mTitleList.add("动态");
        mTitleList.add("推荐");
        mTitleList.add("喜欢");
    }

}
