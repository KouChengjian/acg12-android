package com.kcj.animationfriend.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.ViewHolderHomeHList;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.AlbumPvwActivity;
import com.kcj.animationfriend.ui.HomeActivity;
import com.kcj.animationfriend.ui.VideoHomeInfoActivity;
import com.kcj.animationfriend.util.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HomeHListAdapter extends RecyclerView.Adapter<ViewHolderHomeHList>{

	private Context mContext;
	private List<Object> mList;

	public HomeHListAdapter(Context mContext,List<Object> mList){
		this.mContext = mContext;
		this.mList = mList;
	}
	
	public void setList(List<Object> mList){
		this.mList = mList;
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}

	@Override
	public ViewHolderHomeHList onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_area_hlist, parent, false);
		return new ViewHolderHomeHList(view);
	}
	
	@Override
	public void onBindViewHolder(final ViewHolderHomeHList mHolder, final int position) {
		String url = "";
		Object object = mList.get(position);
		if(object instanceof Album){
			url = ((Album) object).getUrlList().get(0);
			mHolder.ll_item_area_album.setVisibility(View.VISIBLE);
			mHolder.ll_item_area_vedio.setVisibility(View.GONE);
			mHolder.tv_item_area_album_collection.setText(((Album) object).getFavorites()+"");
			mHolder.tv_item_area_album_love.setText(((Album) object).getLove()+"");
			mHolder.ll_item_area_num.setVisibility(View.GONE);
			mHolder.ll_item_area_name.setText("");
			mHolder.ll_item_area_author.setText("");
		}else if(object instanceof Palette){
			url = ((Palette) object).getUrlAlbum().get(0);
			mHolder.ll_item_area_album.setVisibility(View.GONE);
			mHolder.ll_item_area_vedio.setVisibility(View.GONE);
			mHolder.ll_item_area_num.setText(((Palette) object).getNum()+"");
			mHolder.ll_item_area_name.setText(((Palette) object).getName());
			mHolder.ll_item_area_author.setText("");
		}else if(object instanceof Video){
			url = ((Video) object).getPic();
			mHolder.ll_item_area_album.setVisibility(View.GONE);
			mHolder.ll_item_area_vedio.setVisibility(View.VISIBLE);
			mHolder.tv_item_area_vedio_baofang.setText(StringUtils.numswitch(((Video) object).getPlay()));
			mHolder.tv_item_area_vedio_danmugu.setText(StringUtils.numswitch(((Video) object).getVideoReview()));
			mHolder.ll_item_area_num.setVisibility(View.GONE);
			mHolder.ll_item_area_name.setText(((Video) object).getTitle());
			mHolder.ll_item_area_author.setText(((Video) object).getAuthor());
			mHolder.ll_item_area_author.setVisibility(View.GONE);
		}
		ImageLoader.getInstance().displayImage(url, mHolder.icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
			}
		});
		mHolder.icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				Bundle bundle = new Bundle();
				if(mList.get(position) instanceof Album){
					Album item = (Album) mList.get(position);
					i.setClass(mContext, AlbumPvwActivity.class);
					i.putExtra("data", item);
					i.putExtra("position", position);
					UserProxy.commentAlbum = new ArrayList<Album>();
					for(int j= 0 ; j < mList.size() ;j++){
						Album album = (Album) mList.get(j);
						UserProxy.commentAlbum.add(album);
					}
				}else if(mList.get(position) instanceof Palette){
					Palette item = (Palette) mList.get(position);
					i.setClass(mContext, HomeActivity.class);
					i.putExtra("data", item);
					i.putExtra("AreaType", 1);
				}else if(mList.get(position) instanceof Video){
					Video item = (Video) mList.get(position);
					i.setClass(mContext, VideoHomeInfoActivity.class);
					bundle.putSerializable("videoItemdata", item);
				}
				i.putExtras(bundle);
				mContext.startActivity(i);
			}
		});		
	}

	

}
