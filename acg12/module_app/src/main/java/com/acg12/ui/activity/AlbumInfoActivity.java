package com.acg12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.acg12.lib.conf.EventConfig;
import com.acg12.lib.conf.event.CommonEnum;
import com.acg12.entity.Album;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.AlbumInfoView;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfoActivity extends SkinBaseActivity<AlbumInfoView> {

    private int position;
    private boolean refresh = false;
    private List<Album> mList = null;
    private boolean isRequest = false;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        mList = (ArrayList<Album>) intent.getSerializableExtra("list");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData(mList, refresh);
        mView.scrollToPositionWithOffset(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == -1) {
            backPressed();
        }
    }

    public void requestData() {
        if (isRequest) {
            return;
        }
        isRequest = true;
        HttpRequestImpl.getInstance().albumList(null, mView.getLastId(), new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                mView.bindData(result, refresh);
                EventConfig.get().postCommon(CommonEnum.COMMON_NEWEST_ALBUM, result);
                isRequest = false;
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                isRequest = false;
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }

    public void backPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", mView.updateCurrObject());
        finishResult(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            backPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
