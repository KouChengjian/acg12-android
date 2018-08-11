package org.acg12.ui.views;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.acg12.utlis.CollapsingToolbarLayoutState;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.R;
import org.acg12.entity.Home;
import org.acg12.ui.adapter.HomeTagAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/10.
 */
public class HomeView extends ViewImpl {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.home_toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_container)
    protected RelativeLayout layout_container;
    @BindView(R.id.iv_home_cover)
    ImageView iv_home_cover;
    @BindView(R.id.btn_home_search)
    View btn_home_search;
    @BindView(R.id.btn_newest_news)
    TextView btn_newest_news;
    @BindView(R.id.btn_newest_illustration)
    TextView btn_newest_illustration;
    @BindView(R.id.common_recyclerview)
    RecyclerView commonRecycleview;

    HomeTagAdapter homeTagAdapter;

    MenuItem searchMenu;
    CollapsingToolbarLayoutState state = CollapsingToolbarLayoutState.EXPANDED;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_home);
        toolbar.inflateMenu(R.menu.menu_main);
//        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#ffffff"));

        searchMenu = toolbar.getMenu().findItem(R.id.menu_main_search);
        searchMenu.setVisible(false);

        ViewGroup.LayoutParams layoutParams = layout_container.getLayoutParams();
        layoutParams.height = PixelUtil.getScreenWidthPx(getContext()) / 4 * 3;
        layout_container.setLayoutParams(layoutParams);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        commonRecycleview.setLayoutManager(staggeredGridLayoutManager);
        homeTagAdapter = new HomeTagAdapter(getContext());
        commonRecycleview.setAdapter(homeTagAdapter);

        swipeRefreshLayout.setColorSchemeResources(com.acg12.lib.R.color.theme_body);
        swipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(getContext(), 50), PixelUtil.dp2px(getContext(), 24));
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar, btn_home_search ,btn_newest_news ,btn_newest_illustration);
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) mPresenter);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        appbar.addOnOffsetChangedListener((AppBarLayout.OnOffsetChangedListener) mPresenter);
    }

    public void bindData(Home home) {
        if (home == null) return;
        ImageLoadUtils.glideLoading(home.getCover(), iv_home_cover);
        homeTagAdapter.getList().clear();
        addObjectList(home.getTagsList());
    }

    public void addObjectList(List list) {
        homeTagAdapter.addAll(list);
        homeTagAdapter.notifyDataSetChanged();
    }

    public void toolbarExpanded() {
        if (state != CollapsingToolbarLayoutState.EXPANDED) {
            state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
            swipeRefreshLayout.setEnabled(true);
        }
    }

    public void toolbarCollapsed() {
        if (state != CollapsingToolbarLayoutState.COLLAPSED) {
            collapsingToolbarLayout.setTitle("");//设置title不显示
            searchMenu.setVisible(true);
            state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
        }
    }

    public void toolbarInternediate() {
        if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
            if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                searchMenu.setVisible(false);
            }
            state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
        }
        swipeRefreshLayout.setEnabled(false);
    }

    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
