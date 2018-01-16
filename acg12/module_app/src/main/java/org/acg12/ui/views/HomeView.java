package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.acg12.kk.ui.ViewImpl;
import com.acg12.kk.ui.base.PresenterHelper;
import com.acg12.kk.utils.PixelUtil;
import com.acg12.kk.widget.CommonRecycleview;

import org.acg12.R;
import org.acg12.ui.adapter.HomeTagAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/10.
 */
public class HomeView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

    protected RelativeLayout layout_container;
    View btn_home_search;

    HomeTagAdapter homeTagAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void created() {
        super.created();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.include_home_header, null);
        layout_container = (RelativeLayout) header.findViewById(R.id.layout_container);
        btn_home_search = header.findViewById(R.id.btn_home_search);
        ViewGroup.LayoutParams layoutParams = layout_container.getLayoutParams();
        layoutParams.height = PixelUtil.getScreenWidthPx(getContext()) / 4 * 3;
        layout_container.setLayoutParams(layoutParams);

        commonRecycleview.addHeader(header, true);
        commonRecycleview.setStaggeredGridLayoutManager();
        homeTagAdapter = new HomeTagAdapter(getContext());
        commonRecycleview.setAdapter(homeTagAdapter);
//      commonRecycleview.startRefreshing();


    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , btn_home_search);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
    }

    public void addObjectList(List list) {
        homeTagAdapter.addAll(list);
    }


    public void stopRefreshing() {
        commonRecycleview.stopRefreshLoadMore(true);
    }
}
