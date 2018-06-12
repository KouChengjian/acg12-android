package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.conf.Constant;
import org.acg12.entity.Palette;
import org.acg12.net.impl.HomeRequestImpl;
import org.acg12.ui.activity.PreviewPaletteActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.TabPaletteView;

import java.util.List;

public class TabPaletteFragment extends SkinBaseFragment<TabPaletteView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;

    public static TabPaletteFragment newInstance() {
        TabPaletteFragment fragment = new TabPaletteFragment();
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh("");
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("palette",mView.getPalette(position));
        startAnimActivity(PreviewPaletteActivity.class , bundle);
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
        HomeRequestImpl.getInstance().paletteList(currentUser(),pinId, new HttpRequestListener<List<Palette>>() {
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
