package org.acg12.ui.fragment;


import android.os.Bundle;

import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.views.HomeView;
import org.acg12.widget.IRecycleView;

public class HomeFragment extends PresenterFragmentImpl<HomeView> implements IRecycleView.LoadingListener{


    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onLoadMore() {

    }
}
