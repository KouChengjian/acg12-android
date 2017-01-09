package org.acg12.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import org.acg12.R;
import org.acg12.config.Config;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.MainView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends PresenterActivityImpl<MainView> implements NavigationView.OnNavigationItemSelectedListener {

    public static long firstTime;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        Config.navigationEventBus().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toggleDrawer(Boolean toggle){
        mView.toggleDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mView.closeDrawers();
        switch (item.getItemId()){
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
                startAnimActivity(SkinActivity.class);
                break;
            case R.id.nav_settings:
                startAnimActivity(SettingActivity.class);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
            //System.exit(0);
            finish();
        } else {
            ShowToast(R.string.double_click_logout);
        }
        firstTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.navigationEventBus().unregister(this);
    }

}
