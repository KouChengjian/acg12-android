package com.acg12.ui.activity;

import android.os.Bundle;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.TipLayoutView;

import com.acg12.entity.Subject;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SearchInfoView;

public class SearchInfoActivity extends SkinBaseActivity<SearchInfoView> implements TipLayoutView.OnReloadClick {

    private int id;
    private int type;
    private String title;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getExtras().getString("title");
        mView.setTitle(title);
        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onReload() {
        requestData();
    }

    private void requestData() {
        mView.startProgress();
        HttpRequestImpl.getInstance().subjectInfo(id, type, title, new HttpRequestListener<Subject>() {
            @Override
            public void onSuccess(Subject result) {
                if (result.getName() == null || result.getName().isEmpty()) {
                    mView.stopProgressOrError();
                } else {
                    mView.bindData(id, type, title, result);
                    mView.stopProgress();
                }
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
                ShowToast(msg);
                mView.stopProgressOrError();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }
}
