package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.Home;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.HomeAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.widget.IRecycleView;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/10.
 */
public class HomeView extends ViewImpl {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void created() {
        super.created();


    }


}
