package com.acg12.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.lib.conf.event.CommonEnum;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.ui.activity.DownloadActivity;
import com.acg12.ui.activity.NewestAlbumActivity;
import com.acg12.ui.activity.NewestNewsActivity;
import com.acg12.ui.activity.SearchActivity;

import com.acg12.R;
import com.acg12.lib.conf.EventConfig;
import com.acg12.lib.cons.Constant;
import com.acg12.entity.Home;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseFragment;
import com.acg12.ui.views.HomeView;

public class HomeFragment extends SkinBaseFragment<HomeView> implements Toolbar.OnMenuItemClickListener ,SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener , AppBarLayout.OnOffsetChangedListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        Home home = DaoBaseImpl.getInstance(mContext).queryHome();
        mView.bindData(home);

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
            EventConfig.get().postCommon(CommonEnum.COMMON_TOGGLE_DRAWER);
        } else if(v.getId() == R.id.btn_home_search){
            startAnimActivity(SearchActivity.class);
        } else if(v.getId() == R.id.btn_newest_news){
            startAnimActivity(NewestNewsActivity.class);
        } else if(v.getId() == R.id.btn_newest_illustration){
            startAnimActivity(NewestAlbumActivity.class);
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
                DaoBaseImpl.getInstance(mContext).saveHome(result);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
