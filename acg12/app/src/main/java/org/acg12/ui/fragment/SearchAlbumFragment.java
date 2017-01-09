package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.R;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.views.SearchAlbumView;
import org.acg12.widget.IRecycleView;

public class SearchAlbumFragment extends PresenterFragmentImpl<SearchAlbumView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener{


    public static SearchAlbumFragment newInstance(String title) {
        SearchAlbumFragment fragment = new SearchAlbumFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
