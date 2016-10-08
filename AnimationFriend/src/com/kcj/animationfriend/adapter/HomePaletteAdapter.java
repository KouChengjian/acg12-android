package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.ViewHolderFooter;
import com.kcj.animationfriend.adapter.base.ViewHolderHomePalette;
import com.kcj.animationfriend.bean.Palette;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HomePaletteAdapter extends RecyclerView.Adapter<ViewHolder>{

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM   = 1;
    private static final int TYPE_FOOTER = 2;
    
	private Context mContext;
	private List<Palette> data;
	
	public HomePaletteAdapter(Context ct, List<Palette> datas) {
		this.mContext = ct;
		this.data = datas;
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}
	
	public boolean isPositionFooter(int position) {  
        return position == data.size()+1;  
    } 
	
	@Override  
    public int getItemViewType(int position) {  
        if(isPositionFooter(position))
        	return TYPE_FOOTER;  
        return TYPE_ITEM;  
    } 
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(viewType == TYPE_HEADER){
			View foot = LayoutInflater.from(mContext).inflate(R.layout.include_listview_footer, parent, false);
			return new ViewHolderFooter(foot);
		}else if(viewType == TYPE_FOOTER){
			View foot = LayoutInflater.from(mContext).inflate(R.layout.include_listview_footer, parent, false);
			return new ViewHolderFooter(foot);
		}else{
			View view = LayoutInflater.from(mContext).inflate(R.layout.include_fragment_palette, parent, false);
			return new ViewHolderHomePalette(view);
		}
	}
	
	@Override
	public void onBindViewHolder(final ViewHolder mHolder, final int position) {
        if(mHolder instanceof ViewHolderHomePalette){
        	final Palette palette = data.get(position);
        	((ViewHolderHomePalette)mHolder).tv_palete_name.setText(palette.getName());
    		if(palette.getNum() == 0){
    			((ViewHolderHomePalette)mHolder).tv_palete_num.setVisibility(View.GONE);
    		}else{
    			((ViewHolderHomePalette)mHolder).tv_palete_num.setText(palette.getNum()+"");
    		}
    		List<String> paletteList = palette.getUrlAlbum();
    		if(paletteList != null){
    			for(int i = 0 ; i < paletteList.size() ; i++){
    				switch (i) {
    				case 0:
    					((ViewHolderHomePalette)mHolder).imageView.setImageWidth(300);
    					((ViewHolderHomePalette)mHolder).imageView.setImageHeight(300);
    					ImageLoader.getInstance().displayImage(paletteList.get(0), ((ViewHolderHomePalette)mHolder).imageView, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
    						@Override
    						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
    							super.onLoadingComplete(imageUri, view, loadedImage);
    						}
    					});	
    					
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_1, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_2, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_3, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    					break;
    				case 1:
    					ImageLoader.getInstance().displayImage(paletteList.get(1), ((ViewHolderHomePalette)mHolder).iv_palete_icon_1, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
    						@Override
    						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
    							super.onLoadingComplete(imageUri, view, loadedImage);
    						}
    					});	
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_2, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_3, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});
    					break;
    				case 2:
    					ImageLoader.getInstance().displayImage(paletteList.get(2), ((ViewHolderHomePalette)mHolder).iv_palete_icon_2, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
    						@Override
    						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
    							super.onLoadingComplete(imageUri, view, loadedImage);
    						}
    					});	
    					ImageLoader.getInstance().displayImage(null, 
    							((ViewHolderHomePalette)mHolder).iv_palete_icon_3, MyApplication.getInstance().getOptions
    							(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});
    					break;
    				case 3:
    					ImageLoader.getInstance().displayImage(paletteList.get(3), ((ViewHolderHomePalette)mHolder).iv_palete_icon_3, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
    						@Override
    						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
    							super.onLoadingComplete(imageUri, view, loadedImage);
    						}
    					});	
    					break;
    				}
    			}
    		}else{
    			((ViewHolderHomePalette)mHolder).imageView.setImageWidth(300);
    			((ViewHolderHomePalette)mHolder).imageView.setImageHeight(300);
    			ImageLoader.getInstance().displayImage(null, ((ViewHolderHomePalette)mHolder).imageView, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
    				@Override
    				public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
    					super.onLoadingComplete(imageUri, view, loadedImage);
    				}
    			});	
    			ImageLoader.getInstance().displayImage(null, 
    					((ViewHolderHomePalette)mHolder).iv_palete_icon_1, MyApplication.getInstance().getOptions
    					(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    			ImageLoader.getInstance().displayImage(null, 
    					((ViewHolderHomePalette)mHolder).iv_palete_icon_2, MyApplication.getInstance().getOptions
    					(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    			ImageLoader.getInstance().displayImage(null, 
    					((ViewHolderHomePalette)mHolder).iv_palete_icon_3, MyApplication.getInstance().getOptions
    					(R.drawable.news_item_bg),new SimpleImageLoadingListener(){});	
    		}
    		((ViewHolderHomePalette)mHolder).imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(mOnItemClickListener != null){
						mOnItemClickListener.onItemClick(null, position);
					}
				}
			});
        }
	}
	
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
	
	public void setOnClickItem(OnRecyclerViewItemClickListener mOnItemClickListener){
		this.mOnItemClickListener = mOnItemClickListener;
	}
	
	public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }
	
}
