package org.acg12.ui.activity;

import android.os.Bundle;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.TipLayoutView;

import org.acg12.entity.Subject;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.SearchInfoView;

public class SearchInfoActivity extends SkinBaseActivity<SearchInfoView> implements TipLayoutView.OnReloadClick{

    private int id;
    private String title;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
         id = getIntent().getIntExtra("id",0);
         title = getIntent().getExtras().getString("title");

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

    private void requestData(){
        mView.getTipLayoutView().startProgress();
        HttpRequestImpl.getInstance().subjectInfo(490, 0, "", new HttpRequestListener<Subject>() {
            @Override
            public void onSuccess(Subject result) {
                mView.bindData(id , title ,result);
                mView.getTipLayoutView().stopProgress();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
                ShowToast(msg);
                mView.getTipLayoutView().stopProgressOrEmpty();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }


}
