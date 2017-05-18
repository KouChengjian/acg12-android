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
    public static List<Album> mList = null;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData(position,mList);
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
//        LogUtil.e("position = "+position);
        if (position == mView.getList().size()-1){
            ShowToast("正在加载更多");
            int total = mList.size();
            refresh(mList.get(total - 1).getPinId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    public void refresh(String pinId){
        HttpRequestImpl.getInstance().albumList(pinId, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                mList.addAll(result);
                mView.addList(result);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
//                LogUtil.e(msg);
//                LogUtil.e(mTag , msg);
//                ShowToastView(msg);
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
