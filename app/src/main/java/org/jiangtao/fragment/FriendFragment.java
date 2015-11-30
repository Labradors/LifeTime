package org.jiangtao.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jiangtao.adapter.FriendFragmentAdapter;
import org.jiangtao.lifetime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends android.support.v4.app.Fragment {
    private View mView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;
    private List<android.support.v4.app.Fragment> mListFragment;
    private List<String> mListTitle;
    private android.support.v4.app.Fragment mFriendFragment;
    private android.support.v4.app.Fragment mFriendInfoFragment;

    public FriendFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        instanceTabLayout();
        initViewList();
        viewPagerFuction();
        return mView;
    }

    /**
     * 控件关联
     */
    private void viewPagerFuction() {
        mViewPager = (ViewPager) mView.findViewById(
                R.id.viewpager_fragment_viewpager_friend);
        mPagerAdapter = new FriendFragmentAdapter(getChildFragmentManager(),
                mListFragment, mListTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    /**
     * 初始化list
     */
    private void initViewList() {
        mFriendFragment = new DynamicFriendFragment();
        mFriendInfoFragment = new DynamicInfoFragment();
        mListFragment = new ArrayList<>();
        mListFragment.add(mFriendFragment);
        mListFragment.add(mFriendInfoFragment);
        mListTitle = new ArrayList<>();
        mListTitle.add("好友");
        mListTitle.add("动态");
    }

    /**
     * 初始化tablayout
     */
    private void instanceTabLayout() {
        mTabLayout = (TabLayout) mView.findViewById(
                R.id.tablayout_fragment_tablayout_friend);
    }
}
