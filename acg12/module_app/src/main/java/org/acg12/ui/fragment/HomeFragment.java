package org.acg12.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.acg12.common.ui.base.BaseFragment;
import com.acg12.kk.listener.HttpRequestListener;
import com.acg12.kk.utils.LogUtil;

import org.acg12.R;
import org.acg12.entity.Home;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.SearchActivity;
import org.acg12.ui.views.HomeView;

public class HomeFragment extends BaseFragment<HomeView> implements SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener , AppBarLayout.OnOffsetChangedListener {

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
        if(v.getId() == R.id.btn_home_search){
            startAnimActivity(SearchActivity.class);
        }
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
