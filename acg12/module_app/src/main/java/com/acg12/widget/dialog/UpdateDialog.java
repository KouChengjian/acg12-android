package com.acg12.widget.dialog;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.Update;
import com.acg12.net.download.DUtil;
import com.acg12.net.download.DownLoadCallback;
import com.acg12.net.download.DownloadManger;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.widget.BaseDialog;

import java.io.File;

public class UpdateDialog extends BaseDialog implements View.OnClickListener{

	private Context context;
	private TextView tv_content;
	private Button btn_checkable;
	private Update update;
	private DownloadManger downloadManger;
	private NotificationCompat.Builder mBuilder;
	private NotificationManager mNotificationManager;
	private RemoteViews mRemoteViews;
	private float mProgress = 0;
	private int notifyId = 100;

	Form form = Form.set;

	public enum Form{
		main ,set;
	}
	
	public UpdateDialog(Context context , Update update) {
		super(context);
		this.update = update;
		this.context = context;
		initNotify();
		setDialogContentView(R.layout.include_dialog_update_app);
		tv_content = (TextView) findViewById(R.id.tv_content);
		btn_checkable = (Button) findViewById(R.id.btn_checkable);

		setMessage();
		btn_checkable.setSelected(update.isIgnore());

		
		btn_checkable.setOnClickListener(this);
	}

	public void setMessage(){
		String title = "现在最新版本为："+update.getVersionName()+"\\n";
		String content = update.getMessage();
		content = title + content;
		tv_content.setText(content.replace("\\n","\n"));
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		if(id == R.id.btn_checkable){
			if(update.isIgnore()){
				update.setIgnore(false);
				update.setOldVersionCode("");
				btn_checkable.setSelected(update.isIgnore());
			} else {
				update.setIgnore(true);
				update.setOldVersionCode(update.getVersionCode());
				btn_checkable.setSelected(update.isIgnore());
			}
			DaoBaseImpl.getInstance(context).saveUpdate(update);
		}
	}

	public void show(Form f) {
		form = f;
		show();
	}

	public void show() {
		if(update.getDialogStatus() == 0){
			if(form == Form.set){
				Toastor.ShowToast("当前版本为最新");
			}
			return ;
		}else if(update.getDialogStatus() == 1){ // 普通更新
			setCancelable(true);
			setButton1("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			});
//			setButton2Background(R.drawable.selector_common_btn);
			setButton2("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
					startDownload();
				}
			});
		} else if(update.getDialogStatus() == 2){ // 强制更新
			setCancelable(false);
//			setButton2Background(R.drawable.selector_common_btn);
			setButton2("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
//					DownloadService.getDownloadManager().startDownloadApp(mContext ,update, true, false);
				}
			});
		} else {
			return;
		}
		super.show();
	}

	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT
	 * 点击去除: Notification.FLAG_AUTO_CANCEL
	 */
	public PendingIntent getDefalutIntent(int flags){
		return PendingIntent.getActivity(mContext, 1, new Intent(), flags);
	}

	public PendingIntent getUpdataIntent(File file){
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, 0);
		return pendingIntent;
	}

	public void initNotify(){
		mBuilder = new NotificationCompat.Builder(mContext);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(getDefalutIntent(0))
				.setNumber(5)//显示数量
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				//.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.mipmap.ic_logo);

		mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
		mBuilder.setAutoCancel(true);
		mRemoteViews = new RemoteViews(AppUtil.getPackageInfo(context).packageName, R.layout.include_notify_progress);
		mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.mipmap.ic_logo);
		mBuilder.setContent(mRemoteViews).setContentIntent(getDefalutIntent(0)).setTicker("");
		Notification nitify = mBuilder.build();
		nitify.contentView = mRemoteViews;
//		mNotificationManager.notify(notifyId, nitify);

		mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, (int)mProgress, false);
		mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
		mRemoteViews.setViewVisibility(R.id.tv_custom_progress_status, View.VISIBLE);
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, mProgress+"%");
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "开始下载（"+update.getVersionName()+"）");
	}

	public void showCustomProgressNotify(float progress) {
		mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, (int)progress, false);
		mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
		mRemoteViews.setViewVisibility(R.id.tv_custom_progress_status, View.VISIBLE);
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, progress+"%");
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "开始下载（"+update.getVersionName()+"）");
		Notification nitify = mBuilder.build();
		mNotificationManager.notify(notifyId, nitify);
	}

	public void startDownload(){
		final String name = System.currentTimeMillis()+"=";
		// http://cn-gdfs4-dx-v-06.acgvideo.com/vg2/f/f8/2286361-1.mp4?expires=1496826900&platform=html5&ssig=ZPuFfdNa1qykwx8VWvhW3g&oi=3071080385&nfa=fkYkF/LEe5xFyJPq/bZ9eQ==&dynamic=1&hfa=2063897260
		// http://1.82.242.43/imtt.dd.qq.com/16891/DC9E925209B19E7913477E7A0CCE6E52.apk
		String url = "http://1.82.242.43/imtt.dd.qq.com/16891/DC9E925209B19E7913477E7A0CCE6E52.apk";

		downloadManger = DUtil.initDownloadBuilder(mContext)
				.url(url)
				.path(Environment.getExternalStorageDirectory() + "/DUtil/")
				.name(name + ".apk")
				.childTaskCount(1)
				.build()
				.start(new DownLoadCallback() {

					@Override
					public void onStart(long currentSize, long totalSize, float progress) {
//						mTip.setText(name + "：准备下载中...");
//						progressBar.setProgress((int) progress);
//						mProgress.setText(IOUtils.formatSize(currentSize) + " / " + IOUtils.formatSize(totalSize) + "--------" + progress + "%");
					}

					@Override
					public void onProgress(long currentSize, long totalSize, float progress) {
						showCustomProgressNotify(progress);
						LogUtil.e((int) progress+"===");
//						mTip.setText(name + "：下载中...");
//						progressBar.setProgress((int) progress);
//						mProgress.setText(IOUtils.formatSize(currentSize) + " / " + IOUtils.formatSize(totalSize) + "--------" + progress + "%");
					}

					@Override
					public void onPause() {
//						mTip.setText(name + "：暂停中...");
					}

					@Override
					public void onCancel() {
//						mTip.setText(name + "：已取消...");
					}

					@Override
					public void onFinish(File file) {
//						mTip.setText(name + "：下载完成...");
						Uri uri = Uri.fromFile(file);
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setDataAndType(uri, "application/vnd.android.package-archive");
						mContext.startActivity(intent);
					}

					@Override
					public void onWait() {

					}

					@Override
					public void onError(String error) {
						LogUtil.e(error);
					}
				});
	}

}