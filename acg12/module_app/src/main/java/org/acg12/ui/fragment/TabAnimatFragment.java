package org.acg12.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.kk.listener.HttpRequestListener;
import com.acg12.kk.listener.ItemClickSupport;
import com.acg12.kk.widget.IRecycleView;

import org.acg12.conf.Constant;
import org.acg12.entity.Video;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.PlayBungumiActivity;
import com.acg12.common.ui.base.BaseFragment;
import org.acg12.ui.views.TabAnimatView;

import java.util.List;

public class TabAnimatFragment extends BaseFragment<TabAnimatView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    int type = 0;
    int page = 1;
    boolean refresh = true;

    public static TabAnimatFragment newInstance(int type) {
        TabAnimatFragment fragment = new TabAnimatFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        type = getArguments().getInt("type");
        refresh(page);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", mView.getVideo(position));
        startAnimActivity(PlayBungumiActivity.class , bundle);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        page = 1;
        refresh(page);
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page ++;
        refresh(page);
    }

    public void refresh(int page){
        HttpRequestImpl.getInstance().videoList(currentUser(),page+"" , type, new HttpRequestListener<List<Video>>() {
            @Override
            public void onSuccess(List<Video> result) {
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
