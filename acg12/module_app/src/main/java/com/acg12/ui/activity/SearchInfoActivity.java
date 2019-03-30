package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.entity.Subject;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SearchInfoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchInfoActivity extends SkinBaseActivity<SearchInfoView> implements TipLayoutView.OnReloadClick {

    private int id;
    private int type;
    private String title ,typeName;
    private Subject subject;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getStringExtra("title");
        typeName = getIntent().getStringExtra("typeName");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
        dynamicAddView(mView.getToolBarView(), mDynamicAttr);

//        List<DynamicAttr> mDynamicAttr1 = new ArrayList<>();
//        mDynamicAttr1.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
//        dynamicAddView(mView.getTabLayout(), mDynamicAttr1);

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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_menu_collection) {
            if (subject.getIsCollect() == 1) {
                delCollect();
            } else {
                addCollect();
            }
        }
    }

    private void requestData() {
        mView.startProgress();
        HttpRequestImpl.getInstance().subjectInfo(id, type, title, new HttpRequestListener<Subject>() {
            @Override
            public void onSuccess(Subject result) {
                subject = result;
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

    private void addCollect() {
        startLoading("收藏中...");
        Map<String, Object> params = new HashMap<>();
        params.put("relevanceId", id);
        params.put("type", type);
        params.put("typeName", typeName);
        params.put("image", subject.getImage());
        params.put("name", subject.getName());
        params.put("nameCn", subject.getNameCn());
        HttpRequestImpl.getInstance().collectSubjectAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                subject.setIsCollect(1);
                mView.setCollectStatus(subject.getIsCollect());
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    private void delCollect() {
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectSubjectDel(id, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                subject.setIsCollect(0);
                mView.setCollectStatus(subject.getIsCollect());
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }
}
