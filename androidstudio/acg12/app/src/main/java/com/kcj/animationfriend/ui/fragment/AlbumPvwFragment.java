package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.CommentAppendAdapter;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Comment;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.AlbumPvwOptActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.ScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: AlbumPvwFragment
 * @Description: 图片预览
 * @author: KCJ
 * @date:  
 */
public class AlbumPvwFragment extends BaseFragment implements OnClickListener ,OnItemClickListener {

	@InjectView(R.id.ll_pic_container)
	protected LinearLayout llPicContainer;   
	@InjectView(R.id.details_comment_text)
	protected TextView contentText;
	@InjectView(R.id.details_comment_image)
	protected ImageView contentImage;
	@InjectView(R.id.two_comment_list)
	protected ListView commentList;
	@InjectView(R.id.two_commit_scroll)
	protected ScrollView scrollView;
	
	private int pageNum = 0;
	protected int position = 0;
	protected Album album = null;
	protected CommentAppendAdapter mAdapter;
	private List<Comment> comments = new ArrayList<Comment>();
	protected boolean hidden;
	protected NewBroadcastReceiver newReceiver;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comment, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		position = getArguments().getInt("position");
		album = (Album)getArguments().getSerializable("data");
		initViews();
		initEvents();
		initDatas();
		initBroadCast();
	}
	
	public void initViews(){
		mAdapter = new CommentAppendAdapter(mContext, comments);
		commentList.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(commentList);
		commentList.setCacheColorHint(0);
		commentList.setScrollingCacheEnabled(false);
		commentList.setScrollContainer(false);
		commentList.setFastScrollEnabled(true);
		commentList.setSmoothScrollbarEnabled(true);
	}
	
	public void initEvents(){
		commentList.setOnItemClickListener(this);
		contentImage.setOnClickListener(this);
	}
	
	public void initDatas(){
		if(album.getContent().isEmpty() || album.getContent() == null){
			contentText.setVisibility(View.GONE);
		}else{
			contentText.setText(album.getContent());
		}
		ArrayList<BmobFile> proFileList = album.getProFileList();
		if(proFileList != null){
			ScaleImageView layout = null ;
			int i = 0;
			for(BmobFile bmobFile:proFileList){
				if(bmobFile != null){
					layout = (ScaleImageView)LayoutInflater.from(mContext).inflate(R.layout.include_pic_item, null);
					ImageLoader.getInstance().displayImage(bmobFile.getFileUrl(mContext), layout, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
					});
				}
				if(layout != null){
					layout.setTag(i++);
					layout.setOnClickListener(this);
					llPicContainer.addView(layout);
				}
			}
		}else{
			// 抓取图片显示
			ArrayList<String> urlList = album.getUrlList();
			if(urlList != null){
				ScaleImageView layout = null ;
				int i = 0;
				for(String url:urlList){
					if(url != null){
						layout = (ScaleImageView)LayoutInflater.from(mContext).inflate(R.layout.include_pic_item, null);
						ImageLoader.getInstance().displayImage(url, layout, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
							@Override
							public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
						});
					}
					if(layout != null){
						layout.setTag(i++);
						layout.setOnClickListener(this);
						llPicContainer.addView(layout);
					}
				}
			}
		}
		refresh();// 获取数据
	}
	
	private void initBroadCast(){
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(Constant.Filter);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		getActivity().registerReceiver(newReceiver, intentFilter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if(hidden){
		}else{
		}
	}
	
	public void refresh(){
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryDatas();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void queryDatas() {
		final User user = HttpProxy.getCurrentUser(mContext);
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		query.addWhereRelatedTo("commentBR", new BmobPointer(album));
		query.include("user");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.findObjects(mContext, new FindListener<Comment>() {
			@Override
			public void onSuccess(List<Comment> data) {
				if(data.size()!=0 && data.get(data.size()-1)!=null){
					if(data.size()<Constant.NUMBERS_PER_PAGE){
//						ShowToast("已加载完所有评论~");
					}
					mAdapter.getDataList().addAll(data);
					mAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
					if(user!= null){
						for(Comment comment:mAdapter.getDataList()){
							if(comment.getUser().getObjectId().equals(user.getObjectId())){
								if(comment.getType() == 1){
									Intent intent = new Intent();
									intent.setAction(Constant.Filter_LOVE);
									intent.putExtra("position", position);
									mContext.sendBroadcast(intent);
								}
							}
						}
					}
					
				}else{
//					ShowToast( "暂无更多评论~");
					pageNum--;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				ShowToast( "获取评论失败。请检查网络~"+arg1);
				pageNum--;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_album_pic:
			int position = (int)v.getTag();
			Intent intent = new Intent(getActivity() , AlbumPvwOptActivity.class);
			intent.putExtra("data", album);
			intent.putExtra("position", position);
			startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
	
	/**
     * 动态设置listView的高度 
     *  item 总布局必须是linearLayout
     * @param listView 
     */  
    public void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount()-1))  
                +15;  
        listView.setLayoutParams(params);  
    }
    
    /**
	 * 新消息广播接收者
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Constant.Filter)){
				int positions = intent.getExtras().getInt("position");
				if(positions == position){
					Comment Comment = (Comment)intent.getSerializableExtra("data");
					mAdapter.getDataList().add(Comment);
					mAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
				}
			}
		}
	}
}
