package org.acg12.ui.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acg12.lib.ui.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.loadimage.ImageLoadUtils;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.entity.Album;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.activity.PreviewImageActivity;
import org.acg12.widget.MyImageView;
import org.acg12.widget.ViewPagerFixed;

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
        toolbar.inflateMenu(R.menu.menu_save);

        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.include_preview_album, null, false);
            ProgressBar spinner = (ProgressBar) view.findViewById(R.id.page_loading);
            MyImageView dragPhotoView = (MyImageView) view.findViewById(R.id.page_image);
            TextView pageText = (TextView) view.findViewById(R.id.page_text);
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
        PresenterHelper.click(mPresenter, toolbar);
        preAlbumViewpage.addOnPageChangeListener((ViewPager.OnPageChangeListener) mPresenter);
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) mPresenter);
    }

    public void bindData(int position, List<Album> mList) {
        albumList.addAll(mList);
        dragPagerAdapter = new DragPagerAdapter();
        preAlbumViewpage.setOffscreenPageLimit(1);
        preAlbumViewpage.setAdapter(dragPagerAdapter);
        preAlbumViewpage.setCurrentItem(position);
    }

    public void addList(List<Album> mList) {
        albumList.addAll(mList);
        dragPagerAdapter.setListViews();
        dragPagerAdapter.notifyDataSetChanged();
    }

    public List<Album> getList() {
        return albumList;
    }

    class DragPagerAdapter extends PagerAdapter {

        private int size;

        public DragPagerAdapter() {
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
//            LogUtil.e("instantiateItem = "+position+"====");

            int i = position % 4;
            final DragView dragView = mDragView.get(i);
            if (PreviewAlbumActivity.mList.size() > position) {
                Album album = albumList.get(position);
                ViewUtil.setText(dragView.getPageText(), album.getContent());
                loaderImage(album, dragView.getSpinner(), dragView.getDragPhotoView());
            }

            container.addView(dragView.getView(), 0);
            return dragView.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int i = position % 4;
            final DragView dragView = mDragView.get(i);
            ImageLoadUtils.releaseImageViewResouce(dragView.getDragPhotoView());
            dragView.getDragPhotoView().setOnClickListener(null);
            container.removeView(dragView.getView());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void loaderImage(final Album album, final ProgressBar spinner, final ImageView dragPhotoView) {
            spinner.setVisibility(View.GONE);
            dragPhotoView.setVisibility(View.VISIBLE);
            ImageLoadUtils.glideLoading(getContext(), album.getImageUrl(), new GlideDrawableImageViewTarget(dragPhotoView) {

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    super.onResourceReady(resource, glideAnimation);
                    spinner.setVisibility(View.GONE);
                    dragPhotoView.setVisibility(View.VISIBLE);
                }
            });

            dragPhotoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", album.getImageUrl());
                    ViewUtil.startAnimActivity(((Activity) getContext()), PreviewImageActivity.class, bundle, Constant.RESULT_ACTIVITY_REG_DEFAULT);
                }
            });
        }
    }

    class DragView {
        private View view;
        private ProgressBar spinner;
        private MyImageView dragPhotoView;
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

        public MyImageView getDragPhotoView() {
            return dragPhotoView;
        }

        public void setDragPhotoView(MyImageView dragPhotoView) {
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
