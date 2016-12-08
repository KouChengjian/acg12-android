package com.kcj.animationfriend;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.config.BmobConstant;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.im.inteface.OnReceiveListener;
import cn.bmob.im.util.BmobJsonUtil;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.ui.MainActivity;
import com.kcj.animationfriend.ui.NewFriendActivity;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.util.Network;
import com.liteutil.util.Log;

/**
 * @ClassName: MyMessageReceiver
 * @Description: 推送消息接收器
 * @author: smile
 * @date: 2014-5-30 下午4:01:13
 */
public class MyMessageReceiver extends BroadcastReceiver {

	// 事件监听
	public static ArrayList<EventListener> ehList = new ArrayList<EventListener>();

	public static final int NOTIFY_ID = 0x000;
	public static int mNewNum = 0;//
	public BmobUserManager userManager;
	public BmobChatUser currentUser;

	// 如果你想发送自定义格式的消息，请使用sendJsonMessage方法来发送Json格式的字符串，然后你按照格式自己解析并处理

	@Override
	public void onReceive(Context context, Intent intent) {
		// 获取推送消息
		String json = intent.getStringExtra("msg");
		Log.e("onReceive","收到的message == " + json);
		userManager = BmobUserManager.getInstance(context);
		currentUser = userManager.getCurrentUser();
		boolean isNetConnected = Network.isConnected(context);
		if (isNetConnected) {
			parseMessage(context, json);
		} else {
			for (int i = 0; i < ehList.size(); i++) {
				((EventListener) ehList.get(i)).onNetChange(isNetConnected);
			}
		}
	}

	/**
	 * @Title: parseMessage
	 * @Description: 解析Json字符串
	 * @param context
	 * @param json
	 * @return void
	 * @throws
	 */
	private void parseMessage(final Context context, String json) {
		JSONObject jo;
		try 
		{
			jo = new JSONObject(json);
			final String alert = BmobJsonUtil.getString(jo, "alert");
			if(alert == null || alert.isEmpty()){
				String tag = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TAG);
				//下线通知
				if(tag.equals(BmobConfig.TAG_OFFLINE))
				{
					if(currentUser!=null){
						if (ehList.size() > 0) {// 有监听的时候，传递下去
							for (EventListener handler : ehList)
								handler.onOffline();
						}else{
							//清空数据
							MyApplication.getInstance().logout();
						}
					}
				}
				else
				{
					String fromId = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TARGETID);
					//增加消息接收方的ObjectId--目的是解决多账户登陆同一设备时，无法接收到非当前登陆用户的消息。
				    final String toId = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TOID);
					String msgTime = BmobJsonUtil.getString(jo,BmobConstant.PUSH_READED_MSGTIME);
					//该消息发送方不为黑名单用户
					if(fromId!=null && !BmobDB.create(context,toId).isBlackUser(fromId))
					{
						//不携带tag标签--此可接收陌生人的消息
						if(TextUtils.isEmpty(tag))
						{
	                        BmobChatManager.getInstance(context).createReceiveMsg(json, new OnReceiveListener() {
								
								@Override
								public void onSuccess(BmobMsg msg) {
									if (ehList.size() > 0) {// 有监听的时候，传递下去
										for (int i = 0; i < ehList.size(); i++) {
											((EventListener) ehList.get(i)).onMessage(msg);
										}
									} else {
										boolean isAllow = MyApplication.getInstance().getSpUtil().isAllowPushNotify();
										if(isAllow && currentUser!=null && currentUser.getObjectId().equals(toId)){//当前登陆用户存在并且也等于接收方id
											mNewNum++;
											showMsgNotify(context,msg);
										}
									}
								}
								
								@Override
								public void onFailure(int code, String arg1) {
									Log.i("获取接收的消息失败："+arg1);
								}
							});
						}
						//带tag标签
						else
						{
							if(tag.equals(BmobConfig.TAG_ADD_CONTACT)){
								//保存好友请求道本地，并更新后台的未读字段
								BmobInvitation message = BmobChatManager.getInstance(context).saveReceiveInvite(json, toId);
								if(currentUser!=null){//有登陆用户
									if(toId.equals(currentUser.getObjectId())){
										if (ehList.size() > 0) {// 有监听的时候，传递下去
											for (EventListener handler : ehList)
												handler.onAddUser(message);
										}else{
											showOtherNotify(context, message.getFromname(), toId,  message.getFromname()+"请求添加好友", NewFriendActivity.class);
										}
									}
								}
								if(currentUser.getObjectId().equals(Constant.ADMIN_ID)){
//									AddAgree(context ,message);
								}
							}else if(tag.equals(BmobConfig.TAG_ADD_AGREE)){
								String username = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TARGETUSERNAME);
								//收到对方的同意请求之后，就得添加对方为好友--已默认添加同意方为好友，并保存到本地好友数据库
								BmobUserManager.getInstance(context).addContactAfterAgree(username, new FindListener<BmobChatUser>() {
									
									@Override
									public void onError(int arg0, final String arg1) {}
									
									@Override
									public void onSuccess(List<BmobChatUser> arg0) {
										//保存到内存中
										MyApplication.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(context).getContactList()));
									}
								});
								//显示通知
								showOtherNotify(context, username, toId,  username+"同意添加您为好友", MainActivity.class);
								//创建一个临时验证会话--用于在会话界面形成初始会话
								BmobMsg.createAndSaveRecentAfterAgree(context, json);
								
							}else if(tag.equals(BmobConfig.TAG_READED)){//已读回执
								String conversionId = BmobJsonUtil.getString(jo,BmobConstant.PUSH_READED_CONVERSIONID);
								if(currentUser!=null){
									//更改某条消息的状态
									BmobChatManager.getInstance(context).updateMsgStatus(conversionId, msgTime);
									if(toId.equals(currentUser.getObjectId())){
										if (ehList.size() > 0) {// 有监听的时候，传递下去--便于修改界面
											for (EventListener handler : ehList)
												handler.onReaded(conversionId, msgTime);
										}
									}
								}
							}
						}
					}
					//在黑名单期间所有的消息都应该置为已读，不然等取消黑名单之后又可以查询的到
					else
					{
						BmobChatManager.getInstance(context).updateMsgReaded(true, fromId, msgTime);
						Log.i("该消息发送方为黑名单用户");
					}
				}
			}else{
				User user = HttpProxy.getCurrentUser(context);
				if(user == null){
					showOtherNotify(context,alert);
				}else{
					BmobChatManager.getInstance(context).createReceiveMsg(settleJson(user,alert), new OnReceiveListener() {
						
						@Override
						public void onSuccess(BmobMsg msg) {
							if (ehList.size() > 0) {// 有监听的时候，传递下去
								for (int i = 0; i < ehList.size(); i++) {
									((EventListener) ehList.get(i)).onMessage(msg);
								}
							} else {
								showOtherNotify(context,alert);
							}
						}
						
						@Override
						public void onFailure(int code, String arg1) {
							Log.e("获取接收的消息失败："+arg1);
						}
					});
				}
				
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			//这里截取到的有可能是web后台推送给客户端的消息，也有可能是开发者自定义发送的消息，需要开发者自行解析和处理
			Log.e("parseMessage错误：==="+e.getMessage());
		}
	}
	
	public String settleJson(User user,String alert){
		JSONObject alertJS = new JSONObject();
		try {
			JSONObject apsJS = new JSONObject();
			apsJS.put("sound", "");
			apsJS.put("alert", "漫友发来了一个消息");
			apsJS.put("badge", 0);
			alertJS.put("tag", ""); 
			alertJS.put("tId", user.getObjectId());
			alertJS.put("fId", Constant.ADMIN_ID);  
			alertJS.put("mt", "1");
			alertJS.put("ft", String.valueOf(System.currentTimeMillis()/1000).toString()); // 时间
			alertJS.put("ex", "Bmob");
			alertJS.put("mc", alert);
			alertJS.put("aps", apsJS);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return alertJS.toString();
	}

	/** 
	 *  显示与聊天消息的通知
	 * @Title: showNotify
	 * @return void
	 * @throws
	 */
	public void showMsgNotify(Context context,BmobMsg msg) {
		// 更新通知栏
		int icon = R.drawable.logo;
		String trueMsg = "";
		if(msg.getMsgType()==BmobConfig.TYPE_TEXT && msg.getContent().contains("\\ue")){
			trueMsg = "[表情]";
		}else if(msg.getMsgType()==BmobConfig.TYPE_IMAGE){
			trueMsg = "[图片]";
		}else if(msg.getMsgType()==BmobConfig.TYPE_VOICE){
			trueMsg = "[语音]";
		}else if(msg.getMsgType()==BmobConfig.TYPE_LOCATION){
			trueMsg = "[位置]";
		}else{
			trueMsg = msg.getContent();
		}
		CharSequence tickerText = msg.getBelongUsername() + ":" + trueMsg;
		String contentTitle = msg.getBelongUsername()+ " (" + mNewNum + "条新消息)";
		
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		boolean isAllowVoice = MyApplication.getInstance().getSpUtil().isAllowVoice();
		boolean isAllowVibrate = MyApplication.getInstance().getSpUtil().isAllowVibrate();
		
		BmobNotifyManager.getInstance(context).showNotifyWithExtras(isAllowVoice,isAllowVibrate,icon, tickerText.toString(), contentTitle, tickerText.toString(),intent);
	}
	
	
	/** 
	 * 显示其他Tag的通知
	 * showOtherNotify
	 */
	public void showOtherNotify(Context context,String username,String toId,String ticker,Class<?> cls){
		boolean isAllow = MyApplication.getInstance().getSpUtil().isAllowPushNotify();
		boolean isAllowVoice = MyApplication.getInstance().getSpUtil().isAllowVoice();
		boolean isAllowVibrate = MyApplication.getInstance().getSpUtil().isAllowVibrate();
		if(isAllow && currentUser!=null && currentUser.getObjectId().equals(toId)){
			//同时提醒通知
			BmobNotifyManager.getInstance(context).showNotify(isAllowVoice,isAllowVibrate,R.drawable.logo, ticker,username, ticker.toString(),NewFriendActivity.class);
		}
	}
	
	/**
	 * 消息推送的通知
	 */
	public void showOtherNotify(Context context ,String smg ){
//			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//			Notification n = new Notification();  
//	        n.icon = R.drawable.logo;  
//	        n.tickerText = "漫友收到消息推送";  
//	        n.when = System.currentTimeMillis();  
//	        //n.flags=Notification.FLAG_ONGOING_EVENT;  
//	        Intent i = new Intent();  
//	        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);  
//	        n.setLatestEventInfo(context, "消息", smg, pi);  
//	        n.defaults |= Notification.DEFAULT_SOUND;
//	        n.flags = Notification.FLAG_AUTO_CANCEL;
//	        nm.notify(1, n);
	}
		
	public void AddAgree(final Context context ,BmobInvitation message){
		//同意添加好友
		BmobUserManager.getInstance(context).agreeAddContact(message, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				MyApplication.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(context).getContactList()));	
			}
			
			@Override
			public void onFailure(int arg0, final String arg1) {
			}
		});
	}
}
