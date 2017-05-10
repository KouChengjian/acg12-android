package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;

import org.acg12.R;
import org.acg12.bean.Skin;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.SkinLoaderAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.widget.IRecycleView;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinView extends ViewImpl {

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
        return R.layout.activity_skin;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.skin));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(false);
        skinLoaderAdapter = new SkinLoaderAdapter(getContext());
        mRecyclerView.setAdapter(skinLoaderAdapter);
        mSwipeRefreshLayout.setEnabled(false);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
//        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
//        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
    }

    public void bindData(){
        skinLoaderAdapter.add(new Skin(0xff3998e1 , "胖次蓝" , "default"));
        skinLoaderAdapter.add(new Skin(0xfffb7299 , "少女粉" , "skin_pink.skin"));
        skinLoaderAdapter.add(new Skin(0xfff44336 , "姨妈红" , "skin_red.skin"));
        skinLoaderAdapter.notifyDataSetChanged();

    }
}
