package org.acg12.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acg12.common.conf.DLStatus;
import com.acg12.common.entity.DownLoad;
import com.acg12.common.listener.DownLoadCallback;
import com.acg12.common.net.download.DownloadManger;
import com.acg12.kk.ui.base.KKBaseRecyclerView;
import com.acg12.kk.utils.IOUtils;
import com.acg12.kk.utils.LogUtil;

import org.acg12.R;

import java.io.File;

/**
 * Created by Administrator on 2017/6/14.
 */
public class DownloadViewHolder extends KKBaseRecyclerView {

    private TextView name;
    private TextView download_size;
    private TextView percentage;
    private ProgressBar progress_bar;
    private TextView start;
    private TextView pause;
    private TextView resume;
    private TextView cancel;
    private TextView restart;

    private TextView tv_download_state;

    public DownloadViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initViews(View itemView) {
        name = (TextView) itemView.findViewById(R.id.name);
        download_size = (TextView) itemView.findViewById(R.id.download_size);
        percentage = (TextView) itemView.findViewById(R.id.percentage);
        progress_bar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        start = (TextView) itemView.findViewById(R.id.start);
        pause = (TextView) itemView.findViewById(R.id.pause);
        resume = (TextView) itemView.findViewById(R.id.resume);
        cancel = (TextView) itemView.findViewById(R.id.cancel);
        restart = (TextView) itemView.findViewById(R.id.restart);
        tv_download_state = (TextView) itemView.findViewById(R.id.tv_download_state);
    }

    public void bindData(final Context mContext, final DownLoad data) {
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
                LogUtil.e(dl.getState() + "====");
                data.setState(dl.getState());
                if (data.getState() == DLStatus.PAUSE) {
                    tv_download_state.setText("暂停");
                    DownloadManger.getInstance(mContext).resume(data.getName());
                } else if (data.getState() == DLStatus.START || data.getState() == DLStatus.NONE) {
                    tv_download_state.setText("开始");
                    DownloadManger.getInstance(mContext).pause(data.getName());
                }
            }
        });

        setListener(mContext, data);


        if (data.getState() == DLStatus.PAUSE) {
            tv_download_state.setText("开始");
            tv_download_state.setVisibility(View.VISIBLE);
        } else if (data.getState() == DLStatus.FINISH) {
            tv_download_state.setVisibility(View.INVISIBLE);
        } else {
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
                name.setText(downloadData.getName() + ":下载中");
                download_size.setText(IOUtils.formatSize(currentSize) + "/" + IOUtils.formatSize(totalSize));
                percentage.setText(progress + "%");
                progress_bar.setProgress((int) progress);
            }

            @Override
            public void onPause() {
                name.setText(downloadData.getName() + ":已暂停");
            }

            @Override
            public void onCancel() {
                name.setText(downloadData.getName() + ":已取消");
            }

            @Override
            public void onFinish(File file) {
                name.setText(downloadData.getName() + ":已完成");
                tv_download_state.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onWait() {
                name.setText(downloadData.getName() + ":等待中");
            }

            @Override
            public void onError(String error) {
                name.setText(downloadData.getName() + ":出错");
            }
        });


    }
}
