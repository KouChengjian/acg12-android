package com.acg12.ui.activity;

import android.os.Bundle;

import com.acg12.R;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.CollectView;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends SkinBaseActivity<CollectView> {

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
        dynamicAddView(mView.getToolBarView(), mDynamicAttr);

        List<DynamicAttr> mDynamicAttr1 = new ArrayList<>();
        mDynamicAttr1.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
        dynamicAddView(mView.getTabLayout(), mDynamicAttr1);
    }

}
