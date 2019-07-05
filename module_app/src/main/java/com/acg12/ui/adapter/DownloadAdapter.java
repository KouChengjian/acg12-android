package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.po.DownLoadEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.LogUtil;
import com.acg12.utils.IOUtils;
import com.acg12.utils.download.task.DLStatus;
import com.acg12.utils.download.task.DownLoadCallback;
import com.acg12.utils.download.task.DownloadManger;

import java.io.File;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-07-04 18:34
 * Description:
 */
public class DownloadAdapter extends CommonRecyclerAdapter<DownLoadEntity> {

    public DownloadAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new ViewHolder(getItemView(R.layout.item_download, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(mContext, getList().get(position));
    }

    public class ViewHolder extends CommonRecyclerViewHolder {

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

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            download_size = itemView.findViewById(R.id.download_size);
            percentage = itemView.findViewById(R.id.percentage);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            start = itemView.findViewById(R.id.start);
            pause = itemView.findViewById(R.id.pause);
            resume = itemView.findViewById(R.id.resume);
            cancel = itemView.findViewById(R.id.cancel);
            restart = itemView.findViewById(R.id.restart);
            tv_download_state = itemView.findViewById(R.id.tv_download_state);
        }

        public void bindData(Context context, DownLoadEntity data) {
            name.setText(data.getName());
            download_size.setText(IOUtils.formatSize(data.getCurrentLength()) + "/" + IOUtils.formatSize(data.getTotalLength()));
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
                    DownLoadEntity dl = DownloadManger.getInstance(mContext).getDbData(data.getName());
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

        private void setListener(final Context mContext, final DownLoadEntity downloadData) {

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
}
