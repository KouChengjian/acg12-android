package org.acg12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.acg12.bean.Album;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PreviewAlbumView;
import org.acg12.utlis.LogUtil;

import java.util.List;

public class PreviewAlbumActivity extends PresenterActivityImpl<PreviewAlbumView> implements View.OnClickListener , ViewPager.OnPageChangeListener {

    int position;
    List<Album> albumList ;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        albumList = (List<Album>)intent.getSerializableExtra("albumList");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData(position,albumList );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
       this.position = position;
        int albumTotal = albumList.size();
        if(albumTotal - position < 5){
            //refresh(albumList.get(albumTotal - 1).getPinId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    public void refresh(String pinId){
        HttpRequestImpl.getInstance().albumList(pinId, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                albumList.addAll(result);
                mView.bindData(position,albumList );
//                if (result.size() != 0 && result.get(result.size() - 1) != null) {
//                    if (result.size() < Constant.LIMIT_PAGER) {
//                        mView.stopLoading();
//                    }
//                    mView.bindData(result , refresh);
//                }
//                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
//                LogUtil.e(mTag , msg);
//                ShowToastView(msg);
//                mView.stopRefreshLoadMore(refresh);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
