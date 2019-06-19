package com.acg12.ui.activity;


import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.adapter.CaricatureChapterAdapter;
import com.acg12.ui.adapter.CaricatureInfoAdapter;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.CaricatureInfoView;
import com.acg12.widget.caricature.TouchRecyclerView;
import com.acg12.widget.dialog.ModuleDialog;

public class CaricatureInfoActivity extends SkinBaseActivity<CaricatureInfoView> implements TipLayoutView.OnReloadClick, TouchRecyclerView.ITouchCallBack, CaricatureChapterAdapter.OnCaricatureChapterListener, CaricatureInfoAdapter.OnCaricatureInfoListener {

    private int id;
    private int type;
    private int index;
    private String title;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", -1);
        title = getIntent().getStringExtra("title");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.setTitle(title);
        requestChapters();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void click() {
        mView.clickResetMenu();
    }

    @Override
    public void onClickItem(int position) {
        mView.clickResetMenu();
    }

    @Override
    public void onReload() {
        requestChapters();
    }

    @Override
    public void onClickChapter(CaricatureChaptersEntity chaptersEntity, int position) {
        mView.returnAllStatus();
        mView.resetTouchRecyclerView();
        requestChapters(id, chaptersEntity.getIndex(), type, true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_bottom_menu) {
            mView.switchLeftMenu();
        } else if (view.getId() == R.id.tv_bottom_brightness) {
            mView.openLightPop();
        } else if (view.getId() == R.id.tv_bottom_switch_screen) {
            ScreenUtils.switchScreen(this);
        } else if (view.getId() == R.id.tv_bottom_switch_module) {
            openModuleDialog();
        }
    }

    private void requestChapters() {
        HttpRequestImpl.getInstance().caricatureChapters(id, type, new HttpRequestListener<CaricatureEntity>() {
            @Override
            public void onSuccess(CaricatureEntity result) {
                mView.bindCaricatureData(result);
                requestChapters(id, 1, type, true);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                mView.getTipLayoutView().showEmptyOrRefresh();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    private void requestChapters(int id, final int index, int type, final boolean refresh) {
        this.index = index;
        HttpRequestImpl.getInstance().caricatureChaptersPage(id, index, type, new HttpRequestListener<CaricatureChaptersEntity>() {
            @Override
            public void onSuccess(CaricatureChaptersEntity result) {
                mView.bindChaptersData(result, index, refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    private void openModuleDialog() {
        mView.switchBAndTMenu();
        ModuleDialog moduleDialog = new ModuleDialog(this);
        moduleDialog.show();
        moduleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initModule(SPUtils.getInstance(Parameter.SP_CONFIG).getInt(Parameter.SP_PREVIEW_MODE, 0));
                mView.initPreLoaderAdapter();
                requestChapters(id, index, type, true);
            }
        });
    }


    public void onLoadMoreRequested(int index) {
        requestChapters(id, index, type, false);
    }

    @Override
    public void onBackPressed() {
        if (!mView.returnAllStatus()) super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
