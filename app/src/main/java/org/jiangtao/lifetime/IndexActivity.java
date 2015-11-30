package org.jiangtao.lifetime;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.User;
import org.jiangtao.fragment.FriendFragment;
import org.jiangtao.fragment.HomePageFragment;
import org.jiangtao.fragment.MessageFragment;
import org.jiangtao.fragment.PersonalFragment;
import org.jiangtao.sql.UserBusinessImpl;
import org.jiangtao.utils.Code;
import org.jiangtao.utils.Popupwindow;
import org.jiangtao.utils.TurnActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    //自定义弹框类
    private Popupwindow menuWindow;
    private ImageButton mBtnPopupwindow;
    private FragmentCallback fragmentCallback;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private HomePageFragment mHomePageFragment;
    private MessageFragment mMessageFragment;
    private FriendFragment mFriendFragment;
    private PersonalFragment mPersonalFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mHeadImageView;
    private TextView mHeadTextView;
    private UserBusinessImpl business;
    Fragment[] fragments = new Fragment[4];
    private static Boolean isQuit = false;
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        drawerBindFragment();
        manageActionBar();
        initFragment();
        controlsInitialize();
        //判断用户登陆
//        decideUserLogin();

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

    /**
     * 判断用户登陆
     */
    private void decideUserLogin() {
        business = new UserBusinessImpl(this);
        User user = new User();
        try {
            ArrayList<User> userArrayList = (ArrayList<User>) business.selectAllUser();
            if (userArrayList != null) {
                for (int i = 0; i < userArrayList.size(); i++) {
                    user = userArrayList.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            LifeApplication.isLogin = true;
            LifeApplication.user_email = user.getUser_email();
            LifeApplication.user_name = user.getUser_name();
            LifeApplication.user_id = user.getUser_id();
            String user_headimage = user.getUser_headpicture();
            //读取图像，丢到drawer的图像层

            mHeadTextView.setText(user.getUser_name());
        }
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
                    TurnActivity.turnWrietNoteActivity(IndexActivity.this);
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
        mHeadImageView = (ImageView) findViewById(R.id.layout_menu_iv_header);
        mHeadTextView = (TextView) findViewById(R.id.layout_menu_tv_header);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_action_back, R.drawable.ic_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_open);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_close);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
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
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * 初始化所有fragment
     */
    private void initFragment() {
        mHomePageFragment = new HomePageFragment();
        mMessageFragment = new MessageFragment();
        mFriendFragment = new FriendFragment();
        mPersonalFragment = new PersonalFragment(this);
        fragments[0] = mHomePageFragment;
        fragments[1] = mFriendFragment;
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void personalOnClick(View v) {
        switch (v.getId()) {
            case R.id.personal_tv_nologin:
                if (LifeApplication.isLogin) {
                    //开启activity，更新用户信息
                    Intent intent = new Intent(IndexActivity.this, UpdateUserInformationActivity.class);
                    startActivityForResult(intent, Code.REQUESTCODE_UPDATEUSER_INFORMATION);
                } else {
                    TurnActivity.turnLoginActivity(IndexActivity.this);
                }
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

    /**
     * 根据请求码和结果码进行不同的操作
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 当用户登陆成功后，更新数据
         */
        if (requestCode == Code.REQUESTCODE_INDEXACTIVITY_TO_LOGINACTIVITY) {
            switch (resultCode) {
                case Code.RESULLTCODE_LOGINSUCCESS_NOPICTURE: {
                    boolean flag = data.getBooleanExtra("flag", false);
                    if (flag) {
                        fragmentCallback.onMainAction(flag);
                        getSupportFragmentManager().beginTransaction()
                                .show(fragments[3])
                                .hide(fragments[1])
                                .hide(fragments[2])
                                .hide(fragments[0])
                                .commitAllowingStateLoss();
                    }
                }
            }
        }
    }

    /**
     * 定义activity与fragment之间的通信
     */
    public interface FragmentCallback {
        public void onMainAction(boolean flag);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            fragmentCallback = (FragmentCallback) fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置单击返回键的效果
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isQuit) {
                isQuit = true;
                Snackbar.make(mDrawerLayout, R.string.two_click_exit, Snackbar.LENGTH_SHORT).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return true;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
