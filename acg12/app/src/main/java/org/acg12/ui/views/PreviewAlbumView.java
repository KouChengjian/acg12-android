package org.acg12.ui.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.widget.ViewPagerFixed;
import org.acg12.widget.dargphoto.DragPhotoView;
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

    ArrayList<View> viewList = new ArrayList<View>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_album;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("图片预览");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
        preAlbumViewpage.addOnPageChangeListener((ViewPager.OnPageChangeListener)mPresenter);
    }

    public void bindData(int position ,List<Album> albumList){
        preAlbumViewpage.setOffscreenPageLimit(3);
        preAlbumViewpage.setAdapter(new DragPagerAdapter(paddingViewList(albumList)));
        preAlbumViewpage.setCurrentItem(position);
    }

    public ArrayList<View> paddingViewList(List<Album> albumList){
        for (int i = 0 , num = albumList.size(); i < num; i++) {
            Album album = albumList.get(i);
            View imageLayout = LayoutInflater.from(getContext()).inflate(R.layout.common_preview_album, null, false);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.page_loading);
            final PhotoView dragPhotoView = (PhotoView) imageLayout.findViewById(R.id.page_image);
            dragPhotoView.setBackgroundColor(0x00ffffff);
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

            viewList.add(imageLayout);
        }
        return viewList;
    }


    class DragPagerAdapter extends PagerAdapter{

        ArrayList<View> viewList;

        public DragPagerAdapter(ArrayList<View> viewList){
            this.viewList =viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
