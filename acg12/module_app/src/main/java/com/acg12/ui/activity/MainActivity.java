package com.acg12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.acg12.conf.AppConfig;
import com.acg12.conf.EventConfig;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.constant.Constant;
import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.Update;
import com.acg12.entity.User;

import com.acg12.lib.listener.HttpRequestListener;

import com.acg12.net.download.DownloadManger;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;

import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.widget.dialog.UpdateDialog;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;

import com.acg12.R;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.MainView;
import com.acg12.widget.dialog.debug.DebugBaseServerDialog;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SkinBaseActivity<MainView> implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,View.OnLongClickListener{

    public static long firstTime;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
//        BaseConfig.initListVideoUtil(this);
        EventConfig.get().getUserEvent().register(this);
        EventConfig.get().getNavigationEvent().register(this);

        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.NAVIGATIONVIEW, R.color.theme_primary));
        dynamicAddView(mView.getNavigationView(), mDynamicAttr);

        updateUser();
        updateApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //LogUtil.e(requestCode+"===="+resultCode);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mView.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_home:
                mView.onTabSelect(0);
                break;
            case R.id.nav_find:
                mView.onTabSelect(1);
                break;
            case R.id.nav_calendar:
                mView.onTabSelect(2);
                break;
            case R.id.nav_star:
                if (DaoBaseImpl.getInstance(mContext).getCurrentUser() == null) {
                    startAnimActivity(LoginActivity.class); //IndexActivity CollectActivity
                } else {
                    startAnimActivity(CollectActivity.class);
                }
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
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_nav_avatar || id == R.id.tv_nav_nick || id == R.id.tv_nav_signature) {
            mView.closeDrawers();
            User u = DaoBaseImpl.getInstance(mContext).getCurrentUser();
            if (u == null) {
                startAnimActivity(LoginActivity.class);
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", u);
                startAnimActivity(UserInfoActivity.class, bundle);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.iv_nav_avatar){
            if(BaseApp.isDebug()){
                new DebugBaseServerDialog(this).show();
                String baseURLInfo = String.format("服务器地址:[%s] VodType:[%s]", AppConfig.SERVER.baseURL, AppConfig.SERVER.imAppid);
                ShowToast("当前地址为" + baseURLInfo);
            }
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toggleDrawer(Boolean toggle) {
        mView.toggleDrawer();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUser(User user) {
        mView.paddingDate(user);
    }

    public void updateUser() {
        User user = DaoBaseImpl.getInstance(mContext).getCurrentUser();
        if (user == null) return;
        HttpRequestImpl.getInstance().userInfo(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                mView.paddingDate(result);
            }

            @Override
            public void onFailure(int errorcode, String msg) {

            }
        });
    }

    private void showUpdateApp(Update result) {
        UpdateDialog updateDialog = new UpdateDialog(mContext, result);
        updateDialog.setTitle("漫友更新啦");
        updateDialog.setTitleGravity();
        updateDialog.show(UpdateDialog.Form.main);
    }

    private void updateApp() {
        HttpRequestImpl.getInstance().updateApp(currentUser(), AppUtil.getPackageInfo(mContext).versionCode, new HttpRequestListener<Update>() {
            @Override
            public void onSuccess(Update result) {
                showUpdateApp(result);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mView.getDrawerLayout().isDrawerVisible(GravityCompat.START)) {
                mView.toggleDrawer();
            } else {
//                if (BaseConfig.ListVideoUtilInstance().backFromFull()) {
//                    return true;
//                }
                if (firstTime + 2000 > System.currentTimeMillis()) {
                    finish();
                } else {
                    ShowToast(R.string.double_click_logout);
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
        DownloadManger.getInstance(mContext).destroy();
        super.onDestroy();
        EventConfig.get().getUserEvent().unregister(this);
        EventConfig.get().getNavigationEvent().unregister(this);
//        BaseConfig.ListVideoUtilInstance().releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }
}
