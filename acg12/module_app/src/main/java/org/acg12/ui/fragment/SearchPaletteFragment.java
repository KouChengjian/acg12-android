package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;

import org.acg12.conf.Constant;
import org.acg12.entity.Palette;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.activity.PreviewPaletteActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.SearchPaletteView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPaletteFragment extends SkinBaseFragment<SearchPaletteView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener ,CommonRecycleview.IRecycleUpdataListener{

    private String title = "";
    private int page = 1;
    private boolean refresh = true;

    public static SearchPaletteFragment newInstance(String title) {
        SearchPaletteFragment fragment = new SearchPaletteFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        title = getArguments().getString("title");
        refresh(title, page);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("palette", mView.getPalette(position));
        startAnimActivity(PreviewPaletteActivity.class, bundle);
    }

    @Override
    public void onReload() {
//        mView.resetLoading();
        refresh(title, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        refresh(title, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh = true;
        refresh(title, page);
    }

    public void refresh(String key, int page) {
        HttpRequestImpl.getInstance().searchPalette(currentUser(), key, page + "", new HttpRequestListener<List<Palette>>() {
            @Override
            public void onSuccess(List<Palette> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER) {
                        mView.stopLoading();
                    }
                    mView.bindData(result, refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag, msg);
//                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }

}
