package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SkinView;

import java.util.ArrayList;
import java.util.List;

public class SkinActivity extends SkinBaseActivity<SkinView> implements View.OnClickListener {

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
//        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
//        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
//        dynamicAddView(mView.getToolBarView(), mDynamicAttr);

        mView.bindData();
    }

}
