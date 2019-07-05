package com.acg12.utils.download.task;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.acg12.cache.dto.DownLoadDao;
import com.acg12.entity.po.DownLoadEntity;
import com.acg12.utils.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/24.
 */
public class DownloadFileTask implements Runnable {

    private int EACH_TEMP_SIZE = 16;
    private int TEMP_FILE_TOTAL_SIZE;//临时文件的总大小

    private int BUFFER_SIZE = 4096;

    private Context context;

    private String url;
    private String path;
    private String name;
    private int childTaskCount;
    private Handler handler;

    private boolean IS_PAUSE;
    private boolean IS_DESTROY;
    private boolean IS_CANCEL;
    private ArrayList<Call> callList;

    public DownloadFileTask(Context context, DownLoadEntity downloadData, Handler handler) {
        this.context = context;
        this.url = downloadData.getUrl();
        this.path = downloadData.getPath();
        this.name = downloadData.getName();
        this.childTaskCount = downloadData.getChildTaskCount();
        this.handler = handler;

        TEMP_FILE_TOTAL_SIZE = EACH_TEMP_SIZE * childTaskCount;
    }

    @Override
    public void run() {
        try {
            File saveFile = new File(path, name);
            File tempFile = new File(path, name + ".temp");
            DownLoadEntity data = DownLoadDao.INSTANCE.queryUrlDownLoad(name);
            if (IOUtils.isFileExists(saveFile) && IOUtils.isFileExists(tempFile) && data != null) {
//                LogUtil.e("有 ====");
                Response response = OkHttpManager.getInstance().initRequest(url, data.getLastModify());
                if (response != null && response.isSuccessful() && IOUtils.isNotServerFileChanged(response)) {
//                    LogUtil.e("服务器修改 ====");
                    TEMP_FILE_TOTAL_SIZE = EACH_TEMP_SIZE * data.getChildTaskCount();
                    onStart(data.getTotalLength(), data.getCurrentLength(), "", true);
                } else {
//                    LogUtil.e("服务器无修改 ====");
                    prepareRangeFile(response);
                }
                saveRangeFile();
            } else {
//                LogUtil.e("无 ====");
                Response response = OkHttpManager.getInstance().initRequest(url);
                if (response != null && response.isSuccessful()) {
                    if (IOUtils.isSupportRange(response)) {
//                        LogUtil.e("1 ====");
                        prepareRangeFile(response);
//                        LogUtil.e("2 ====");
                        saveRangeFile();
//                        LogUtil.e("3 ====");
                    } else {
                        saveCommonFile(response);
                    }
                }
            }
        } catch (IOException e) {
            onError(e.toString());
        }
    }

    private void prepareRangeFile(Response response) {
        FileOutputStream fout = null;
        try {

            File saveFile = IOUtils.createFile(path, name);
            File tempFile = IOUtils.createFile(path, name + ".temp");

            long fileLength = response.body().contentLength();
            onStart(fileLength, 0, IOUtils.getLastModify(response), true);

            DownLoadDao.INSTANCE.delDownLoad(name);
            IOUtils.deleteFile(saveFile, tempFile);

            fout = new FileOutputStream(tempFile);
            String str = "0\r\n" + String.valueOf(fileLength);
            byte[] bytes = str.getBytes();
            fout.write(bytes);
            fout.flush();
        } catch (Exception e) {
            onError(e.toString());
        } finally {
            IOUtils.close(response);
            IOUtils.close(fout);
        }
    }

    /**
     * 开始断点下载
     */
    private void saveRangeFile() {
        final File saveFile = IOUtils.createFile(path, name);
        final File tempFile = IOUtils.createFile(path, name + ".temp");
        final Ranges range = readDownloadRange(tempFile);
        callList = new ArrayList<>();

        Call call = OkHttpManager.getInstance().initRequest1(url, range.start, range.end);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        callList.add(call);
        startSaveRangeFile(response, range, saveFile, tempFile);
    }

    private void saveCommonFile(Response response) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {

            long fileLength = response.body().contentLength();
            onStart(fileLength, 0, "", false);

            IOUtils.deleteFile(path, name);

            File file = IOUtils.createFile(path, name);
            if (file == null) {
                return;
            }

            inputStream = response.body().byteStream();
            outputStream = new FileOutputStream(file);

            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = inputStream.read(buffer)) != -1) {
                if (IS_CANCEL) {
                    handler.sendEmptyMessage(DLStatus.CANCEL);
                    break;
                }

                if (IS_DESTROY) {
                    break;
                }

                outputStream.write(buffer, 0, len);
                onProgress(len);
            }

            outputStream.flush();
        } catch (Exception e) {
            onError(e.toString());
        } finally {
            IOUtils.close(inputStream);
            IOUtils.close(outputStream);
            IOUtils.close(response);
        }
    }

    /**
     * 读取保存的断点信息
     *
     * @param tempFile
     * @return
     */
    public Ranges readDownloadRange(File tempFile) {
        BufferedReader buffreader = null;
        try {
            buffreader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile)));
            long startByteArray = 0;
            long endByteArray = 0;
            String line;
            int lineNum = 0;
            while (( line = buffreader.readLine()) != null) {
                if(lineNum == 0){
                    startByteArray = Long.valueOf(line).longValue();
                } else if(lineNum == 1){
                    endByteArray = Long.valueOf(line).longValue();
                }
                lineNum++;
            }
            return new Ranges(startByteArray, endByteArray);
        } catch (Exception e) {
            onError(e.toString());
        } finally {
            IOUtils.close(buffreader);
        }
        return null;
    }

    /**
     * 分段保存文件
     */
    private void startSaveRangeFile(Response response, Ranges range, File saveFile, File tempFile) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = response.body().byteStream();
            fileOutputStream  = new FileOutputStream(saveFile ,true);
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((len = inputStream.read(buffer)) != -1) {
//                LogUtil.e("读取======" + len);
                if (IS_CANCEL) {
                    handler.sendEmptyMessage(DLStatus.CANCEL);
                    callList.get(0).cancel();
                    break;
                }

                fileOutputStream.write(buffer, 0, len);

                Ranges rang = readDownloadRange(tempFile);
                long star = rang.start + len;
//                LogUtil.e("rang.start = "+rang.start +"  len = "+len);
                String str = star + "\r\n" + rang.end;
//                LogUtil.e("star = "+star +"  end = "+rang.end);
//                if(tempFile.exists()){
//                    tempFile.delete();
//                }
                OutputStream tempOutputStream = new FileOutputStream(tempFile);
                byte[] bytes = str.getBytes();
                tempOutputStream.write(bytes);
                tempOutputStream.flush();
                tempOutputStream.close();

                onProgress(len);

                if (IS_DESTROY) {
                    handler.sendEmptyMessage(DLStatus.DESTROY);
                    callList.get(0).cancel();
                    break;
                }

                if (IS_PAUSE) {
                    handler.sendEmptyMessage(DLStatus.PAUSE);
                    callList.get(0).cancel();
                    break;
                }
            }
            fileOutputStream.flush();
        } catch (Exception e) {
            onError(e.toString());
        } finally {
            IOUtils.close(fileOutputStream);
            IOUtils.close(inputStream);
            IOUtils.close(response);
        }
    }

    public void onProgress(int length) {
        Message message = Message.obtain();
        message.what = DLStatus.PROGRESS;
        message.arg1 = length;
        handler.sendMessage(message);
    }

    public void onError(String msg) {
        Message message = Message.obtain();
        message.what = DLStatus.ERROR;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void onStart(long totalLength, long currentLength, String lastModify, boolean isSupportRange) {
        Message message = Message.obtain();
        message.what = DLStatus.START;
        Bundle bundle = new Bundle();
        bundle.putInt("totalLength", (int) totalLength);
        bundle.putInt("currentLength", (int) currentLength);
        bundle.putString("lastModify", lastModify);
        bundle.putBoolean("isSupportRange", isSupportRange);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void pause() {
        IS_PAUSE = true;
    }

    public void cancel() {
        IS_CANCEL = true;
    }

    public void destroy() {
        IS_DESTROY = true;
    }

}
