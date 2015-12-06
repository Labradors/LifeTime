package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jiangtao.adapter.HomePageFragmentAdapter;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.fragment.AppSettingFragment;
import org.jiangtao.fragment.UserInfoSettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置界面
 * include two fragment
 * 1.app setting fragent
 * 2.user setting fragment
 */
public class SettingActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentsList;
    private List<String> mTitles;
    private UserCallBack userCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seetting);
        controlsInit();
        fragmentsListInit();
        viewPagerFuction();
    }

    private void viewPagerFuction() {
        PagerAdapter mPagerAdapter = new HomePageFragmentAdapter(getSupportFragmentManager(),
                fragmentsList, mTitles);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    private void fragmentsListInit() {
        Fragment userInformationSettingFragment = new UserInfoSettingFragment();
        Fragment appSettingFragment = new AppSettingFragment();
        fragmentsList = new ArrayList<>();
        fragmentsList.add(userInformationSettingFragment);
        fragmentsList.add(appSettingFragment);
        mTitles = new ArrayList<>();
        mTitles.add("个人");
        mTitles.add("软件");

    }

    private void controlsInit() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_fragment_setting);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_fragment_setting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seetting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * app设置单击事件
     *
     * @param view
     */
    public void appSettingOnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_cache: {
                clearCache();
            }
        }
    }

    /**
     * 清理缓存
     */
    public void clearCache() {
        LifeApplication.lruCache.evictAll();
        userCallBack.sendMessage(true);
    }

    /**
     * 通信接口
     */
    public interface UserCallBack {
        public void sendMessage(boolean flag);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            userCallBack = (UserCallBack) fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
