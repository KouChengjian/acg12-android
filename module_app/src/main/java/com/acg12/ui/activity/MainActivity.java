package com.acg12.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.cache.AccountManager;
import com.acg12.conf.AppConfig;
import com.acg12.conf.EventBusConfig;
import com.acg12.entity.event.CommonEnum;
import com.acg12.entity.event.CommonEvent;
import com.acg12.entity.po.UserEntity;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.utils.ForegroundUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.GlideUtil;
import com.acg12.lib.widget.dialog.CommonDialog;
import com.acg12.ui.activity.setting.SettingActivity;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.MainContract;
import com.acg12.ui.fragment.HomeFragment;
import com.acg12.ui.presenter.MainPresenter;
import com.acg12.widget.debug.DebugBaseServerDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/19
 * Description: 自动生成
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener, View.OnLongClickListener {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    protected NavigationView navigationView;

    private MenuItem[] mTabs;
    private Fragment[] fragments;
    private HomeFragment homeFragment;
//    private FindFragemnt findFragemnt;
//    private CalendarFragment calendarFragment;

    private View headerView;
    private ImageView iv_nav_avatar;
    private TextView tv_nav_nick;
    private ImageView iv_nav_sex;
    private TextView tv_nav_signature;

    private CommonDialog commonDialog;

    private int currentTabIndex;
    public static long firstTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);
        EventBusConfig.register(EventBusConfig.EventComm | EventBusConfig.EventUser, this);
        ForegroundUtil.register(getApplication());
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        headerView = navigationView.getHeaderView(0);
        iv_nav_avatar = headerView.findViewById(R.id.iv_nav_avatar);
        tv_nav_nick = headerView.findViewById(R.id.tv_nav_nick);
        iv_nav_sex = headerView.findViewById(R.id.iv_nav_sex);
        tv_nav_signature = headerView.findViewById(R.id.tv_nav_signature);

        Menu menu = navigationView.getMenu();
        mTabs = new MenuItem[menu.size()];
        mTabs[0] = menu.findItem(R.id.nav_home);
        mTabs[1] = menu.findItem(R.id.nav_find);
        mTabs[2] = menu.findItem(R.id.nav_calendar);

        homeFragment = HomeFragment.newInstance();
//        calendarFragment =  CalendarFragment.newInstance();
        fragments = new Fragment[]{homeFragment};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, fragments[0])
                .show(fragments[0])
                .commit();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this, iv_nav_avatar, tv_nav_nick, tv_nav_signature);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
//                mView.onTabSelect(0);
                break;
            case R.id.nav_find:
//                mView.onTabSelect(1);
                break;
            case R.id.nav_calendar:
//                mView.onTabSelect(2);
                break;
            case R.id.nav_star:
//                if (DaoBaseImpl.getInstance(mContext).getCurrentUser() == null) {
//                    startAnimActivity(LoginActivity.class); //IndexActivity CollectActivity
//                } else {
//                    startAnimActivity(CollectActivity.class);
//                }
                break;
            case R.id.nav_down:
                startAnimActivity(DownloadActivity.class);
                break;
            case R.id.nav_history:
                startAnimActivity(RecordActivity.class);
                break;
            case R.id.nav_color_lens:
                startAnimActivity(SkinActivity.class);
                break;
            case R.id.nav_settings:
                startAnimActivity(SettingActivity.class);
                break;
        }
        closeDrawers();
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_nav_avatar || id == R.id.tv_nav_nick || id == R.id.tv_nav_signature) {
            closeDrawers();
//            UserEntity u = DaoBaseImpl.getInstance(mContext).getCurrentUser();
//            if (u == null) {
//                startAnimActivity(LoginActivity.class);
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user", u);
//                startAnimActivity(UserInfoActivity.class, bundle);
//            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.iv_nav_avatar) {
            if (BaseApp.isDebug()) {
                new DebugBaseServerDialog(this).show();
                String baseURLInfo = String.format("服务器地址:[%s]", AppConfig.SERVER.baseURL);
                showMsg("当前地址为" + baseURLInfo);
            }
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEvent(UserEntity user) {
        paddingDate(user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commonEvent(CommonEvent commonEvent) {
        if (CommonEnum.HTTP_TOKEN_LOSE == commonEvent.getCommonEvent()) {
            showExitDialog();
        } else if (CommonEnum.COMMON_TOGGLE_DRAWER == commonEvent.getCommonEvent()) {
            toggleDrawer();
        }
    }

    public void paddingDate(UserEntity user) {
        if (user == null || user.getUsername() == null) {
            iv_nav_avatar.setImageResource(R.mipmap.bg_avatar_default);
            iv_nav_sex.setVisibility(View.GONE);
            ViewUtil.setText(tv_nav_nick, "点击头像登录");
            ViewUtil.setText(tv_nav_signature, "");
        } else {
            String avatar = user.getAvatar();
            if (avatar != null) {
                GlideUtil.glideCircleLoading(context(), avatar, iv_nav_avatar);
                iv_nav_sex.setVisibility(View.VISIBLE);
                if (user.getSex() == 0) {
                    iv_nav_sex.setSelected(false);
                } else {
                    iv_nav_sex.setSelected(true);
                }
                ViewUtil.setText(tv_nav_nick, user.getNick());
                ViewUtil.setText(tv_nav_signature, user.getSignature());
            }
        }
    }

    private void onTabSelect(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setChecked(false);
        //把当前tab设为选中状态
        mTabs[index].setChecked(true);
        currentTabIndex = index;
    }

    private void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    private void showExitDialog() {
        if (ForegroundUtil.get().getActivity() == null) return;
        if (commonDialog != null && commonDialog.isShowing()) {
            return;
        }
        commonDialog = new CommonDialog(ForegroundUtil.get().getActivity(), "警告", "登录时间已过期，请重新登录", true);
        commonDialog.setCallback(new CommonDialog.Callback() {
            @Override
            public void commit() {
                AccountManager.getInstance().logout();
            }

            @Override
            public void cancle() {
                AccountManager.getInstance().logout();
            }
        });
        commonDialog.showDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                toggleDrawer();
            } else {
//                if (BaseConfig.ListVideoUtilInstance().backFromFull()) {
//                    return true;
//                }
                if (firstTime + 2000 > System.currentTimeMillis()) {
                    finish();
                } else {
                    showMsg(R.string.double_click_logout);
                }
                firstTime = System.currentTimeMillis();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        EventBusConfig.unregister(EventBusConfig.EventComm | EventBusConfig.EventUser, this);
        super.onDestroy();
    }
}