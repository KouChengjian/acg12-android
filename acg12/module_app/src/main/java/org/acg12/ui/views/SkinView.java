package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;

import com.acg12.lib.entity.Skin;
import com.acg12.lib.ui.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.CommonRecycleview;

import org.acg12.R;
import org.acg12.ui.adapter.SkinLoaderAdapter;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    SkinLoaderAdapter skinLoaderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_skin;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.skin));

        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        skinLoaderAdapter = new SkinLoaderAdapter(getContext());
        commonRecycleview.setAdapter(skinLoaderAdapter);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
    }

    public void bindData(){
        skinLoaderAdapter.add(new Skin(0xffD95555 , "姨妈红" , "default"));
        skinLoaderAdapter.add(new Skin(0xfffb7299 , "少女粉" , "skin_pink.skin"));
//        skinLoaderAdapter.add(new Skin(0xfff44336 , "胖次蓝" , "skin_red.skin"));
        skinLoaderAdapter.notifyDataSetChanged();

    }
}
