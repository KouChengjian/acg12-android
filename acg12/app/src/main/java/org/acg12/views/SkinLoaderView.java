package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Skin;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.SkinLoaderAdapter;
import org.acg12.ui.adapter.TabAnimatAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.widget.IRecycleView;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinLoaderView extends ViewImpl {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;

    SkinLoaderAdapter skinLoaderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_skin_loader;
    }

    @Override
    public void created() {
        super.created();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(false);
        skinLoaderAdapter = new SkinLoaderAdapter(getContext());
        mRecyclerView.setAdapter(skinLoaderAdapter);

//        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
//        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
//        mSwipeRefreshLayout.setRefreshing(true);


        for (int i = 0 ; i < 8 ; i++) {
            skinLoaderAdapter.add(new Skin());
        }
        skinLoaderAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
//        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
    }
}
