package com.acg12.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.constant.Constant;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.ui.adapter.view.CaricatureInfoViewHolder;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 16:22
 * Description:
 */
public class CaricatureInfoAdapter extends CommonRecyclerAdapter<CaricatureChaptersEntity> implements ListPreloader.PreloadModelProvider<CaricatureChaptersEntity>{

    private int layoutRes;
    private int screenWidth;
    //预览模式
    private int module;
    //加载图片的宽度
    private int tagWidth;
    //加载图片的高度
    private int tagHeight;
    //记录每一个集数的对象
    private final SparseArray<CaricatureEntity> mComicPreViewSparseArray = new SparseArray<>();
    //预加载的ViewPreLoad
    private ViewPreloadSizeProvider<CaricatureChaptersEntity> preloadSizeProvider;


    public CaricatureInfoAdapter(Context mContext, int layoutRes, ViewPreloadSizeProvider preloadSizeProvider) {
        super(mContext);
        this.layoutRes = layoutRes;
        this.preloadSizeProvider = preloadSizeProvider;
        module = PreferencesUtils.getInt(mContext, Constant.XML_KEY_CARICATURE_MODE, 0);
        tagHeight = module == 0 ? PixelUtil.dp2px(mContext, 500f) : ScreenUtils.getScreenHeight(mContext) / 2;
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        tagWidth = screenWidth / 2;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CaricatureInfoViewHolder(getItemView(R.layout.item_caricature_vertical, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CaricatureInfoViewHolder) holder).bindData(mContext, getList(), position);
    }

    @NonNull
    @Override
    public List<CaricatureChaptersEntity> getPreloadItems(int position) {
        return null;
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull CaricatureChaptersEntity item) {
        return null;
    }

//    private void autoLoadMore(int position) {
//
//    }
}
