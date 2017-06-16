package org.acg12.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.net.download.DownLoad;
import org.acg12.net.download.DownLoadCallback;
import org.acg12.net.download.DownloadManger;
import org.acg12.utlis.IOUtils;
import org.acg12.utlis.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/6/14.
 */
public class DownloadViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    TextView download_size;
    TextView percentage;
    ProgressBar progress_bar;
    TextView start;
    TextView pause;
    TextView resume;
    TextView cancel;
    TextView restart;

    TextView tv_download_state;


    public DownloadViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        download_size = (TextView) itemView.findViewById(R.id.download_size);
        percentage = (TextView) itemView.findViewById(R.id.percentage);
        progress_bar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        start = (TextView) itemView.findViewById(R.id.start);
        pause = (TextView) itemView.findViewById(R.id.pause);
        resume = (TextView) itemView.findViewById(R.id.resume);
        cancel = (TextView) itemView.findViewById(R.id.cancel);
        restart = (TextView) itemView.findViewById(R.id.restart);
        tv_download_state  = (TextView) itemView.findViewById(R.id.tv_download_state);
    }

    public void bindData(final Context mContext , final DownLoad data){
        name.setText(data.getName());
        download_size.setText(IOUtils.formatSize(data.getCurrentLength()) + "/"
                + IOUtils.formatSize(data.getTotalLength()));
        percentage.setText(data.getPercentage() + "%");
        progress_bar.setProgress((int) data.getPercentage());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).start(data.getName());
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).pause(data.getName());
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).resume(data.getName());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).cancel(data.getName());
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).restart(data.getName());
            }
        });
        tv_download_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoad dl = DownloadManger.getInstance(mContext).getDbData(data.getName());
                LogUtil.e(dl.getState() +"====");
                data.setState(dl.getState());
                if(data.getState() == Constant.PAUSE){
                    tv_download_state.setText("暂停");
                    DownloadManger.getInstance(mContext).resume(data.getName());
                } else if(data.getState() == Constant.START || data.getState() == Constant.NONE){
                    tv_download_state.setText("开始");
                    DownloadManger.getInstance(mContext).pause(data.getName());
                }
            }
        });

        setListener(mContext , data);


        if(data.getState() == Constant.PAUSE){
            tv_download_state.setText("开始");
            tv_download_state.setVisibility(View.VISIBLE);
        } else if(data.getState() == Constant.FINISH){
            tv_download_state.setVisibility(View.INVISIBLE);
        } else{
            tv_download_state.setText("暂停");
            tv_download_state.setVisibility(View.VISIBLE);
        }
    }


    private void setListener(final Context mContext, final DownLoad downloadData) {

        DownloadManger.getInstance(mContext).setOnDownloadCallback(downloadData, new DownLoadCallback() {
            @Override
            public void onStart(long currentSize, long totalSize, float progress) {
                name.setText(downloadData.getName() + ":准备中");
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress) {
//                LogUtil.e("progress = "+progress);
                name.setText( downloadData.getName() + ":下载中");
                download_size.setText(IOUtils.formatSize(currentSize) + "/" + IOUtils.formatSize(totalSize));
                percentage.setText( progress + "%");
                progress_bar.setProgress((int) progress);
            }

            @Override
            public void onPause() {
                name.setText( downloadData.getName() + ":已暂停");
            }

            @Override
            public void onCancel() {
                name.setText( downloadData.getName() + ":已取消");
            }

            @Override
            public void onFinish(File file) {
                name.setText( downloadData.getName() + ":已完成");
                tv_download_state.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onWait() {
                name.setText( downloadData.getName() + ":等待中");
            }

            @Override
            public void onError(String error) {
                name.setText( downloadData.getName() + ":出错");
            }
        });


    }
}
