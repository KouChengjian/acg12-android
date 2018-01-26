package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.views.SearchInfoView;

import java.util.ArrayList;
import java.util.List;

public class SearchInfoActivity extends BaseActivity<SearchInfoView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        String title = getIntent().getExtras().getString("title");
        mView.bindData(title);

        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
        dynamicAddView(mView.getTabLayout(), mDynamicAttr);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        }
    }
}
