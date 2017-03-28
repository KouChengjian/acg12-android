package org.acg12.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;


import com.shuyu.gsyvideoplayer.GSYVideoPlayer;

import org.acg12.R;
import org.acg12.config.Config;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.utlis.skin.entity.AttrFactory;
import org.acg12.utlis.skin.entity.DynamicAttr;
import org.acg12.views.MainView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends PresenterActivityImpl<MainView> implements NavigationView.OnNavigationItemSelectedListener {

    public static long firstTime;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        Config.initListVideoUtil(this);
        Config.navigationEventBus().register(this);
        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.NAVIGATIONVIEW, R.color.theme_primary));
        dynamicAddView(mView.getNavigationView(), mDynamicAttr);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toggleDrawer(Boolean toggle){
        mView.toggleDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mView.closeDrawers();
        switch (item.getItemId()){
            case R.id.nav_home:
                mView.onTabSelect(0);
                break;
            case R.id.nav_star:
                startAnimActivity(CollectActivity.class);
                break;
            case R.id.nav_down:
                startAnimActivity(DownloadActivity.class);
                break;
            case R.id.nav_history:
                startAnimActivity(RecordActivity.class);
                break;
            case R.id.nav_color_lens:
                //startAnimActivity(SkinActivity.class);
                mView.onTabSelect(1);
                break;
            case R.id.nav_settings:
                startAnimActivity(SettingActivity.class);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(mView.getDrawerLayout().isDrawerVisible(GravityCompat.START)){
            mView.toggleDrawer();
        } else {
            if (Config.ListVideoUtilInstance().backFromFull()) {
                return;
            }
            if (firstTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                ShowToast(R.string.double_click_logout);
            }
            firstTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.navigationEventBus().unregister(this);
        Config.ListVideoUtilInstance().releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }

}
