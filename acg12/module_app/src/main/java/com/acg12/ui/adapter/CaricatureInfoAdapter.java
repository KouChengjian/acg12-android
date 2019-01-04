package com.acg12.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.acg12.constant.Constant;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureChaptersPageEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.lib.utils.glide.GlideApp;
import com.acg12.ui.adapter.view.CaricatureInfoViewHolder;
import com.acg12.utlis.ListUtils;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.Collections;
import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 16:22
 * Description:
 */
public class CaricatureInfoAdapter extends CommonRecyclerAdapter<CaricatureChaptersPageEntity> implements ListPreloader.PreloadModelProvider<CaricatureChaptersPageEntity> {

    private int layoutRes;
    private int screenWidth;
    private int module;//预览模式
    private int tagWidth;//加载图片的宽度
    private int tagHeight;//加载图片的高度
    private final SparseArray<CaricatureChaptersEntity> mComicPreViewSparseArray = new SparseArray<>(); //记录每一个集数的对象
    private ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider; //预加载的ViewPreLoad
    private CaricatureChaptersEntity chaptersEntity;

    public CaricatureInfoAdapter(Context mContext, int layoutRes, ViewPreloadSizeProvider preloadSizeProvider) {
        super(mContext);
        this.layoutRes = layoutRes;
        this.preloadSizeProvider = preloadSizeProvider;
        module = PreferencesUtils.getInt(mContext, Constant.XML_KEY_CARICATURE_MODE, 0);
        tagHeight = module == 0 ? PixelUtil.dp2px(mContext, 500f) : ScreenUtils.getScreenHeight(mContext) / 2;
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        tagWidth = screenWidth / 2;
    }

    public void setComicPreView(CaricatureChaptersEntity chaptersEntity) {
        this.chaptersEntity = chaptersEntity;
        if (chaptersEntity != null) {
            chaptersEntity.setPagerSize(chaptersEntity.getPags().size());
            mComicPreViewSparseArray.put(chaptersEntity.getIndex(), chaptersEntity);
        }
    }

    public CaricatureChaptersEntity getComicPreViewByIndex(int index) {
        return mComicPreViewSparseArray.get(index);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CaricatureInfoViewHolder(getItemView(layoutRes, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CaricatureInfoViewHolder) holder).setWidthOrHeight(tagWidth, tagHeight, screenWidth);
        ((CaricatureInfoViewHolder) holder).setPreloadSizeProvider(preloadSizeProvider);
        ((CaricatureInfoViewHolder) holder).bindData(mContext, chaptersEntity, getList(), position, module);
    }

    @NonNull
    @Override
    public List<CaricatureChaptersPageEntity> getPreloadItems(int position) {
        try {
            if (ListUtils.isEmpty(getList())) return Collections.emptyList();
            return getList().subList(position, position + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull CaricatureChaptersPageEntity item) {
        return GlideApp.with(mContext).asBitmap().load(item.getUrl()).override(tagWidth, tagHeight).placeholder(new ColorDrawable(Color.BLACK)).diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
