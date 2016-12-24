package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.TabAlbumAdapter;
import org.acg12.ui.adapter.TabPaletteAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabPaletteView extends ViewImpl {

    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    TabPaletteAdapter tabPaletteAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_palette;
    }

    @Override
    public void created() {
        super.created();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLoadingMoreEnabled(true);
        tabPaletteAdapter = new TabPaletteAdapter(getContext());
        mRecyclerView.setAdapter(tabPaletteAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Palette> result , boolean refresh){
        if (refresh) {
            tabPaletteAdapter.setList(result);
        } else {
            tabPaletteAdapter.addAll(result);
        }
        tabPaletteAdapter.notifyDataSetChanged();
    }

    public String getBoardId(){
        return tabPaletteAdapter.getList().get(tabPaletteAdapter.getList().size()-1).getBoardId();
    }

    public void stopLoading(){
        mRecyclerView.noMoreLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh)
            mSwipeRefreshLayout.setRefreshing(false);
        else
            mRecyclerView.loadMoreComplete();
        loadNull();
    }

    private void loadNull() {
        List<Palette> mlist = tabPaletteAdapter.getList();
        if (mlist != null && !mlist.isEmpty()) {
            if (loadNullImageview != null && loadNullTextview != null) {
                ViewUtil.setText(loadNullTextview, "");
                if (loadNullImageview.getVisibility() == View.VISIBLE) {
                    loadNullImageview.setVisibility(View.GONE);
                }
            }
        } else {
            if (loadNullImageview == null && loadNullTextview == null) {
                View view = layoutLoadNull.inflate();
                loadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
                loadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
            }
            //loadNullImageview.setImageResource(R.mipmap.ic_error);
            ViewUtil.setText(loadNullTextview, "暂时没有消息");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }
}
