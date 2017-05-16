package org.acg12.ui.views;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.LogUtil;
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
        toolbar.setTitle("图片预览");
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

    public View initItemView(Album album ,int position){
//      View view = LayoutInflater.from(getContext()).inflate(R.layout.common_preview_album, null, false);
        TextView view = new TextView(getContext());// 构造textView对象
        view.setText("第" + position + "页");// 设置文字
        // 设置布局
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
//        // 设置位置
        view.setGravity(Gravity.CENTER);
//            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.page_loading);
//            final PhotoView dragPhotoView = (PhotoView) imageLayout.findViewById(R.id.page_image);
//            dragPhotoView.setBackgroundColor(0x00ffffff);
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

        return view;
    }

    public void  addList(List<Album> albumList){
        LogUtil.e(albumList.size()+"====");
        paddingViewList(albumList);
//        mList.addAll(paddingViewList(albumList));
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
        public Object instantiateItem(View container, int position) {
//            LogUtil.e("instantiateItem = "+position+"====");
            try {
                ((ViewPager) container).addView(mList.get(position % size), 0);
            } catch (Exception e) {
                //Log.e("zhou", "exception：" + e.getMessage());
            }
            return mList.get(position % size);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
//            LogUtil.e("destroyItem = "+position+"====");
            ((ViewPager) container).removeView(mList.get(position % size));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
