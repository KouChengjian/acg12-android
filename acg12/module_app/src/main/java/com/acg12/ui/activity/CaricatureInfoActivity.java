package com.acg12.ui.activity;


import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.adapter.CaricatureChapterAdapter;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.CaricatureInfoView;
import com.acg12.widget.caricature.TouchRecyclerView;

public class CaricatureInfoActivity extends SkinBaseActivity<CaricatureInfoView> implements TouchRecyclerView.ITouchCallBack, CaricatureChapterAdapter.OnCaricatureChapterListener {

    private int id;
    private int type;


    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", -1);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
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
    public void onClickChapter(CaricatureChaptersEntity chaptersEntity, int position) {
        mView.returnAllStatus();
        mView.resetTouchRecyclerView();
        requestChapters(id, chaptersEntity.getIndex(), type);
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
//            mView.openModuleDialog();
        }
    }

    private void requestChapters() {
        HttpRequestImpl.getInstance().caricatureChapters(id, type, new HttpRequestListener<CaricatureEntity>() {
            @Override
            public void onSuccess(CaricatureEntity result) {
                mView.bindCaricatureData(result);
                requestChapters(id, 1, type);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    private void requestChapters(int id, final int index, int type) {
        HttpRequestImpl.getInstance().caricatureChaptersPage(id, index, type, new HttpRequestListener<CaricatureChaptersEntity>() {
            @Override
            public void onSuccess(CaricatureChaptersEntity result) {
                mView.bindChaptersData(result, index, true);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
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
