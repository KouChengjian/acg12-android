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
import com.kcj.animationfriend.adapter.base.ViewHolderFind;
import com.kcj.animationfriend.adapter.base.ViewHolderFooter;
import com.kcj.animationfriend.bean.Video;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * @ClassName: FindAdapter
 * @Description: 发现
 * @author: KCJ
 * @date:
 */
public class FindAdapter extends RecyclerView.Adapter<ViewHolder>{

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM   = 1;
    private static final int TYPE_FOOTER = 2;
    
	private Context mContext;
	private List<Video> data;

	public FindAdapter(Context ct, List<Video> datas) {
		this.mContext = ct;
		this.data = datas;
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}
	
	@Override  
    public int getItemViewType(int position) {  
//        if (isPositionHeader(position))  
//            return TYPE_HEADER;  
//        else 
        if(isPositionFooter(position))
        	return TYPE_FOOTER;  
        return TYPE_ITEM;  
    } 
	
	public boolean isPositionHeader(int position) {  
        return position == 0;  
    } 
	
	public boolean isPositionFooter(int position) {  
        return position == data.size()+1;  
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
			View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_page, parent, false);
			return new ViewHolderFind(view);
		}
	}
	
	@Override
	public void onBindViewHolder(final ViewHolder mHolder, final int position) {
		if(mHolder instanceof ViewHolderFind){
			Video video = data.get(position);
			((ViewHolderFind)mHolder).icon.setImageWidth(250);
			((ViewHolderFind)mHolder).icon.setImageHeight(350);
			ImageLoader.getInstance().displayImage(video.getPic(), ((ViewHolderFind)mHolder).icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					if(loadedImage.getHeight()> 350){
						((ViewHolderFind)mHolder).icon.setImageHeight(350);
					}else{
						((ViewHolderFind)mHolder).icon.setImageHeight(loadedImage.getHeight());
					}
				}
			});
			((ViewHolderFind)mHolder).msg.setText(video.getTitle());
			((ViewHolderFind)mHolder).num.setText(video.getUpdateContent());
			
			((ViewHolderFind)mHolder).icon.setOnClickListener(new OnClickListener() {
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
