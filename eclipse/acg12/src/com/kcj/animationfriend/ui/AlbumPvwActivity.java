package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.EmoViewPagerAdapter;
import com.kcj.animationfriend.adapter.EmoteAdapter;
import com.kcj.animationfriend.adapter.WeakFragmentPagerAdapter;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Comment;
import com.kcj.animationfriend.bean.FaceText;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.ui.fragment.AlbumPvwFragment;
import com.kcj.animationfriend.util.FaceTextUtils;
import com.kcj.animationfriend.view.EmoticonsEditText;
import com.kcj.animationfriend.view.LoadingDialog;
import com.kcj.animationfriend.view.photo.zoom.ViewPagerFixed;
import com.liteutil.util.Log;

/**
 * @ClassName: AlbumPvwActivity
 * @Description: 图片预览
 * @author: KCJ
 * @date: 
 */
public class AlbumPvwActivity extends BaseSwipeBackActivity implements OnClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.ll_comment_operation)
	protected LinearLayout ll_comment_operation;//最下面的操作控制 
	@InjectView(R.id.tv_comment_operation_love)
	protected TextView love;
	@InjectView(R.id.tv_comment_operation_comment)
	protected TextView comment;
	@InjectView(R.id.tv_comment_operation_collection)
	protected TextView collection;
	@InjectView(R.id.comment_operation_import)
	protected LinearLayout rl_comment_inport; // 输入评论布局
	@InjectView(R.id.edit_user_comment)
	protected EmoticonsEditText edit_user_comment; // edit
	@InjectView(R.id.btn_chat_add)
	protected Button btn_chat_add; // 添加更多内容
	@InjectView(R.id.btn_chat_emo)
	protected Button btn_chat_emo; // 表情
	@InjectView(R.id.pager_emo)
	protected ViewPager pager_emo;
	@InjectView(R.id.btn_chat_send)
	protected Button btn_chat_send; // 发送
	@InjectView(R.id.layout_more)
	protected LinearLayout layout_more; // 所有的布局
	@InjectView(R.id.layout_emo)
	protected LinearLayout layout_emo;
	@InjectView(R.id.layout_add)
	protected LinearLayout layout_add;
	@InjectView(R.id.layout_add_pictrue)
	protected LinearLayout layout_add_pictrue;
	// 隐藏
	@InjectView(R.id.ll_comment_operation_love)
	protected RelativeLayout rlLove;
	@InjectView(R.id.ll_comment_operation_comment)
	protected RelativeLayout rlComment;
	// 表情
	@InjectView(R.id.vp_image_perview)
	protected ViewPagerFixed pager;
	protected MyPagerAdapter adapter = null;
	protected List<Album> listAlbum = new ArrayList<Album>();
	protected List<Comment> curComments = new ArrayList<Comment>(); // 当前评论
	protected NewBroadcastReceiver newReceiver;
	protected Album curAlbum = null;
	protected int position = 0;
	protected Drawable drawableP;
	protected Drawable drawableN;
	protected List<FaceText> emos;
	
	protected LoadingDialog dialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		listAlbum.addAll(UserProxy.commentAlbum);
		position = getIntent().getExtras().getInt("position");
		curAlbum = listAlbum.get(position);
		initViews();
		initEvent();
		initDatas();
		initBroadCast();
		initEmoView();
	}
	
	@SuppressWarnings("deprecation")
	public void initViews(){
		setTitle(R.string.home_album);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		dialog = new LoadingDialog(mContext);
		drawableP = getResources().getDrawable(R.drawable.icon_love_p);
		drawableP.setBounds(1, 1, 30, 30);
		drawableN = getResources().getDrawable(R.drawable.icon_love);
		drawableN.setBounds(1, 1, 30, 30);
		pager.addOnPageChangeListener(pageChangeListener);
		adapter = new MyPagerAdapter(getSupportFragmentManager() ,listAlbum);
		pager.setAdapter(adapter);
		pager.setPageMargin((int) getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
		pager.setCurrentItem(position);
		btn_chat_add.setVisibility(View.GONE);
	}
	
	public void initEvent(){
		love.setOnClickListener(this);
		comment.setOnClickListener(this);
		collection.setOnClickListener(this);
		btn_chat_add.setOnClickListener(this);
		btn_chat_emo.setOnClickListener(this);
		btn_chat_send.setOnClickListener(this);
	}
	
	public void initDatas(){
		if(curAlbum.getMyLove()){
			love.setText(curAlbum.getLove()+"");
			love.setTextColor(0xffd95555);
			love.setCompoundDrawables(drawableP, null, null, null);
		}else{
			love.setText(curAlbum.getLove()+"");
			love.setTextColor(0xffaaaaaa);
			love.setCompoundDrawables(drawableN, null, null, null);
		}
		if(curAlbum.getUser() == null){
			rlLove.setVisibility(View.INVISIBLE);
			rlComment.setVisibility(View.INVISIBLE);
		}
	}
	
	private void initBroadCast(){
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(Constant.Filter_LOVE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}
	
	private void initEmoView() {
		emos = FaceTextUtils.faceTexts;
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < 2; ++i) {
			views.add(getGridView(i));
		}
		pager_emo.setAdapter(new EmoViewPagerAdapter(views));
	}
	
	public void onResume() {
		super.onResume();
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		Intent intent = new Intent();
		intent.putExtra("position", position);
		setResult(Activity.RESULT_OK, intent);
	}
	
	public void onDestroy() {
		unregisterReceiver(newReceiver);
		super.onDestroy();
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_chat_add:
			if (layout_more.getVisibility() == View.GONE) {
				layout_more.setVisibility(View.VISIBLE);
				layout_add.setVisibility(View.VISIBLE);
				layout_emo.setVisibility(View.GONE);
				hideSoftInputView();
			}else {
				if (layout_emo.getVisibility() == View.VISIBLE) {
					layout_emo.setVisibility(View.GONE);
					layout_add.setVisibility(View.VISIBLE);
				}else if(layout_add_pictrue.getVisibility() == View.VISIBLE){
					layout_add_pictrue.setVisibility(View.GONE);
					layout_add.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.btn_chat_emo:
			if (layout_more.getVisibility() == View.GONE) {
				showEditState(true);
			} else {
				if (layout_add.getVisibility() == View.VISIBLE) {
					layout_add.setVisibility(View.GONE);
					layout_emo.setVisibility(View.VISIBLE);
				} else if(layout_add_pictrue.getVisibility() == View.VISIBLE){
					layout_add_pictrue.setVisibility(View.GONE);
					layout_emo.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.btn_chat_send: // 发送
			InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
			imm.hideSoftInputFromWindow(edit_user_comment.getWindowToken(), 0);
			ll_comment_operation.setVisibility(View.VISIBLE);
			rl_comment_inport.setVisibility(View.GONE);
			layout_more.setVisibility(View.GONE);
			layout_add.setVisibility(View.GONE);
			layout_emo.setVisibility(View.GONE);
			layout_add_pictrue.setVisibility(View.GONE);
			onClickSendMomment(edit_user_comment.getText().toString().trim(),0);
			break;
		case R.id.tv_picture: // 选择图片
			if (layout_add.getVisibility() == View.VISIBLE) {
				layout_add.setVisibility(View.GONE);
				layout_add_pictrue.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.tv_camera: // 照相
			break;
		case R.id.tv_comment_operation_collection: // 控制- 采集
			if(!isCurrentUser()){
				startAnimActivity(new Intent(this, RstAndLoginActivity.class));
				return;
			}
			onClickCollection();
			break;
		case R.id.tv_comment_operation_love: // 控制-喜欢
			if(curAlbum.getMyLove()){
				ShowToast("已经点赞");
			}else{
				onClickLove();
			}
			break;
		case R.id.tv_comment_operation_comment: // 控制- 评论
			if(!isCurrentUser()){
				startAnimActivity(new Intent(this, RstAndLoginActivity.class));
				return;
			}
			edit_user_comment.requestFocus();
			InputMethodManager imms = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
			imms.showSoftInput(edit_user_comment, 0);  
			ll_comment_operation.setVisibility(View.GONE);
			rl_comment_inport.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	/**
	 * 根据是否点击笑脸来显示文本输入框的状态
	 */
	private void showEditState(boolean isEmo) {
		edit_user_comment.setVisibility(View.VISIBLE);
		edit_user_comment.requestFocus();
		if (isEmo) {
			layout_more.setVisibility(View.VISIBLE);
			layout_emo.setVisibility(View.VISIBLE);
			layout_add.setVisibility(View.GONE);
			hideSoftInputView();
		} else {
			layout_more.setVisibility(View.GONE);
			showSoftInputView();
		}
	}
	
	// 显示软键盘
	public void showSoftInputView() {
		if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.showSoftInput(edit_user_comment, 0);
		}
	}	
	
	public boolean isCurrentUser(){
		 User currentUser = HttpProxy.getCurrentUser(mContext);
         if (currentUser == null) {
             return false;
         }else{
        	 return true;
         }
	}
	
	/**
	 * 发送评论
	 */
	private void onClickSendMomment(String commentEdit ,int type) {
		User currentUser = HttpProxy.getCurrentUser(mContext);
		if(currentUser != null){//已登录
			 
			if(TextUtils.isEmpty(commentEdit)){
				ShowToast( "评论内容不能为空。");
				return;
			}
			publishComment(commentEdit ,currentUser ,type);
		}else{//未登录
			Intent intent = new Intent();
			intent.setClass(mContext, RstAndLoginActivity.class);
			startActivity(intent);
		}
	}
	
	private void publishComment(String commitContent,final User currentUser ,final int type) {
		final Comment twocomment = new Comment();
		twocomment.setUser(currentUser);
		twocomment.setCommentContent(commitContent);
		twocomment.setType(type);
		twocomment.save(mContext, new SaveListener() {
			@Override
			public void onSuccess() {
				//将该评论与画集绑定到一起
				edit_user_comment.setText("");
				BmobRelation relation = new BmobRelation();
				relation.add(twocomment);
				curAlbum.setCommentRelat(relation);
				curAlbum.update(mContext, new UpdateListener() {
					@Override
					public void onSuccess() {
						if(type == 0){
							ShowToast("更新评论成功。");
	                        Intent intent = new Intent();
	        				intent.setAction(Constant.Filter);
	        				intent.putExtra("data", twocomment);
	        				intent.putExtra("position", position);
	                        sendBroadcast(intent);
						}else if(type == 1){
							saveLove(currentUser ,twocomment);
						}
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						dialog.dismiss();
						ShowToast("关联失败:"+arg1);
					}
				});
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				dialog.dismiss();
				ShowToast("评论失败。请检查网络~"+arg1);
			}
		});
	}
	
	/**
	 *  喜欢
	 */
	private void onClickLove() {
		final User user = HttpProxy.getCurrentUser(mContext);
		if(user == null){
			//前往登录注册界面
			ShowToast("请先登录。");
			Intent intent = new Intent();
			intent.setClass(mContext, RstAndLoginActivity.class);
			startActivity(intent);
			return;
		}
		dialog.show();
		onClickSendMomment("点赞",1);
	}
	
	public void saveLove(final User user ,final Comment twocomment){
		User curUser = HttpProxy.getCurrentUser(mContext);
		BmobRelation relation = new BmobRelation();
		relation.add(curAlbum);
		curUser.setFavourBR(relation);
		curUser.update(mContext, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				curAlbum.increment("love",1);
				curAlbum.update(mContext, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						dialog.dismiss();
						ShowToast("点赞成功~");
						curAlbum.setLove(curAlbum.getLove()+1);
						curAlbum.setMyLove(true);
						love.setText(curAlbum.getLove()+"");
						love.setTextColor(0xffd95555);
						love.setCompoundDrawables(drawableP, null, null, null);
						Intent intent = new Intent();
						intent.setAction(Constant.Filter);
						intent.putExtra("data", twocomment);
						intent.putExtra("position", position);
		                sendBroadcast(intent);
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Log.e("onFailure", arg1);
						ShowToast("点赞失败");
						user.update(mContext);
						dialog.dismiss();
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("onFailure", arg1);
				dialog.dismiss();
				ShowToast("点赞失败");
			}
		});
		
	}
	
	/**
	 *  采集
	 */
	public void onClickCollection(){
		Intent intent = new Intent(this,AlbumPvwCltActivity.class);
		intent.putExtra("data", curAlbum);
		startActivity(intent);
	}
	
	/**
	 *  表情
	 */
	private View getGridView(final int i) {
		View view = View.inflate(this, R.layout.include_emo_gridview, null);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		List<FaceText> list = new ArrayList<FaceText>();
		if (i == 0) {
			list.addAll(emos.subList(0, 21));
		} else if (i == 1) {
			list.addAll(emos.subList(21, emos.size()));
		}
		final EmoteAdapter gridAdapter = new EmoteAdapter(AlbumPvwActivity.this,
				list);
		gridview.setAdapter(gridAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FaceText name = (FaceText) gridAdapter.getItem(position);
				String key = name.text.toString();
				try {
					if (edit_user_comment != null && !TextUtils.isEmpty(key)) {
						int start = edit_user_comment.getSelectionStart();
						CharSequence content = edit_user_comment.getText()
								.insert(start, key);
						edit_user_comment.setText(content);
						// 定位光标位置
						CharSequence info = edit_user_comment.getText();
						if (info instanceof Spannable) {
							Spannable spanText = (Spannable) info;
							Selection.setSelection(spanText,
									start + key.length());
						}
					}
				} catch (Exception e) {

				}
			}
		});
		return view;
	}
	
	/**
	 * pageView滑动监听
	 */
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		
		public void onPageSelected(int positions) {
			UserProxy.position = positions;
			position = positions;
			curAlbum = listAlbum.get(position);
			initDatas();
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		public void onPageScrollStateChanged(int positions) {
			hideSoftInputView();
			ll_comment_operation.setVisibility(View.VISIBLE);
			rl_comment_inport.setVisibility(View.GONE);
		}
	};
	
	public class MyPagerAdapter extends WeakFragmentPagerAdapter {

		List<Album> listAlbum ;

        public MyPagerAdapter(FragmentManager fm ,List<Album> listAlbum) {
            super(fm);
            this.listAlbum = listAlbum;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return listAlbum.size();
        }
        
        public void addlist(Album album){
        	listAlbum.add(album);
        }
        
        public void updata(){
        	this.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
        	AlbumPvwFragment commentFragment = new AlbumPvwFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putSerializable("data", listAlbum.get(position));
            commentFragment.setArguments(bundle);
            saveFragment(commentFragment);
            return commentFragment;
        }
    }
	
	/**
	 * 新消息广播接收者
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Constant.Filter_LOVE)){
				int positions = intent.getExtras().getInt("position");
				listAlbum.get(positions).setMyLove(true);
				if(positions == position){
					curAlbum = listAlbum.get(position);
					initDatas();
				}
			}
		}
	}
}
