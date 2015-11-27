package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.jiangtao.fragment.HomePageFragment;
import org.jiangtao.fragment.MessageFragment;
import org.jiangtao.fragment.PersonalFragment;
import org.jiangtao.fragment.SearchFragment;
import org.jiangtao.utils.Popupwindow;
import org.jiangtao.utils.TurnActivity;


public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    //自定义弹框类
    Popupwindow menuWindow;
    private ImageButton mBtnPopupwindow;

    private NavigationView mNavigationView;

    private DrawerLayout mDrawerLayout;
    private HomePageFragment mHomePageFragment;
    private MessageFragment mMessageFragment;
    private SearchFragment mSearchFragment;
    private PersonalFragment mPersonalFragment;
    Fragment[] fragments = new Fragment[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        drawerBindFragment();
        manageActionBar();
        initFragment();
        controlsInitialize();

        /**
         * Popupwindow
         */
        mBtnPopupwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow = new Popupwindow(IndexActivity.this, itemsOnClick);
                menuWindow.showAtLocation(IndexActivity.this.findViewById(R.id.ibtn_activity_index_pupopwindow), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });

    }

    //为弹出popupwindow窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.pup_btn_takephoto:
                    break;
                case R.id.pup_btn_richscan:
                    break;
                case R.id.pup_btn_writedynamic:
                    TurnActivity.turnWrietDynamicActivity(IndexActivity.this);
                    break;
                case R.id.pup_btn_writenote:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * indexactivity中初始化控件
     */
    private void controlsInitialize() {

        mBtnPopupwindow = (ImageButton) findViewById(R.id.ibtn_activity_index_pupopwindow);

    }

    /**
     * indexactivity中绑定fragment
     */
    private void drawerBindFragment() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_activity_index);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
    }

    /**
     * 对actionabr的操作
     */
    private void manageActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    /**
     * 初始化所有fragment
     */
    private void initFragment() {
        mHomePageFragment = new HomePageFragment();
        mMessageFragment = new MessageFragment();
        mSearchFragment = new SearchFragment();
        mPersonalFragment = new PersonalFragment();
        fragments[0] = mHomePageFragment;
        fragments[1] = mSearchFragment;
        fragments[2] = mMessageFragment;
        fragments[3] = mPersonalFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout_activtiy_index, fragments[0])
                .add(R.id.framelayout_activtiy_index, fragments[1])
                .add(R.id.framelayout_activtiy_index, fragments[2])
                .add(R.id.framelayout_activtiy_index, fragments[3])
                .show(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .commit();
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
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .commit();
                break;
            }
            case R.id.ibtn_activity_index_search: {
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[1])
                        .hide(fragments[0])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .commit();
                break;
            }
            case R.id.ibtn_activity_index_message: {
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[2])
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[3])
                        .commit();
                break;
            }
            case R.id.ibtn_activity_index_personal: {
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[3])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[0])
                        .commit();
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

    public void personalOnClick(View v) {
        switch (v.getId()) {
            case R.id.personal_tv_nologin:
                TurnActivity.turnLoginActivity(IndexActivity.this);
                break;
            case R.id.personal_tv_message:
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[2])
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[3])
                        .commit();
                break;
        }
    }

    public void messageOnClik(View v) {
        switch (v.getId()) {
            case R.id.message_tv_mycollection:
                TurnActivity.turnMyCollectionActivity(IndexActivity.this);
                break;
            case R.id.message_tv_mydynamic:
                TurnActivity.turnMyDynamicActivity(IndexActivity.this);
                break;
        }
    }


}
