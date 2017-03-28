package org.acg12.views;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.ui.fragment.TabAnimatFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class CollectView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TabAnimatFragment tabMADAMVFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.colloct));

        tabMADAMVFragment = TabAnimatFragment.newInstance(0);
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, tabMADAMVFragment).show(tabMADAMVFragment).commit();

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
    }
}
