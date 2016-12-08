package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.ui.VideoHomeInfoActivity;
import com.kcj.animationfriend.util.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * @ClassName: VideoAdapter
 * @Description: 视频列表适配器
 * @author: KCJ
 * @date: 
 */
public class VideoAdapter extends BaseAdapter{

	private Context mContext; // 上下文对象
	private List<Video> list; // 博客列表
	
	public VideoAdapter(Context mContext ,List<Video> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null ) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_video_info, null);
			holder = new ViewHolder();
			// holder.id = (TextView) convertView.findViewById(R.id.id);
			holder.title = (TextView) convertView.findViewById(R.id.list_item_title);
			holder.uptext = (TextView) convertView.findViewById(R.id.tv_up);
			holder.bofangtext = (TextView) convertView.findViewById(R.id.tv_bofang);
			holder.tv_review = (TextView) convertView.findViewById(R.id.tv_review);
			holder.img = (ImageView) convertView.findViewById(R.id.list_item_image);
			convertView.setTag(holder); // 表示给View添加一个格外的数据，
		} else {
			holder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
		}
		Video item = list.get(position); // 获取当前数据
		if (item != null) {
			// 显示标题内容
			holder.title.setText(item.getTitle());
			holder.uptext.setText(item.getAuthor());
			holder.bofangtext.setText(item.getPlay());
			holder.tv_review.setText(item.getVideoReview());
			if (item.getPic() != null) {
				holder.img.setVisibility(View.VISIBLE);
				// 异步加载图片
				ImageLoader.getInstance().displayImage(item.getPic(), holder.img, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
			} else {
				// 
				holder.img.setVisibility(View.VISIBLE); 
			}
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				Bundle bundle = new Bundle();
				Video item = (Video) list.get(position);
				i.setClass(mContext, VideoHomeInfoActivity.class);
				bundle.putSerializable("videoItemdata", item);
				i.putExtras(bundle);
				mContext.startActivity(i);
			}
			
		});
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		ImageView img;
		TextView uptext;
		TextView bofangtext;
		TextView tv_review;
	}
}
