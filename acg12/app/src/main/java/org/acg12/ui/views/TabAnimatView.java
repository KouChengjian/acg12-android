package org.acg12.ui.views;

import android.graphics.Point;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.conf.Config;
import org.acg12.listener.SampleListener;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.TabAnimatAdapter;
import org.acg12.ui.adapter.base.TabAnimatViewHolder;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatView extends ViewImpl {

    @BindView(R.id.video_full_container)
    FrameLayout videoFullContainer;
    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    TabAnimatAdapter tabAnimatAdapter;
    ListVideoUtil listVideoUtil;
    LinearLayoutManager layoutManager;
    int lastVisibleItem;
    int firstVisibleItem;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_animat;
    }

    @Override
    public void created() {
        super.created();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        tabAnimatAdapter = new TabAnimatAdapter(getContext());
        mRecyclerView.setAdapter(tabAnimatAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);


        listVideoUtil = Config.ListVideoUtilInstance();
        listVideoUtil.setFullViewContainer(videoFullContainer);
        tabAnimatAdapter.setListVideoUtil(listVideoUtil);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        //ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
        setRecyclerViewListener();
    }

    public void bindData(List<Video> result , boolean refresh){
        if (refresh) {
            tabAnimatAdapter.setList(result);
        } else {
            tabAnimatAdapter.addAll(result);
        }
        tabAnimatAdapter.notifyDataSetChanged();
    }

    public Video getVideo(int position){
        return tabAnimatAdapter.getList().get(position);
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
        List<Video> mlist = tabAnimatAdapter.getList();
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
            ViewUtil.setText(loadNullTextview, "暂时没有信息");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }

    public ListVideoUtil getListVideoUtil(){
        return listVideoUtil;
    }

    public void setRecyclerViewListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
                Log.e("TAG","firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(TabAnimatViewHolder.TAG)) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    Log.e("TAG","position="+position +"   ======  listVideoUtil.getPlayTAG()= " +
                            listVideoUtil.getPlayTAG() +"  ==== TabAnimatViewHolder.TAG "  +TabAnimatViewHolder.TAG);
                    //不可视的是时候
                    Log.e("TAG",position +"====="+ firstVisibleItem);
                    Log.e("TAG",position +"====="+ lastVisibleItem);
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        Log.e("TAG","true1");
                        //如果是小窗口就不需要处理
                        Log.e("TAG",!listVideoUtil.isSmall() +"====="+ !listVideoUtil.isFull());
                        if (!listVideoUtil.isSmall() && !listVideoUtil.isFull()) {
                            Log.e("TAG","true2");
                            //小窗口
                            int size = CommonUtil.dip2px(getContext(), 150);
                            //actionbar为true才不会掉下面去
                            listVideoUtil.showSmallVideo(new Point(size, size), false, false);
                        }
                    } else {
                        Log.e("TAG","true3");
                        if (listVideoUtil.isSmall()) {
                            listVideoUtil.smallVideoToNormal();
                        }
                    }
                }
            }
        });

        //小窗口关闭被点击的时候回调处理回复页面
        listVideoUtil.setVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("Duration " + listVideoUtil.getDuration() + " CurrentPosition " + listVideoUtil.getCurrentPositionWhenPlaying());
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals("TT2")) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        listVideoUtil.releaseVideoPlayer();
                        tabAnimatAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

    }
}
