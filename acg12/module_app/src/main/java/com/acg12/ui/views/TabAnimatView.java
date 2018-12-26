package com.acg12.ui.views;

import android.graphics.Point;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.acg12.entity.Video;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.listener.SampleListener;
import com.acg12.ui.adapter.TabAnimatAdapter;
import com.acg12.ui.adapter.base.TabAnimatViewHolder;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import com.acg12.R;
import com.acg12.entity.Video;
import com.acg12.listener.SampleListener;
import com.acg12.ui.adapter.TabAnimatAdapter;
import com.acg12.ui.adapter.base.TabAnimatViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatView extends ViewImpl {

    @BindView(R.id.video_full_container)
    FrameLayout videoFullContainer;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

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
        layoutManager = commonRecycleview.setLinearLayoutManager();
        tabAnimatAdapter = new TabAnimatAdapter(getContext());
        commonRecycleview.setAdapter(tabAnimatAdapter);
        commonRecycleview.startRefreshing();

//        listVideoUtil = BaseConfig.ListVideoUtilInstance();
//        listVideoUtil.setFullViewContainer(videoFullContainer);
//        tabAnimatAdapter.setListVideoUtil(listVideoUtil);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
//        setRecyclerViewListener();
    }

    public void bindData(List<Video> result , boolean refresh){
        if (refresh) {
            tabAnimatAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            tabAnimatAdapter.addAll(result);
            commonRecycleview.notifyChanged(tabAnimatAdapter.getList().size() - result.size() , tabAnimatAdapter.getList().size());
        }

    }

    public Video getVideo(int position){
        return tabAnimatAdapter.getList().get(position);
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public ListVideoUtil getListVideoUtil(){
        return listVideoUtil;
    }

    public void setRecyclerViewListener(){
        commonRecycleview.getIRecycleView().addOnScrollListener(new RecyclerView.OnScrollListener() {
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
