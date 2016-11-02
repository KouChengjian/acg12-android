package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.BaseContentAdapter;
import com.kcj.animationfriend.bean.Comment;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.util.FaceTextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName:
 * @Description: 二级评论
 * @author kcj
 * @date
 */
public class CommentAppendAdapter extends BaseContentAdapter<Comment> {
	
	public Context mContext;
	
	public CommentAppendAdapter(Context context, List<Comment> list) {
		super(context, list);
		mContext = context;
	}

	public View getConvertView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_two_comment, null);
			viewHolder.userIcon = (ImageView) convertView.findViewById(R.id.details_comment_logo);
			viewHolder.userName = (TextView) convertView.findViewById(R.id.details_comment_name);
			viewHolder.commentContent = (TextView) convertView.findViewById(R.id.content_comment);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Comment comment = dataList.get(position);
		User user = comment.getUser();
		String userUrl = null;
		if(user != null){
			if(user.getAvatar() != null){
				userUrl = user.getAvatar();
				ImageLoader.getInstance().displayImage(userUrl, viewHolder.userIcon, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
			}
		}
		if (comment.getUser() != null) {
			if(TextUtils.isEmpty(comment.getUser().getNick())){
				viewHolder.userName.setText(comment.getUser().getUsername()+ " :");
			}else{
				viewHolder.userName.setText(comment.getUser().getNick()+ " :");
			}
		} else {
			viewHolder.userName.setText("墙友");
		}
		try {
			SpannableString spannableString = FaceTextUtils
					.toSpannableString(mContext, comment.getCommentContent());
			viewHolder.commentContent.setText(spannableString);
		} catch (Exception e) {
		}
		return convertView;
	}

	public static class ViewHolder {
		public ImageView userIcon;
		public TextView userName;
		public TextView commentContent;
	}
}
