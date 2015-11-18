package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jiangtao.fragment.HomePageFragment;


public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private Fragment mHomePageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        drawerBindFragment();
        manageActionBar();
        initFragment();
        //默认显示的页面
        getSupportFragmentManager().beginTransaction().replace(
                R.id.framelayout_activtiy_index, mHomePageFragment
        ).commit();
    }

    /**
     * indexactivity中绑定fragment
     */
    private void drawerBindFragment() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_activity_index);
        Fragment fragment = getSupportFragmentManager().findFragmentById
                (R.id.fragment_drawer_activity_index);
    }

    /**
     * 对actionabr的操作
     */
    private void manageActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化所有fragment
     */
    private void initFragment() {
        mHomePageFragment = new HomePageFragment();
    }


    /**
     * 监听menu菜单的动作事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_activity_index_homepage: {
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.framelayout_activtiy_index, mHomePageFragment
                ).commit();
                break;
            }
            case R.id.ibtn_activity_index_search: {
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.framelayout_activtiy_index, mHomePageFragment
                ).commit();
                break;
            }
            case R.id.ibtn_activity_index_pupopwindow: {

                break;
            }
            case R.id.ibtn_activity_index_message: {
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.framelayout_activtiy_index, mHomePageFragment
                ).commit();
                break;
            }
            case R.id.ibtn_activity_index_personal: {
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.framelayout_activtiy_index, mHomePageFragment
                ).commit();
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_index, menu);
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
}
