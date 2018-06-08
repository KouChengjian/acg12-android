package org.acg12.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;

import org.acg12.R;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.entity.Home;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.DownloadActivity;
import org.acg12.ui.activity.NewestIllustrationActivity;
import org.acg12.ui.activity.NewestNewsActivity;
import org.acg12.ui.activity.SearchActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.HomeView;

public class HomeFragment extends SkinBaseFragment<HomeView> implements Toolbar.OnMenuItemClickListener ,SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener , AppBarLayout.OnOffsetChangedListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        },2 * 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == Constant.TOOLBAR_ID){
            Config.navigationEventBus().post(true);
        } else if(v.getId() == R.id.btn_home_search){
            startAnimActivity(SearchActivity.class);
        } else if(v.getId() == R.id.btn_newest_news){
            startAnimActivity(NewestNewsActivity.class);
        } else if(v.getId() == R.id.btn_newest_illustration){
            startAnimActivity(NewestIllustrationActivity.class);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search:
                startAnimActivity(SearchActivity.class);
                break;
            case R.id.menu_main_download:
                startAnimActivity(DownloadActivity.class);
                break;
        }
        return false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            mView.toolbarExpanded();
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            mView.toolbarCollapsed();
        } else {
            mView.toolbarInternediate();
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh(){
        HttpRequestImpl.getInstance().index(currentUser(), new HttpRequestListener<Home>() {
            @Override
            public void onSuccess(Home result) {
                mView.bindData(result);
                mView.stopRefreshing();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
                mView.stopRefreshing();
            }
        });
    }



}
