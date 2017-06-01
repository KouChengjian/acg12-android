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
import org.acg12.widget.DragImageView;
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

    ArrayList<DragView> mDragView = new ArrayList<>();
    ArrayList<Album> albumList = new ArrayList<>();
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

        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.common_preview_album, null, false);
            ProgressBar spinner = (ProgressBar) view.findViewById(R.id.page_loading);
            DragImageView dragPhotoView = (DragImageView) view.findViewById(R.id.page_image);
            TextView pageText = (TextView)view.findViewById(R.id.page_text);
            DragView dragView = new DragView();
            dragView.setView(view);
            dragView.setDragPhotoView(dragPhotoView);
            dragView.setSpinner(spinner);
            dragView.setPageText(pageText);
            mDragView.add(dragView);
        }
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
        preAlbumViewpage.addOnPageChangeListener((ViewPager.OnPageChangeListener)mPresenter);
    }

    public void bindData(int position ,List<Album> mList){
        albumList.addAll(mList);
        dragPagerAdapter = new DragPagerAdapter();
        preAlbumViewpage.setOffscreenPageLimit(1);
        preAlbumViewpage.setAdapter(dragPagerAdapter);
        preAlbumViewpage.setCurrentItem(position);
    }

//    public ArrayList<DragView> paddingViewList(List<Album> albumList){
//        for (int i = 0 , num = albumList.size(); i < num; i++) {
//            View view = initItemView(albumList.get(i) , i);
//            DragView dragView = new DragView();
//            dragView.setView(view);
//            mList.add(dragView);
//        }
//        return mList;
//    }

//    public View initItemView(Album album, int position) {
//
//        return view;
//    }

    public void  addList(List<Album> mList){
        albumList.addAll(mList);
        dragPagerAdapter.setListViews();
        dragPagerAdapter.notifyDataSetChanged();
    }

    public List<Album> getList(){
        return albumList;
    }

    class DragPagerAdapter extends PagerAdapter{

        private int size;

        public DragPagerAdapter(){
            size = albumList == null ? 0 : albumList.size();
        }

        public void setListViews() { // 自己写的一个方法用来添加数据
            size = albumList == null ? 0 : albumList.size();
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LogUtil.e("instantiateItem = "+position+"====");

            int i = position % 4;
            final DragView dragView = mDragView.get(i);
            if( PreviewAlbumActivity.mList.size() > position){
                Album album = albumList.get(position);
                ViewUtil.setText(dragView.getPageText() , album.getContent());
                loaderImage(album ,dragView.getSpinner() , dragView.getDragPhotoView());
            }

            container.addView(dragView.getView(), 0);
            return dragView.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LogUtil.e("destroyItem = "+position+"====");
            int i = position % 4;
            final DragView dragView = mDragView.get(i);
            ImageLoadUtils.releaseImageViewResouce(dragView.getDragPhotoView());
            container.removeView(dragView.getView());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void loaderImage(final Album album ,final ProgressBar spinner ,final ImageView dragPhotoView){
            spinner.setVisibility(View.GONE);
            dragPhotoView.setVisibility(View.VISIBLE);
//            ImageLoadUtils.glideLoading(getContext() , album.getImageUrl() , dragPhotoView);
            ImageLoadUtils.universalLoading(album.getImageUrl() , dragPhotoView , new SimpleImageLoadingListener(){
                @Override
                public void onLoadingStarted(String imageUri,View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri,View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri,	View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                    dragPhotoView.setVisibility(View.VISIBLE);
                }
            });

//            dragPhotoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url",album.getImageUrl());
//                    ViewUtil.startAnimActivity(getContext() , PreviewImageActivity.class,bundle);
//                }
//            });
        }
    }

    class DragView {
        private View view;
        private ProgressBar spinner;
        private DragImageView dragPhotoView;
        private TextView pageText;

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public ProgressBar getSpinner() {
            return spinner;
        }

        public void setSpinner(ProgressBar spinner) {
            this.spinner = spinner;
        }

        public DragImageView getDragPhotoView() {
            return dragPhotoView;
        }

        public void setDragPhotoView(DragImageView dragPhotoView) {
            this.dragPhotoView = dragPhotoView;
        }

        public TextView getPageText() {
            return pageText;
        }

        public void setPageText(TextView pageText) {
            this.pageText = pageText;
        }
    }
}
