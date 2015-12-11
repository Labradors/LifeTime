package org.jiangtao.lifetime;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.bean.User;
import org.jiangtao.fragment.FriendFragment;
import org.jiangtao.fragment.HomePageFragment;
import org.jiangtao.fragment.MessageFragment;
import org.jiangtao.fragment.PersonalFragment;
import org.jiangtao.sql.UserBusinessImpl;
import org.jiangtao.utils.BitmapUtils;
import org.jiangtao.utils.Code;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;
import org.jiangtao.utils.Popupwindow;
import org.jiangtao.utils.TurnActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.ShareSDK;

/**
 * 主页
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener
        , NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = IndexActivity.class.getSimpleName();
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
        decideUserLogin();
    }

    /**
     * 更新侧滑栏
     *
     * @throws Exception
     */
    private void updateNavigationView() throws Exception {
        business = new UserBusinessImpl(this);
        ArrayList<User> userArrayList = (ArrayList<User>) business.selectAllUser();
        LogUtils.d(TAG, userArrayList.size() + "---------");
        if (userArrayList != null && userArrayList.size() != 0) {
            for (int i = 0; i < userArrayList.size(); i++) {
                User user = userArrayList.get(i);
                if (user != null) {
                    LifeApplication.isLogin = true;
                    LifeApplication.user_email = user.getUser_email();
                    LifeApplication.user_name = user.getUser_name();
                    LifeApplication.user_id = user.getUser_id();
                    String user_name = user.getUser_name();
                    LogUtils.d(TAG, user_name);
                    mHeadTextView.setText(user_name);
                    LogUtils.d(TAG, ConstantValues.saveImageUri + LifeApplication.user_name +
                            ".png");
                    Bitmap bitmap = BitmapUtils.getBitmap(ConstantValues.saveImageUri +
                            LifeApplication.user_name + ".png");
                    if (bitmap != null) {
                        mHeadImageView.setImageBitmap(bitmap);
                    }
                    mNavigationView.invalidate();
                } else {
                    mHeadImageView.setImageBitmap(null);
                    mHeadTextView.setText(null);
                    mNavigationView.invalidate();
                }
            }
        } else {
            mHeadImageView.setImageBitmap(null);
            mHeadTextView.setText(null);
            mNavigationView.invalidate();
        }
    }

    /**
     * 判断用户登陆
     */
    private void decideUserLogin() {
        try {
            updateNavigationView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //为弹出popupwindow窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.pup_btn_takephoto:
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera, Activity.DEFAULT_KEYS_DIALER);
                    break;
                case R.id.pup_btn_richscan:
                    break;
                case R.id.pup_btn_writedynamic:
                    if (LifeApplication.isLogin) {
                        TurnActivity.turnWrietDynamicActivity(IndexActivity.this);
                    } else {
                        TurnActivity.turnLoginActivity(IndexActivity.this);
                    }
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
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_header, null, false);
        mBtnPopupwindow = (ImageButton) findViewById(R.id.ibtn_activity_index_pupopwindow);
        mHeadImageView = (ImageView) view.findViewById(R.id.layout_menu_iv_header);
        mHeadTextView = (TextView) view.findViewById(R.id.layout_menu_tv_header);
        mNavigationView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
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
        mNavigationView.setNavigationItemSelectedListener(this);
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
        mHomePageFragment = new HomePageFragment(this);
        mMessageFragment = new MessageFragment();
        mFriendFragment = new FriendFragment(this);
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
            /**
             * Popupwindow
             */
            case R.id.ibtn_activity_index_pupopwindow: {
                menuWindow = new Popupwindow(IndexActivity.this, itemsOnClick);
                menuWindow.showAtLocation(IndexActivity.this.findViewById(R.id.ibtn_activity_index_pupopwindow),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
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

    public void personalOnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fragment_username:
                if (LifeApplication.isLogin) {
                    //开启activity，更新用户信息
                    Intent intent = new Intent(IndexActivity.this, SettingActivity.class);
                    startActivityForResult(intent, Code.REQUESTCODE_UPDATEUSER_INFORMATION);
                } else {
                    TurnActivity.turnLoginActivity(IndexActivity.this);
                }
                break;
            case R.id.personal_tv_message: {
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[2])
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[3])
                        .commit();
                break;
            }
            case R.id.btn_cancel_login: {
                LogUtils.d(TAG, "run------");
                //lifetime设置为空
                LifeApplication.isLogin = false;
                LifeApplication.user_id = 0;
                LifeApplication.user_email = null;
                LifeApplication.user_name = null;
                //清理数据库
                try {
                    business.deleteTable();
                    fragmentCallback.onMainAction(false);
                    updateNavigationView();
                    Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Code.REQUESTCODE_INDEXACTIVITY_TO_LOGINACTIVITY);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
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
            LogUtils.d(TAG, "登陆成功");
            switch (resultCode) {
                case Code.RESULLTCODE_LOGINSUCCESS_NOPICTURE: {
                    boolean flag = data.getBooleanExtra("flag", false);
                    LogUtils.d(TAG, "<<________------" + flag);
                    try {
                        business = new UserBusinessImpl(this);
                        if (flag) {
                            /**
                             * 发送信号给personalfragment
                             * 把图片和用户名名换掉
                             */
                            User user = null;
                            user = business.selectUser(LifeApplication.user_email);
                            LogUtils.d(TAG, "查看是否取出文件" + user.toString());
                            if (user != null) {
                                LogUtils.d(TAG, "---------" + user.toString());
                                mHeadTextView.setText(user.getUser_name());
                                if (user.getUser_headpicture() != null) {
                                    /**
                                     * 将地址转化为bitmap
                                     */
                                    Bitmap bitmap = BitmapUtils.getBitmap(ConstantValues.saveImageUri +
                                            LifeApplication.user_name + ".png");
                                    mHeadImageView.setImageBitmap(bitmap);
                                    updateNavigationView();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fragmentCallback.onMainAction(flag);
                    getSupportFragmentManager().beginTransaction()
                            .show(fragments[3])
                            .hide(fragments[1])
                            .hide(fragments[2])
                            .hide(fragments[0])
                            .commitAllowingStateLoss();
                    break;
                }

            }
        }
        if (requestCode == Code.REQUEST_OPEN_WRITEDYNAMIC) {
            if (resultCode == Code.RESULTCODE_RETRUN_INDEX) {
                getSupportFragmentManager().beginTransaction()
                        .show(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .commitAllowingStateLoss();
            }
        }
    }

    /**
     * 菜单选项监听器
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_index: {
                Intent intent = new Intent(IndexActivity.this, DynamicActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_attention: {
                Intent intent = new Intent(IndexActivity.this, CollectionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_turn_themes: {
                //切换主题
                break;
            }
            case R.id.nav_setting:
                Intent intent = new Intent(IndexActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_exit: {
                finish();
                ShareSDK.stopSDK(this);
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
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
                ShareSDK.stopSDK(this);
                finish();
                System.exit(0);
            }
        }
        return true;

    }
}
