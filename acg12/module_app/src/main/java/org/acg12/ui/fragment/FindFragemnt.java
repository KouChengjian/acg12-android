package org.acg12.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;

import org.acg12.R;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.ui.activity.DownloadActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.FindView;
import org.acg12.widget.SearchPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public class FindFragemnt extends SkinBaseFragment<FindView> implements Toolbar.OnMenuItemClickListener ,
        View.OnClickListener ,SearchPopWindow.OnPopupShowOrDismiss{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
        dynamicAddView(mView.getTabLayout(), mDynamicAttr);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            Config.navigationEventBus().post(true);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search:
                mView.showPopupWindow();
                break;
            case R.id.menu_main_download:
                startAnimActivity(DownloadActivity.class);
                break;
        }
        return false;
    }

    @Override
    public void popupStatus(boolean status) {
        if(status){
            showSoftInputView();
        }else {
            hideSoftInputView();
        }
    }
}
