package org.jiangtao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.jiangtao.fragment.DynamicFragment;
import org.jiangtao.fragment.LoveFragment;
import org.jiangtao.fragment.RecommendFragment;

import java.util.List;

/**
 * Created by mr-jiang on 15-11-16.
 */
public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mViewList;

    public HomePageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomePageFragmentAdapter(FragmentManager fm, List<Fragment> mViewList) {
        super(fm);
        this.mViewList = mViewList;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new DynamicFragment();
                break;
            }
            case 1: {
                fragment = new RecommendFragment();
                break;
            }
            case 2: {
                fragment = new LoveFragment();
                break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }
}
