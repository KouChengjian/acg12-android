package com.kcj.animationfriend.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.adapter.base.DefaultDownloadViewHolder;
import com.kcj.animationfriend.adapter.base.DownloadViewHolder;
import com.kcj.animationfriend.bean.Download;
import com.kcj.animationfriend.listener.DownloadCallback;
import com.liteutil.exception.DbException;
import com.liteutil.http.LiteHttp;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.request.RequestParams;
import com.liteutil.http.task.PriorityExecutor;
import com.liteutil.orm.db.DataBase;
import com.liteutil.orm.db.assit.QueryBuilder;


/**
 * @ClassName: DownloadManager
 * @Description: 下载管理
 * @author: KCJ
 * @date:  
 */
public class DownloadManager {
	
	private static DownloadManager instance;
	public List<Download> downloadInfoList = new ArrayList<Download>();
	public NotificationManager mNotificationManager;//Notification管理
	public NotificationCompat.Builder mBuilder;
	public int maxDownloadThread = 3;
    public Context mContext;
    
    private DataBase db;
    private final static int MAX_DOWNLOAD_THREAD = 2; // 有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD);
    private final ConcurrentHashMap<Download, DownloadCallback>callbackMap = 
    		new ConcurrentHashMap<Download, DownloadCallback>(5);
	
    public DownloadManager() {
    	this.mContext = MyApplication.getInstance();
    	db = UserProxy.getLiteOrmInstance();
    	List<Download> infoList = db.query(Download.class);
    	if (infoList != null) {
            for (Download info : infoList) {
                if (info.getState().value() < DownloadState.FINISHED.value()) {
                    info.setState(DownloadState.STOPPED);
                }
                downloadInfoList.add(info);
            }
        }   
        if(mNotificationManager == null){
            mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        }
    }
    
    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }
    
    public void updateDownloadInfo(Download info) throws DbException {
    	db.save(info);
    }

    public int getDownloadListCount() {
        return downloadInfoList.size();
    }

    public Download getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }
    
    
    
    public synchronized void startDownload(String url, String label, String savePath,
            boolean autoResume, boolean autoRename,DownloadViewHolder viewHolder) throws DbException {
    	String fileSavePath = new File(savePath).getAbsolutePath();
    	QueryBuilder qb = new QueryBuilder(Download.class);
        qb = new QueryBuilder(Download.class).whereEquals("fileName", label).whereAppendAnd()
             .whereEquals("fileSavePath", fileSavePath);
        List<Download> list = db.<Download>query(qb);
        Download downloadInfo = null;
        if(list.size() > 0){
        	downloadInfo = list.get(0);
        	if (downloadInfo != null) {
                DownloadCallback callback = callbackMap.get(downloadInfo);
                if (callback != null) {
                    if (viewHolder == null) {
                        viewHolder = new DefaultDownloadViewHolder(null, downloadInfo);
                    }
                    if (callback.switchViewHolder(viewHolder)) {
                        return;
                    } else {
                        callback.cancel();
                    }
                }
            }
        }
        // create download info
        if (downloadInfo == null) {
            downloadInfo = new Download();
            downloadInfo.setDownLoadUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setFileName(label);
            downloadInfo.setFileSavePath(fileSavePath);
            db.save(downloadInfo);
        }
        
        // start downloading
        if (viewHolder == null) {
            viewHolder = new DefaultDownloadViewHolder(null, downloadInfo);
        }
        DownloadCallback callback = new DownloadCallback(viewHolder);
        callback.setDownloadManager(this);
        callback.switchViewHolder(viewHolder);
        RequestParams params = new RequestParams(url);
        params.setAutoResume(downloadInfo.isAutoResume());
        params.setAutoRename(downloadInfo.isAutoRename());
        params.setSaveFilePath(downloadInfo.getFileSavePath());
        params.setExecutor(executor);
        params.setCancelFast(true);
        Callback.Cancelable cancelable = LiteHttp.http().get(params, callback);
        callback.setCancelable(cancelable);
        callbackMap.put(downloadInfo, callback);

        if (!downloadInfoList.contains(downloadInfo)) {
            downloadInfoList.add(downloadInfo);
        }
    }
    
    public void stopDownload(int index) {
        Download downloadInfo = downloadInfoList.get(index);
        stopDownload(downloadInfo);
    }

    public void stopDownload(Download downloadInfo) {
        Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    public void stopAllDownload() {
        for (Download downloadInfo : downloadInfoList) {
            Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
            if (cancelable != null) {
                cancelable.cancel();
            }
        }
    }

    public void removeDownload(int index) throws DbException {
        Download downloadInfo = downloadInfoList.get(index);
        db.delete(downloadInfo);
        stopDownload(downloadInfo);
        downloadInfoList.remove(index);
    }

    public void removeDownload(Download downloadInfo) throws DbException {
        db.delete(downloadInfo);
        stopDownload(downloadInfo);
        downloadInfoList.remove(downloadInfo);
    }
    
}
