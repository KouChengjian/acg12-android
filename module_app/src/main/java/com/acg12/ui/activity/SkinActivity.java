package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.utils.skin.entity.Skin;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.ui.adapter.SkinAdapter;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.SkinContract;
import com.acg12.ui.presenter.SkinPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
public class SkinActivity extends BaseMvpActivity<SkinPresenter> implements SkinContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

    SkinAdapter skinAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_skin;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("主题");

        skinAdapter = new SkinAdapter(context());

        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        commonRecycleview.setRefreshEnabled(false);
        commonRecycleview.setAdapter(skinAdapter);

        skinAdapter.add(new Skin(0xffD95555, "姨妈红", "default"));
        skinAdapter.add(new Skin(0xfffb7299, "少女粉", "skin_pink.skin"));
        skinAdapter.add(new Skin(0xff3998e1, "胖次蓝", "skin_blue.skin"));
//        skinLoaderAdapter.add(new Skin(0xff46D29C, "胖次蓝", "skin_blue.skin"));
        commonRecycleview.notifyChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ClickUtil.click(this, toolBarView.getToolbar());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}