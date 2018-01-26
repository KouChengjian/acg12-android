package com.acg12.lib.net.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.acg12.lib.conf.DLStatus;
import com.acg12.lib.dao.DaoBaseImpl;
import com.acg12.lib.entity.DownLoad;
import com.acg12.lib.utils.IOUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/5/24.
 */
public class DownloadProgressHandler {

    private String url;
    private String path;
    private String name;
    private int childTaskCount;

    private Context context;

    private DownLoadCallback downloadCallback;
    private DownLoad downloadData;

    private DownloadFileTask fileTask;

    private int mCurrentState = DLStatus.NONE;

    //是否支持断点续传
    private boolean isSupportRange;

    //重新开始下载需要先进行取消操作
    private boolean isNeedRestart;

    //记录已经下载的大小
    private int currentLength = 0;
    //记录文件总大小
    private int totalLength = 0;
    //记录已经暂停或取消的线程数
    private int tempChildTaskCount = 0;

    private long lastProgressTime;


    public void setDownLoadCallback(DownLoadCallback downloadCallback){
        this.downloadCallback = downloadCallback;
    }

    public DownloadProgressHandler(Context context, DownLoad downloadData, DownLoadCallback downloadCallback) {
        this.context = context;
        this.downloadCallback = downloadCallback;

        this.url = downloadData.getUrl();
        this.path = downloadData.getPath();
        this.name = downloadData.getName();
        this.childTaskCount = downloadData.getChildTaskCount();

        DownLoad dbData = DaoBaseImpl.getInstance(context).queryUrlDownLoad(url);
        this.downloadData = dbData == null ? downloadData : dbData;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public int getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(int mCurrentState){
        this.mCurrentState = mCurrentState;
    }

    public DownLoad getDownloadData() {
        return downloadData;
    }

    public void setFileTask(DownloadFileTask fileTask) {
        this.fileTask = fileTask;
    }

    /**
     * 下载中退出时保存数据、释放资源
     */
    public void destroy() {
        if (mCurrentState == DLStatus.CANCEL || mCurrentState == DLStatus.PAUSE) {
            return;
        }
        fileTask.destroy();
    }

    /**
     * 暂停（正在下载才可以暂停）
     * 如果文件不支持断点续传则不能进行暂停操作
     */
    public void pause() {
        if (mCurrentState == DLStatus.PROGRESS) {
            fileTask.pause();
        }
    }

    /**
     * 取消（已经被取消、下载结束则不可取消）
     */
    public void cancel(boolean isNeedRestart) {
        this.isNeedRestart = isNeedRestart;
        if (mCurrentState == DLStatus.PROGRESS) {
            fileTask.cancel();
        } else if (mCurrentState == DLStatus.PAUSE || mCurrentState == DLStatus.ERROR) {
            mHandler.sendEmptyMessage(DLStatus.CANCEL);
        }
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int mLastSate = mCurrentState;
            mCurrentState = msg.what;
            downloadData.setState(mCurrentState);

            switch (mCurrentState) {
                case DLStatus.START:
                    Bundle bundle = msg.getData();
                    totalLength = bundle.getInt("totalLength");
                    currentLength = bundle.getInt("currentLength");
                    String lastModify = bundle.getString("lastModify");
                    isSupportRange = bundle.getBoolean("isSupportRange");

                    if (currentLength == 0) {
                        DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(name);
                        if(dl == null){
                            DownLoad d = new DownLoad(url, path, childTaskCount, name, currentLength, totalLength, lastModify, System.currentTimeMillis());
                            DaoBaseImpl.getInstance(context).saveDownload(d);
                        }
                    } else {
                        DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(name);
                        if(dl != null){
                            dl.setState(DLStatus.START);
                            dl.setCurrentLength(currentLength);
                            dl.setPercentage(IOUtils.getPercentage(currentLength, totalLength));
                            DaoBaseImpl.getInstance(context).saveDownload(dl);
                        }
                    }

                    if (downloadCallback != null) {
                        downloadCallback.onStart(currentLength, totalLength, IOUtils.getPercentage(currentLength, totalLength));
                    }
                    break;
                case DLStatus.PROGRESS:
                    synchronized (this) {
                        currentLength += msg.arg1;
                        downloadData.setPercentage(IOUtils.getPercentage(currentLength, totalLength));

                        if (downloadCallback != null && (System.currentTimeMillis() - lastProgressTime >= 1000 || currentLength == totalLength)) {
                            downloadCallback.onProgress(currentLength, totalLength, IOUtils.getPercentage(currentLength, totalLength));
                            lastProgressTime = System.currentTimeMillis();
                        }

                        if (currentLength == totalLength) {
                            sendEmptyMessage(DLStatus.FINISH);
                        }
                    }
                    break;
                case DLStatus.CANCEL:
                    synchronized (this) {
                        tempChildTaskCount++;
                        if (tempChildTaskCount == childTaskCount || mLastSate == DLStatus.PAUSE || mLastSate == DLStatus.ERROR) {
                            tempChildTaskCount = 0;
                            if (downloadCallback != null) {
                                downloadCallback.onProgress(0, totalLength, 0);
                            }
                            currentLength = 0;
                            if (isSupportRange) {
                                DaoBaseImpl.getInstance(context).delDownLoad(name);
                                IOUtils.deleteFile(new File(path, name + ".temp"));
                            }
                            IOUtils.deleteFile(new File(path, name));
                            if (downloadCallback != null) {
                                downloadCallback.onCancel();
                            }

                            if (isNeedRestart) {
                                isNeedRestart = false;
                                DownloadManger.getInstance(context).innerRestart(url);
                            }
                        }
                    }
                    break;
                case DLStatus.PAUSE:
                    synchronized (this) {
                        if (isSupportRange) {
                            DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(name);
                            if(dl != null){
                                dl.setState(DLStatus.PAUSE);
                                dl.setCurrentLength(currentLength);
                                dl.setPercentage(IOUtils.getPercentage(currentLength, totalLength));
                                DaoBaseImpl.getInstance(context).saveDownload(dl);
                            }
                        }

                        if (downloadCallback != null) {
                            downloadCallback.onPause();
                        }
                    }
                    break;
                case DLStatus.FINISH:
                    if (isSupportRange) {
                        IOUtils.deleteFile(new File(path, name + ".temp"));
                        if (isSupportRange) {
                            DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(name);
                            if(dl != null){
                                dl.setState(DLStatus.FINISH);
                                dl.setCurrentLength(currentLength);
                                dl.setPercentage(IOUtils.getPercentage(currentLength, totalLength));
                                DaoBaseImpl.getInstance(context).saveDownload(dl);
                            }
                        }
//                        DaoBaseImpl.getInstance().delDownLoad(name);
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onFinish(new File(path, name));
                    }
                    break;
                case DLStatus.DESTROY:
                    synchronized (this) {
                        if (isSupportRange) {
                            DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(name);
                            if(dl != null){
                                dl.setState(DLStatus.DESTROY);
                                dl.setCurrentLength(currentLength);
                                dl.setPercentage(IOUtils.getPercentage(currentLength, totalLength));
                                DaoBaseImpl.getInstance(context).saveDownload(dl);
                            }
                        }
                    }
                    break;
                case DLStatus.ERROR:
                    if (isSupportRange) {
                        DownLoad dl = DaoBaseImpl.getInstance(context).queryUrlDownLoad(url);
                        if(dl != null){
                            dl.setState(DLStatus.ERROR);
                            dl.setCurrentLength(currentLength);
                            dl.setPercentage(IOUtils.getPercentage(currentLength, totalLength));
                            DaoBaseImpl.getInstance(context).saveDownload(dl);
                        }
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onError((String) msg.obj);
                    }
                    break;
            }
        }
    };

}
