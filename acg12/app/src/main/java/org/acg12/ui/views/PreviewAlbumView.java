package org.acg12.ui.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.activity.PreviewImageActivity;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.ViewPagerFixed;
import org.acg12.widget.dargphoto.PhotoView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/24.
 */
public class PreviewAlbumView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.preAlbum_viewpage)
    ViewPagerFixed preAlbumViewpage;

    ArrayList<View> mList = new ArrayList<>();
    DragPagerAdapter dragPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_album;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("预览");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
        preAlbumViewpage.addOnPageChangeListener((ViewPager.OnPageChangeListener)mPresenter);
    }

    public void bindData(int position ,List<Album> albumList){
        paddingViewList(albumList);
        dragPagerAdapter = new DragPagerAdapter(mList);
        preAlbumViewpage.setOffscreenPageLimit(1);
        preAlbumViewpage.setAdapter(dragPagerAdapter);
        preAlbumViewpage.setCurrentItem(position);
    }

    public ArrayList<View> paddingViewList(List<Album> albumList){
        for (int i = 0 , num = albumList.size(); i < num; i++) {
            View view = initItemView(albumList.get(i) , i);
            mList.add(view);
        }
        return mList;
    }

    public View initItemView(Album album, int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_preview_album, null, false);
        return view;
    }

    public void  addList(List<Album> albumList){
//        LogUtil.e(albumList.size()+"====");
        paddingViewList(albumList);
        dragPagerAdapter.setListViews(mList);
        dragPagerAdapter.notifyDataSetChanged();
    }

    public List<View> getList(){
        return mList;
    }

    class DragPagerAdapter extends PagerAdapter{

        private int size;
        ArrayList<View> mList = new ArrayList<>();

        public DragPagerAdapter(ArrayList<View> mList){
            this.mList = mList;
            size = mList == null ? 0 : mList.size();
        }

        public void setListViews(ArrayList<View> mList) {// 自己写的一个方法用来添加数据
            this.mList = mList;
            size = mList == null ? 0 : mList.size();
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            LogUtil.e("instantiateItem = "+position+"====");
            View view = mList.get(position % size);
            ProgressBar spinner = (ProgressBar) view.findViewById(R.id.page_loading);
            ImageView dragPhotoView = (ImageView) view.findViewById(R.id.page_image);
            TextView pageText = (TextView)view.findViewById(R.id.page_text);
            if( PreviewAlbumActivity.mList.size() > position){
                Album album = PreviewAlbumActivity.mList.get(position);
                ViewUtil.setText(pageText , album.getContent());
                //loaderImage(album ,spinner , dragPhotoView);
            }

            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            LogUtil.e("destroyItem = "+position+"====");
            container.removeView(mList.get(position % size));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void loaderImage(final Album album ,final ProgressBar spinner ,final ImageView dragPhotoView){
            spinner.setVisibility(View.GONE);
            dragPhotoView.setVisibility(View.VISIBLE);
            ImageLoadUtils.glideLoading(getContext() , album.getImageUrl() , dragPhotoView);
//            ImageLoadUtils.universalLoading(album.getImageUrl() , dragPhotoView , new SimpleImageLoadingListener(){
//                @Override
//                public void onLoadingStarted(String imageUri,View view) {
//                    spinner.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onLoadingFailed(String imageUri,View view, FailReason failReason) {
//                    spinner.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri,	View view, Bitmap loadedImage) {
//                    spinner.setVisibility(View.GONE);
//                    dragPhotoView.setVisibility(View.VISIBLE);
//                }
//            });

            dragPhotoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url",album.getImageUrl());
                    ViewUtil.startAnimActivity(getContext() , PreviewImageActivity.class,bundle);
                }
            });
        }

    }
}
