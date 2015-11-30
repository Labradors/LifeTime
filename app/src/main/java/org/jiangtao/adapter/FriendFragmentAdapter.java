package org.jiangtao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by mr-jiang on 15-11-30.
 * 朋友fragment
 */
public class FriendFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mViewList;
    private List<String> mTitleList;

    public FriendFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FriendFragmentAdapter(FragmentManager fm, List<Fragment> mViewList,
                                 List<String> mTitleList) {
        super(fm);
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
