package org.acg12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.net.download.DUtil;
import com.acg12.lib.net.download.DownLoadCallback;
import com.acg12.lib.net.download.DownloadManger;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.loadimage.ImageLoadUtils;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.entity.Album;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.views.PreviewAlbumView;

import java.io.File;
import java.util.List;

public class PreviewAlbumActivity extends BaseActivity<PreviewAlbumView> implements View.OnClickListener , ViewPager.OnPageChangeListener , Toolbar.OnMenuItemClickListener {

    private int position;
    public static List<Album> mList = null;
    private DownloadManger downloadManger;

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
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveImage();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            aminFinish();
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
        HttpRequestImpl.getInstance().albumList(currentUser(),pinId, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                mList.addAll(result);
                mView.addList(result);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }

    public void saveImage(){
        Album album = mView.getList().get(position);
        String url = album.getImageUrl().replace("_fw658" , "");
        LogUtil.e(url+"====");
        String name = System.currentTimeMillis()+"";

        downloadManger = DUtil.initDownloadBuilder(mContext)
                .url("http://shouji.360tpcdn.com/170602/c5966f391f74ef437042aae4976987cd/com.qiyi.video_80880.apk")
                .path(Environment.getExternalStorageDirectory() + "/Pictures/漫友/")
                .name(name + ".apk")
                .childTaskCount(1)
                .build()
                .start(new DownLoadCallback() {

                    @Override
                    public void onStart(long currentSize, long totalSize, float progress) {
//						mTip.setText(name + "：准备下载中...");
//						progressBar.setProgress((int) progress);
//						mProgress.setText(IOUtils.formatSize(currentSize) + " / " + IOUtils.formatSize(totalSize) + "--------" + progress + "%");
                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize, float progress) {
//						showCustomProgressNotify(mProgress);
                        LogUtil.e((int) progress+"===");
//						mTip.setText(name + "：下载中...");
//						progressBar.setProgress((int) progress);
//						mProgress.setText(IOUtils.formatSize(currentSize) + " / " + IOUtils.formatSize(totalSize) + "--------" + progress + "%");
                    }

                    @Override
                    public void onPause() {
//						mTip.setText(name + "：暂停中...");
                    }

                    @Override
                    public void onCancel() {
//						mTip.setText(name + "：已取消...");
                    }

                    @Override
                    public void onFinish(File file) {
//						mTip.setText(name + "：下载完成...");

                    }

                    @Override
                    public void onWait() {

                    }

                    @Override
                    public void onError(String error) {
                        LogUtil.e(error);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList = null;
        ImageLoadUtils.clearImageMemoryCache(mContext);
    }



}
