package org.acg12.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.IRecycleView;

import org.acg12.constant.Constant;
import org.acg12.entity.Album;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.NewestIllustrationView;

import java.util.List;

/**
 * 每日插画
 */
public class NewestIllustrationActivity extends SkinBaseActivity<NewestIllustrationView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener{

    private boolean refresh = true;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh("");
            }
        } , 2 * 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.RESULT_ACTIVITY_REG_DEFAULT){
                int position = data.getExtras().getInt("position");
                List<Album> list = mView.getAlbumList();
                list = PreviewAlbumActivity.mList;
                PreviewAlbumActivity.mList = null;
                mView.MoveToPosition(position);
            }
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(mContext , PreviewAlbumActivity.class  );
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        PreviewAlbumActivity.mList = mView.getAlbumList();
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.RESULT_ACTIVITY_REG_DEFAULT);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh("");
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(mView.getPicId());
    }

    public void refresh(String pinId){
        HttpRequestImpl.getInstance().albumList(currentUser(),pinId, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER_20) {
                        mView.stopLoading();
                    }
                    mView.bindData(result , refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }
}
