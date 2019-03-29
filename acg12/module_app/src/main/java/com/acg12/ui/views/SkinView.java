package com.acg12.ui.views;

import android.support.v7.widget.Toolbar;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.skin.entity.Skin;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.ui.adapter.SkinLoaderAdapter;
import com.google.android.exoplayer.C;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    SkinLoaderAdapter skinLoaderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_skin;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("主题");

        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        commonRecycleview.setRefreshEnabled(false);
        skinLoaderAdapter = new SkinLoaderAdapter(getContext());
        commonRecycleview.setAdapter(skinLoaderAdapter);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar());
    }

    public ToolBarView getToolBarView() {
        return toolBarView;
    }

    public void bindData() {
        skinLoaderAdapter.add(new Skin(0xffD95555, "姨妈红", "default"));
        skinLoaderAdapter.add(new Skin(0xfffb7299, "少女粉", "skin_pink.skin"));
        skinLoaderAdapter.add(new Skin(0xff3998e1, "胖次蓝", "skin_blue.skin"));
//        skinLoaderAdapter.add(new Skin(0xff46D29C, "胖次蓝", "skin_blue.skin"));
        commonRecycleview.notifyChanged();
    }
}
