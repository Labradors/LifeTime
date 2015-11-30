package org.jiangtao.lifetime;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import org.jiangtao.adapter.FriendFragmentAdapter;
import org.jiangtao.fragment.NoteFragment;
import org.jiangtao.fragment.WriteNoteFragment;

import java.util.ArrayList;
import java.util.List;

public class WriteNoteActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;
    private List<Fragment> mListFragment;
    private List<String> mListTitle;
    private android.support.v4.app.Fragment mNoteFragement;
    private android.support.v4.app.Fragment mWriteNoteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notectivity);

        this.getSupportActionBar().hide();

        instanceTabLayout();
        initViewList();
        viewPagerFuction();

    }
    /**
     * 控件关联
     */
    private void viewPagerFuction() {
        mViewPager = (ViewPager)findViewById(
                R.id.viewpager_fragment_viewpager_writenote);
        mPagerAdapter = new FriendFragmentAdapter(getSupportFragmentManager(),
                mListFragment, mListTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }
    /**
     * 初始化list
     */
    private void initViewList() {
        mNoteFragement = new NoteFragment();
        mWriteNoteFragment = new WriteNoteFragment();
        mListFragment = new ArrayList<>();
        mListFragment.add(mNoteFragement);
        mListFragment.add(mWriteNoteFragment);
        mListTitle = new ArrayList<>();
        mListTitle.add("列表");
        mListTitle.add("新建");
    }
    /**
     * 初始化tablayout
     */
    private void instanceTabLayout() {
        mTabLayout = (TabLayout) findViewById(
                R.id.tablayout_fragment_tablayout_note);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_notectivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
