package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Palette;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.views.TabPaletteView;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class TabPaletteFragment extends PresenterFragmentImpl<TabPaletteView>  implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh("");
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh("");
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(mView.getBoardId());
    }

    public void refresh(String pinId){
        HttpRequestImpl.getInstance().paletteList(pinId, new HttpRequestListener<List<Palette>>() {
            @Override
            public void onSuccess(List<Palette> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER) {
                        mView.stopLoading();
                    }
                    mView.bindData(result , refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }


}
