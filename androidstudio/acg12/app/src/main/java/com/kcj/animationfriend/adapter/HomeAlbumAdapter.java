package com.kcj.animationfriend.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.bmob.v3.datatype.BmobFile;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.ViewHolderFooter;
import com.kcj.animationfriend.adapter.base.ViewHolderHomeAlbum;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.PersonInfoActivity;
import com.kcj.animationfriend.ui.RstAndLoginActivity;
import com.kcj.animationfriend.util.StringUtils;
import com.liteutil.util.Toastor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HomeAlbumAdapter extends RecyclerView.Adapter<ViewHolder>{

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM   = 1;
    private static final int TYPE_FOOTER = 2;
    
	private Context mContext;
	private List<Album> data;
	
	public HomeAlbumAdapter(Context ct, List<Album> datas) {
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
			View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_album, parent, false);
			return new ViewHolderHomeAlbum(view);
		}
	}

	@Override
	public void onBindViewHolder(final ViewHolder mHolder, final int position) {
		if(mHolder instanceof ViewHolderHomeAlbum){
			Album album = data.get(position);
			// 图片
			ArrayList<BmobFile> proFileList = album.getProFileList();
			if(proFileList != null){
				BmobFile fileList = proFileList.get(0);
				if(fileList != null){
					((ViewHolderHomeAlbum)mHolder).imageView.setImageWidth(album.getResWidth());
					((ViewHolderHomeAlbum)mHolder).imageView.setImageHeight(album.getResHight());
					ImageLoader.getInstance().displayImage(fileList.getFileUrl(mContext)==null?"":fileList.getFileUrl(mContext), ((ViewHolderHomeAlbum)mHolder).imageView, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
					});
				}
			}else{
				// 抓取图片显示
				ArrayList<String> urlList = album.getUrlList();
				if(urlList != null){
					String url = urlList.get(0);
					if(url != null){
						((ViewHolderHomeAlbum)mHolder).imageView.setImageWidth(album.getResWidth());
						((ViewHolderHomeAlbum)mHolder).imageView.setImageHeight(album.getResHight());
						ImageLoader.getInstance().displayImage(url, ((ViewHolderHomeAlbum)mHolder).imageView, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
							@Override
							public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
						});
					}
				}
			}
			// 内容
			String content = album.getContent();
			if(!content.isEmpty()){
				if(((ViewHolderHomeAlbum)mHolder).ll_home_album_comment.getVisibility() == View.GONE){
					((ViewHolderHomeAlbum)mHolder).ll_home_album_comment.setVisibility(View.VISIBLE);
				}
				((ViewHolderHomeAlbum)mHolder).tv_home_album_comment.setText(content);
			}else{
				if(((ViewHolderHomeAlbum)mHolder).ll_home_album_comment.getVisibility() == View.VISIBLE){
					((ViewHolderHomeAlbum)mHolder).ll_home_album_comment.setVisibility(View.GONE);
				}
			}
			// 其他信息
			int collection =  album.getFavorites();
			int love = album.getLove();
			if(collection == 0 && love == 0){
				((ViewHolderHomeAlbum)mHolder).ll_home_album_attribute.setVisibility(View.GONE);
			}else{
				((ViewHolderHomeAlbum)mHolder).ll_home_album_attribute.setVisibility(View.VISIBLE);
				((ViewHolderHomeAlbum)mHolder).tv_home_album_collection.setText(collection+"");
				((ViewHolderHomeAlbum)mHolder).tv_home_album_love.setText(love+"");
			}
			// 上传者信息
			final User user = album.getUser();
			String userUrl = null;
			String userNike = null;
			if(user != null){
				((ViewHolderHomeAlbum)mHolder).rl_home_album_icom.setVisibility(View.VISIBLE);
				if(user.getAvatar() != null){
					userUrl = user.getAvatar();
				}
				if(user.getNick() != null){
					userNike = user.getNick();
				}else{
					userNike = user.getUsername();
				}
				final Palette palette = album.getPalette();
				String paletteName = null;
				if(palette != null){
					paletteName = palette.getName();
				}
				if(paletteName != null){
					((ViewHolderHomeAlbum)mHolder).tv_home_album_name.setText(StringUtils.setStringColor("由"+userNike+"上传到"+paletteName, userNike, paletteName));
				}else{
					((ViewHolderHomeAlbum)mHolder).tv_home_album_name.setText("由"+userNike+"上传到");
				}
				
				ImageLoader.getInstance().displayImage(userUrl, ((ViewHolderHomeAlbum)mHolder).iv_home_album_logo, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
				((ViewHolderHomeAlbum)mHolder).iv_home_album_logo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(HttpProxy.getCurrentUser(mContext) == null){
							Toastor.ShowToast("请先登录~");
							Intent intent = new Intent();
							intent.setClass(mContext, RstAndLoginActivity.class);
							mContext.startActivity(intent);
							return;
						}
						Intent intent = new Intent(mContext , PersonInfoActivity.class);
						intent.putExtra("data", user);
						intent.putExtra("from", "albumType");
						mContext.startActivity(intent);
					}
				});
			}else{
				((ViewHolderHomeAlbum)mHolder).rl_home_album_icom.setVisibility(View.GONE);
			}
			((ViewHolderHomeAlbum)mHolder).imageView.setOnClickListener(new OnClickListener() {
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
