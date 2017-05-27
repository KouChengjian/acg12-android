package org.acg12.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


import com.shuyu.gsyvideoplayer.GSYVideoPlayer;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.config.Config;
import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.LoginView;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.Network;
import org.acg12.utlis.skin.entity.AttrFactory;
import org.acg12.utlis.skin.entity.DynamicAttr;
import org.acg12.ui.views.MainView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends PresenterActivityImpl<MainView> implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    public static long firstTime;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        Config.initListVideoUtil(this);
        Config.navigationEventBus().register(this);
        Config.userEventBus().register(this);
        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.NAVIGATIONVIEW, R.color.theme_primary));
        dynamicAddView(mView.getNavigationView(), mDynamicAttr);

        pudateUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //LogUtil.e(requestCode+"===="+resultCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toggleDrawer(Boolean toggle){
        mView.toggleDrawer();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUser(User user){
        LogUtil.e("updateUser = main");
        mView.paddingDate(user);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mView.closeDrawers();
        switch (item.getItemId()){
            case R.id.nav_home:
                mView.onTabSelect(0);
                break;
            case R.id.nav_star:
                if(DaoBaseImpl.getInstance().getCurrentUser() == null){
                    startAnimActivity(IndexActivity.class);
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
                if(DaoBaseImpl.getInstance().getCurrentUser() == null){
                    startAnimActivity(LoginActivity.class);
                } else {
                    startAnimActivity(SettingActivity.class);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.iv_nav_avatar || id == R.id.tv_nav_nick || id == R.id.tv_nav_signature){
            mView.closeDrawers();
            User u = DaoBaseImpl.getInstance().getCurrentUser();
            if(u == null){
                startAnimActivity(LoginActivity.class);
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user" ,u);
                startAnimActivity(UserInfoActivity.class,bundle);
            }
        }
    }

    public void pudateUser(){
        boolean isNetConnected = Network.isConnected(mContext);
        if (!isNetConnected) {
            return;
        }
        User user = DaoBaseImpl.getInstance().getCurrentUser();
        if(user == null) return;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mView.getDrawerLayout().isDrawerVisible(GravityCompat.START)){
                mView.toggleDrawer();
            } else {
                if (Config.ListVideoUtilInstance().backFromFull()) {
                    return true;
                }
                if (firstTime + 2000 > System.currentTimeMillis()) {
                   finish();
                } else {
                    ShowToast(R.string.double_click_logout);
                }
                firstTime = System.currentTimeMillis();
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.navigationEventBus().unregister(this);
        Config.userEventBus().unregister(this);
        Config.ListVideoUtilInstance().releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }


}
