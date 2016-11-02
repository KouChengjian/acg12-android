package com.kcj.animationfriend.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.BmobRecordManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.im.inteface.OnRecordChangeListener;
import cn.bmob.im.inteface.UploadListener;
import cn.bmob.im.util.BmobLog;

import com.kcj.animationfriend.MyMessageReceiver;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.EmoViewPagerAdapter;
import com.kcj.animationfriend.adapter.EmoteAdapter;
import com.kcj.animationfriend.adapter.MessageChatAdapter;
import com.kcj.animationfriend.bean.FaceText;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.listener.NewRecordPlayClickListener;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.FaceTextUtils;
import com.kcj.animationfriend.util.Network;
import com.kcj.animationfriend.util.SdCardUtil;
import com.kcj.animationfriend.view.EmoticonsEditText;
import com.kcj.animationfriend.view.RefreshLayout;


/**
 * @ClassName: ChatActivity
 * @Description: 聊天界面
 * @author: KCJ
 * @date: 2015-8-30 
 */
public class ChatActivity extends BaseActivity implements OnClickListener, EventListener ,TextWatcher ,OnRefreshListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	// 语音有关
	@InjectView(R.id.layout_record)
	protected RelativeLayout layout_record;
	@InjectView(R.id.tv_voice_tips)
	protected TextView tv_voice_tips;
	@InjectView(R.id.iv_record)
	protected ImageView iv_record;
	// list
	@InjectView(R.id.rl_refresh)
	protected RefreshLayout mRefreshLayout;
	@InjectView(R.id.mListView)
	protected ListView mListView;
	protected MessageChatAdapter mAdapter;
	
	// 最下方输入框
	@InjectView(R.id.btn_chat_emo)
	protected Button btn_chat_emo; 
	@InjectView(R.id.btn_chat_send)
	protected Button btn_chat_send; 
	@InjectView(R.id.btn_chat_add)
	protected Button btn_chat_add;
	@InjectView(R.id.btn_chat_keyboard)
	protected Button btn_chat_keyboard; 
	@InjectView(R.id.btn_speak)
	protected Button btn_speak; 
	@InjectView(R.id.btn_chat_voice)
	protected Button btn_chat_voice;
	@InjectView(R.id.layout_more)
	protected LinearLayout layout_more; 
	@InjectView(R.id.layout_emo)
	protected LinearLayout layout_emo; 
	@InjectView(R.id.pager_emo)
	protected ViewPager pager_emo;
	protected List<FaceText> emos;
	@InjectView(R.id.layout_add)
	protected LinearLayout layout_add;
	@InjectView(R.id.tv_picture)
	protected TextView tv_picture; 
	@InjectView(R.id.tv_camera)
	protected TextView tv_camera; 
	@InjectView(R.id.tv_location)
	protected TextView tv_location;
	@InjectView(R.id.edit_user_comment)
	protected EmoticonsEditText edit_user_comment;
	
	// other
	Toast toast;
	BmobChatUser targetUser;
	String targetId = "";
	private static int MsgPagerNum;
	BmobRecordManager recordManager;
	NewBroadcastReceiver  receiver;
	private Drawable[] drawable_Anims;// 话筒动画
	public static final int NEW_MESSAGE = 0x001;// 收到消息
	private String localCameraPath = "";// 拍照后得到的图片地址
	
	// 声音动画
	int drawable_anims_pos = 0;
	int drawable_anims_time = 0;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_chat);
		setTitle(R.string.chat);
		setSupportActionBar(toolbar);
		initViews();
		initEvent();
		initBroadCast();
	}

	@SuppressWarnings("deprecation")
	public void initViews(){
		manager = BmobChatManager.getInstance(this);
		MsgPagerNum = 0;
		// 组装聊天对象
		targetUser = (BmobChatUser) getIntent().getSerializableExtra("user");
		targetId = targetUser.getObjectId();
		if(!TextUtils.isEmpty(targetUser.getNick())){
			//initTopBarForLeft("与" + targetUser.getNick() + "对话");
			setTitle(targetUser.getNick());
		}else{
			//initTopBarForLeft("与" + targetUser.getUsername() + "对话");
			setTitle(targetUser.getUsername());
		}
		
		// emos
		emos = FaceTextUtils.faceTexts;
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < 2; ++i) {
			views.add(getGridView(i));
		}
		pager_emo.setAdapter(new EmoViewPagerAdapter(views));
		// list
		mRefreshLayout.setEbLoading(false);
	    mRefreshLayout.setColorSchemeResources(R.color.theme_primary);
//		mListView.setPullLoadEnable(false);// 首先不允许加载更多
//		mListView.setPullRefreshEnable(true);// 允许下拉
//		mListView.pullRefreshing();
		mListView.setDividerHeight(0);
		initOrRefresh();
		mListView.setSelection(mAdapter.getCount() - 1);
		// 声音
		drawable_Anims = new Drawable[] {
				getResources().getDrawable(R.drawable.chat_icon_voice2),
				getResources().getDrawable(R.drawable.chat_icon_voice3),
				getResources().getDrawable(R.drawable.chat_icon_voice4),
				getResources().getDrawable(R.drawable.chat_icon_voice5),
				getResources().getDrawable(R.drawable.chat_icon_voice6) };
		recordManager = BmobRecordManager.getInstance(this);// 语音相关管理器
	}
	
    public void initEvent(){
    	btn_chat_add.setOnClickListener(this);
		btn_chat_emo.setOnClickListener(this);
		btn_chat_voice.setOnClickListener(this);
		btn_chat_keyboard.setOnClickListener(this);
		btn_chat_send.setOnClickListener(this);
		
		tv_picture.setOnClickListener(this);
		tv_location.setOnClickListener(this);
		tv_camera.setOnClickListener(this);
		
		edit_user_comment.setOnClickListener(this);
		edit_user_comment.addTextChangedListener(this);
		
		// list
		// 设置监听器
		mRefreshLayout.setOnRefreshListener(this);
//		mListView.setXListViewListener(this);
		mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				hideSoftInputView();
				layout_more.setVisibility(View.GONE);
				layout_add.setVisibility(View.GONE);
				btn_chat_voice.setVisibility(View.VISIBLE);
				btn_chat_keyboard.setVisibility(View.GONE);
				btn_chat_send.setVisibility(View.GONE);
				return false;
			}
		});
		// 重发按钮的点击事件
		mAdapter.setOnInViewClickListener(R.id.iv_fail_resend,new MessageChatAdapter.onInternalClickListener() {
			@Override
			public void OnClickListener(View parentV, View v,Integer position, Object values) {
				// 重发消息
				showResendDialog(parentV, v, values);
			}
		});
		
		// 监听语音
		btn_speak.setOnTouchListener(new VoiceTouchListen());
		// 设置音量大小监听--在这里开发者可以自己实现：当剩余10秒情况下的给用户的提示，类似微信的语音那样
		recordManager.setOnRecordChangeListener(new OnRecordChangeListener() {
			
			@Override
			public void onVolumnChanged(int value) {
//				Log.e("onVolumnChanged", value+"====");
				drawable_anims_time++;
				if(drawable_anims_time == 10){
					drawable_anims_time = 0;
					if(drawable_anims_pos%5 == 0){
						iv_record.setImageDrawable(drawable_Anims[0]);
					}else if(drawable_anims_pos%5 == 1){
						iv_record.setImageDrawable(drawable_Anims[1]);
					}else if(drawable_anims_pos%5 == 2){
						iv_record.setImageDrawable(drawable_Anims[2]);
					}else if(drawable_anims_pos%5 == 3){
						iv_record.setImageDrawable(drawable_Anims[3]);
					}else if(drawable_anims_pos%5 == 4){
						iv_record.setImageDrawable(drawable_Anims[4]);
					}
					drawable_anims_pos++;
					
				}
//				iv_record.setImageDrawable(drawable_Anims[value]);
			}
	
			@Override
			public void onTimeChanged(int recordTime, String localPath) {
//				Log.i("onTimeChanged", recordTime+"===="+localPath);
//				Log.e("onTimeChanged", recordTime%5+"====");
				if (recordTime >= BmobRecordManager.MAX_RECORD_TIME) {// 1分钟结束，发送消息
					// 需要重置按钮
					btn_speak.setPressed(false);
					btn_speak.setClickable(false);
					// 取消录音框
					layout_record.setVisibility(View.INVISIBLE);
					// 发送语音消息
					sendVoiceMessage(localPath, recordTime);
					//是为了防止过了录音时间后，会多发一条语音出去的情况。
					handler.postDelayed(new Runnable() {
	
						@Override
						public void run() {
							btn_speak.setClickable(true);
						}
					}, 1000);
				}else{}
			}
		});
	}
    
    public void initBroadCast(){
    	// 注册接收消息广播
    	receiver = new NewBroadcastReceiver();
    	IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
    	//设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
    	intentFilter.setPriority(5);
    	registerReceiver(receiver, intentFilter);
    }
    
    /**
	 * @Title: initOrRefresh
	 * @Description: 界面刷新
	 * @param
	 * @return void
	 * @throws
	 */
	private void initOrRefresh() {
		if (mAdapter != null) {
			if (MyMessageReceiver.mNewNum != 0) {// 用于更新当在聊天界面锁屏期间来了消息，这时再回到聊天页面的时候需要显示新来的消息
				int news=  MyMessageReceiver.mNewNum;//有可能锁屏期间，来了N条消息,因此需要倒叙显示在界面上
				int size = initMsgData().size();
				for(int i=(news-1);i>=0;i--){
					mAdapter.add(initMsgData().get(size-(i+1)));// 添加最后一条消息到界面显示
				}
				mListView.setSelection(mAdapter.getCount() - 1);
			} else {
				mAdapter.notifyDataSetChanged();
			}
		} else {
			mAdapter = new MessageChatAdapter(this, initMsgData());
			mListView.setAdapter(mAdapter);
		}
	}
	
	/**
	 * 加载消息历史，从数据库中读出
	 */
	private List<BmobMsg> initMsgData() {
		List<BmobMsg> list = BmobDB.create(this).queryMessages(targetId,MsgPagerNum);
		return list;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == NEW_MESSAGE) {
				BmobMsg message = (BmobMsg) msg.obj;
				String uid = message.getBelongId();
				BmobMsg m = BmobChatManager.getInstance(ChatActivity.this).getMessage(message.getConversationId(), message.getMsgTime());
				if (!uid.equals(targetId))// 如果不是当前正在聊天对象的消息，不处理
					return;
				mAdapter.add(m);
				// 定位
				mListView.setSelection(mAdapter.getCount() - 1);
				//取消当前聊天对象的未读标示
				BmobDB.create(ChatActivity.this).resetUnread(targetId);
			}
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constant.REQUESTCODE_TAKE_CAMERA:// 当取到值的时候才上传path路径下的图片到服务器
//				ShowLog("本地图片的地址：" + localCameraPath);
				sendImageMessage(localCameraPath);
				break;
			case Constant.REQUESTCODE_TAKE_LOCAL:
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						Cursor cursor = getContentResolver().query(
								selectedImage, null, null, null, null);
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex("_data");
						String localSelectPath = cursor.getString(columnIndex);
						cursor.close();
						if (localSelectPath == null
								|| localSelectPath.equals("null")) {
							ShowToast("找不到您想要的图片");
							return;
						}
						sendImageMessage(localSelectPath);
					}
				}
				break;
			case Constant.REQUESTCODE_TAKE_LOCATION:// 地理位置
				double latitude = data.getDoubleExtra("x", 0);// 维度
				double longtitude = data.getDoubleExtra("y", 0);// 经度
				String address = data.getStringExtra("address");
				if (address != null && !address.equals("")) {
					sendLocationMessage(address, latitude, longtitude);
				} else {
					ShowToast("无法获取到您的位置信息!");
				}

				break;
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		// 新消息到达，重新刷新界面
		initOrRefresh();
		// 有可能锁屏期间，在聊天界面出现通知栏，这时候需要清除通知和清空未读消息数
		BmobNotifyManager.getInstance(this).cancelNotify();
		BmobDB.create(this).resetUnread(targetId);
		//清空消息未读数-这个要在刷新之后
		MyMessageReceiver.mNewNum=0;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 监听推送的消息
		// 停止录音
		if (recordManager.isRecording()) {
			recordManager.cancelRecording();
			layout_record.setVisibility(View.GONE);
		}
		// 停止播放录音
		if (NewRecordPlayClickListener.isPlaying
				&& NewRecordPlayClickListener.currentPlayListener != null) {
			NewRecordPlayClickListener.currentPlayListener.stopPlayRecord();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		hideSoftInputView();
		try {
			unregisterReceiver(receiver);
		} catch (Exception e) {
		}
	}
	
	
	
	@Override
	public void afterTextChanged(Editable arg0) {}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before,int count) {
		if (!TextUtils.isEmpty(s)) {
			btn_chat_send.setVisibility(View.VISIBLE);
			btn_chat_keyboard.setVisibility(View.GONE);
			btn_chat_voice.setVisibility(View.GONE);
		} else {
			if (btn_chat_voice.getVisibility() != View.VISIBLE) {
				btn_chat_voice.setVisibility(View.VISIBLE);
				btn_chat_send.setVisibility(View.GONE);
				btn_chat_keyboard.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				MsgPagerNum++;
				int total = BmobDB.create(ChatActivity.this).queryChatTotalCount(targetId);
				BmobLog.i("记录总数：" + total);
				int currents = mAdapter.getCount();
				if (total <= currents) {
					ShowToast("聊天记录加载完了哦!");
				} else {
					List<BmobMsg> msgList = initMsgData();
					mAdapter.setList(msgList);
					mListView.setSelection(mAdapter.getCount() - currents - 1);
				}
				mRefreshLayout.setRefreshing(false);
			}
		}, 1000);
	}
	
	@Override
	public void onAddUser(BmobInvitation arg0) {}

	@Override
	public void onMessage(BmobMsg message) {
		Message handlerMsg = handler.obtainMessage(NEW_MESSAGE);
		handlerMsg.obj = message;
		handler.sendMessage(handlerMsg);
	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		if (!isNetConnected) {
			ShowToast(R.string.network_tips);
		}
	}

	@Override
	public void onOffline() {
//		showOfflineDialog(this);
	}

	@Override
	public void onReaded(String conversionId, String msgTime) {
		// 此处应该过滤掉不是和当前用户的聊天的回执消息界面的刷新
		if (conversionId.split("&")[1].equals(targetId)) {
			// 修改界面上指定消息的阅读状态
			for (BmobMsg msg : mAdapter.getList()) {
				if (msg.getConversationId().equals(conversionId)
						&& msg.getMsgTime().equals(msgTime)) {
					msg.setStatus(BmobConfig.STATUS_SEND_RECEIVERED);
				}
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_user_comment: // 点击文本输入框
			mListView.setSelection(mListView.getCount() - 1);
			if (layout_more.getVisibility() == View.VISIBLE) {
				layout_add.setVisibility(View.GONE);
				layout_emo.setVisibility(View.GONE);
				layout_more.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_chat_emo:// 点击笑脸图标
			if (layout_more.getVisibility() == View.GONE) {
				showEditState(true);
			} else {
				if (layout_add.getVisibility() == View.VISIBLE) {
					layout_add.setVisibility(View.GONE);
					layout_emo.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.btn_chat_add:// 添加按钮-显示图片、拍照、位置
			if (layout_more.getVisibility() == View.GONE) {
				layout_more.setVisibility(View.VISIBLE);
				layout_add.setVisibility(View.VISIBLE);
				layout_emo.setVisibility(View.GONE);
				hideSoftInputView();
			} else {
				if (layout_emo.getVisibility() == View.VISIBLE) {
					layout_emo.setVisibility(View.GONE);
					layout_add.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.btn_chat_voice:// 语音按钮
			edit_user_comment.setVisibility(View.GONE);
			layout_more.setVisibility(View.GONE);
			btn_chat_voice.setVisibility(View.GONE);
			btn_chat_keyboard.setVisibility(View.VISIBLE);
			btn_speak.setVisibility(View.VISIBLE);
			hideSoftInputView();
			break;
		case R.id.btn_chat_keyboard:// 键盘按钮，点击就弹出键盘并隐藏掉声音按钮
			showEditState(false);
			break;
		case R.id.btn_chat_send:// 发送文本
			final String msg = edit_user_comment.getText().toString();
			if (msg.equals("")) {
				ShowToast("请输入发送消息!");
				return;
			}
			boolean isNetConnected = Network.isAvailable(mContext);
			if (!isNetConnected) {
				ShowToast(R.string.network_tips);
				// return;
			}
			// 组装BmobMessage对象
			BmobMsg message = BmobMsg.createTextSendMsg(this, targetId, msg);
			message.setExtra("Bmob");
			// 默认发送完成，将数据保存到本地消息表和最近会话表中
			manager.sendTextMessage(targetUser, message);
			// 刷新界面
			refreshMessage(message);
			break;
		case R.id.tv_camera:// 拍照
			selectImageFromCamera();
			break;
		case R.id.tv_picture:// 图片
			selectImageFromLocal();
			break;
		case R.id.tv_location:// 位置
//			selectLocationFromMap();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 启动相机拍照 startCamera
	 * @Title: startCamera
	 * @throws
	 */
	public void selectImageFromCamera() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File dir = new File(Constant.BMOB_PICTURE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		localCameraPath = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent,
				Constant.REQUESTCODE_TAKE_CAMERA);
	}
	
	/**
	 * @Title: selectImage
	 * @Description: 选择图片
	 * @param
	 * @return void
	 * @throws
	 */
	public void selectImageFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, Constant.REQUESTCODE_TAKE_LOCAL);
	}
	
	/**
	 * @Title: selectLocationFromMap
	 * @Description: 启动地图
	 * @param
	 * @return void
	 * @throws
	 */
	private void selectLocationFromMap() {
//		Intent intent = new Intent(this, LocationActivity.class);
//		intent.putExtra("type", "select");
//		startActivityForResult(intent, Constant.REQUESTCODE_TAKE_LOCATION);
	}
	
	/**
	 * @Title: showResendDialog
	 * @Description: 显示重发按钮 showResendDialog
	 * @param @param recent
	 * @return void
	 * @throws
	 */
	public void showResendDialog(final View parentV, View v, final Object values) {
//		DialogTips dialog = new DialogTips(this, "确定重发该消息", "确定", "取消", "提示",
//				true);
//		// 设置成功事件
//		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialogInterface, int userId) {
//				if (((BmobMsg) values).getMsgType() == BmobConfig.TYPE_IMAGE
//						|| ((BmobMsg) values).getMsgType() == BmobConfig.TYPE_VOICE) {// 图片和语音类型的采用
//					resendFileMsg(parentV, values);
//				} else {
//					resendTextMsg(parentV, values);
//				}
//				dialogInterface.dismiss();
//			}
//		});
//		// 显示确认对话框
//		dialog.show();
//		dialog = null;
	}
	
	/**
	 * @ClassName: VoiceTouchListen
	 * @Description: 长按说话
	 * @author smile
	 * @date 2014-7-1 下午6:10:16
	 */
	class VoiceTouchListen implements View.OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (! new SdCardUtil().isSdCardAvailable()) {
					ShowToast("发送语音需要sdcard支持！");
					return false;
				}
				try {
					v.setPressed(true);
					layout_record.setVisibility(View.VISIBLE);
					tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
					// 开始录音
					recordManager.startRecording(targetId);
				} catch (Exception e) {}
				return true;
			case MotionEvent.ACTION_MOVE: {
				if (event.getY() < 0) {
					tv_voice_tips
							.setText(getString(R.string.voice_cancel_tips));
					tv_voice_tips.setTextColor(Color.RED);
				} else {
					tv_voice_tips.setText(getString(R.string.voice_up_tips));
					tv_voice_tips.setTextColor(Color.WHITE);
				}
				return true;
			}
			case MotionEvent.ACTION_UP:
				v.setPressed(false);
				drawable_anims_time = 0;
				drawable_anims_pos = 0;
				layout_record.setVisibility(View.INVISIBLE);
				try {
					if (event.getY() < 0) {// 放弃录音
						recordManager.cancelRecording();
						BmobLog.i("voice", "放弃发送语音");
					} else {
						int recordTime = recordManager.stopRecording();
						if (recordTime > 1) {
							// 发送语音文件
							BmobLog.i("voice", "发送语音");
							sendVoiceMessage(recordManager.getRecordFilePath(targetId),
									recordTime);
						} else {// 录音时间过短，则提示录音过短的提示
							layout_record.setVisibility(View.GONE);
							showShortToast().show();
						}
					}
				} catch (Exception e) {
				}
				return true;
			default:
				return false;
			}
		}
	}
	
	/**
	 * 
	 * @Title: sendImageMessage
	 * @Description: 发送语音消息
	 * @param @param localPath
	 * @return void
	 * @throws
	 */
	private void sendVoiceMessage(String local, int length) {
		manager.sendVoiceMessage(targetUser, local, length,new UploadListener() {
			@Override
			public void onStart(BmobMsg msg) {
				refreshMessage(msg);
			}

			@Override
			public void onSuccess() {
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int error, String arg1) {
				Log.e("","上传语音失败 -->arg1：" + arg1);
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/**
	 * 
	 * @Title: refreshMessage
	 * @Description: 刷新界面
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshMessage(BmobMsg msg) {
		// 更新界面
		mAdapter.add(msg);
		mListView.setSelection(mAdapter.getCount() - 1);
		edit_user_comment.setText("");
	}
	
	/**
	 * 显示录音时间过短的Toast
	 * @Title: showShortToast
	 * @return void
	 * @throws
	 */
	private Toast showShortToast() {
		if (toast == null) {
			toast = new Toast(this);
		}
		View view = LayoutInflater.from(this).inflate(
				R.layout.include_chat_voice_short, null);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(50);
		return toast;
	}
	
	/**
	 * @Title: showEditState
	 * @Description: 根据是否点击笑脸来显示文本输入框的状态
	 * @param @param isEmo: 用于区分文字和表情
	 * @return void
	 * @throws
	 */
	private void showEditState(boolean isEmo) {
		edit_user_comment.setVisibility(View.VISIBLE);
		btn_chat_keyboard.setVisibility(View.GONE);
		btn_chat_voice.setVisibility(View.VISIBLE);
		btn_speak.setVisibility(View.GONE);
		edit_user_comment.requestFocus();
		if (isEmo) {
			layout_more.setVisibility(View.VISIBLE);
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
	
	/**
	 * @Title: sendImageMessage
	 * @Description: 默认先上传本地图片，之后才显示出来 
	 * @param @param localPath
	 * @return void
	 * @throws
	 */
	private void sendImageMessage(String local) {
		if (layout_more.getVisibility() == View.VISIBLE) {
			layout_more.setVisibility(View.GONE);
			layout_add.setVisibility(View.GONE);
			layout_emo.setVisibility(View.GONE);
		}
		manager.sendImageMessage(targetUser, local, new UploadListener() {

			@Override
			public void onStart(BmobMsg msg) {
				Log.e("","开始上传onStart：" + msg.getContent() + ",状态："
						+ msg.getStatus());
				refreshMessage(msg);
			}

			@Override
			public void onSuccess() {
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int error, String arg1) {
//				ShowLog("上传失败 -->arg1：" + arg1);
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/**
	 * 
	 * @Title: sendLocationMessage
	 * @Description: 发送位置信息
	 * @param @param address
	 * @param @param latitude
	 * @param @param longtitude
	 * @return void
	 * @throws
	 */
	private void sendLocationMessage(String address, double latitude,
			double longtitude) {
		if (layout_more.getVisibility() == View.VISIBLE) {
			layout_more.setVisibility(View.GONE);
			layout_add.setVisibility(View.GONE);
			layout_emo.setVisibility(View.GONE);
		}
		// 组装BmobMessage对象
		BmobMsg message = BmobMsg.createLocationSendMsg(this, targetId,
				address, latitude, longtitude);
		// 默认发送完成，将数据保存到本地消息表和最近会话表中
		manager.sendTextMessage(targetUser, message);
		// 刷新界面
		refreshMessage(message);
	}
	
	/**
	 * 新消息广播接收者
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String from = intent.getStringExtra("fromId");
			String msgId = intent.getStringExtra("msgId");
			String msgTime = intent.getStringExtra("msgTime");
			// 收到这个广播的时候，message已经在消息表中，可直接获取
			if(TextUtils.isEmpty(from)&&TextUtils.isEmpty(msgId)&&TextUtils.isEmpty(msgTime)){
				BmobMsg msg = BmobChatManager.getInstance(ChatActivity.this).getMessage(msgId, msgTime);
				if (!from.equals(targetId))// 如果不是当前正在聊天对象的消息，不处理
					return;
				//添加到当前页面
				mAdapter.add(msg);
				// 定位
				mListView.setSelection(mAdapter.getCount() - 1);
				//取消当前聊天对象的未读标示
				BmobDB.create(ChatActivity.this).resetUnread(targetId);
			}
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}
	
	private View getGridView(final int i) {
		View view = View.inflate(this, R.layout.include_emo_gridview, null);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		List<FaceText> list = new ArrayList<FaceText>();
		if (i == 0) {
			list.addAll(emos.subList(0, 21));
		} else if (i == 1) {
			list.addAll(emos.subList(21, emos.size()));
		}
		final EmoteAdapter gridAdapter = new EmoteAdapter(ChatActivity.this,
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
				} catch (Exception e) {}
			}
		});
		return view;
	}

	
}
