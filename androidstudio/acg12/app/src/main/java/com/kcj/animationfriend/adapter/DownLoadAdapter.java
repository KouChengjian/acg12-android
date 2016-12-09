package com.kcj.animationfriend.adapter;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.kcj.animationfriend.DownloadService;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.DownloadViewHolder;
import com.kcj.animationfriend.bean.Download;
import com.kcj.animationfriend.config.DownloadManager;
import com.kcj.animationfriend.config.DownloadState;
import com.liteutil.exception.DbException;
import com.liteutil.http.listener.Callback.CancelledException;
import com.liteutil.util.Log;

/** 
 * @ClassName: DownLoadAdapter
 * @Description: 下载列表
 * @author: KCJ
 * @date: 2015-11-25 下午3:03:40
 */
public class DownLoadAdapter extends BaseAdapter implements SectionIndexer{

	private Context ct;
	private List<Download> data;
	private static DownloadManager downloadManager;
	
	public DownLoadAdapter(Context ct, List<Download> datas) {
		this.ct = ct;
		this.data = datas;
		downloadManager = DownloadService.getDownloadManager();
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Download download = data.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_video_download, parent,false);
			viewHolder = new ViewHolder(convertView, download);
			convertView.setTag(viewHolder);
			viewHolder.refresh();
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.update(download);
		}
		if (download.getState().value() < DownloadState.FINISHED.value()) {
            try {
                downloadManager.startDownload(
                		download.getDownLoadUrl(),
                		download.getFileName(),
                		download.getFileSavePath(),
                		download.isAutoResume(),
                		download.isAutoRename(),
                		viewHolder);
            } catch (DbException ex) {
                Toast.makeText(MyApplication.getInstance(), "添加下载失败", Toast.LENGTH_LONG).show();
            }
        }
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			viewHolder.alpha.setVisibility(View.VISIBLE);
			viewHolder.alpha.setText(download.getSortLetters());
		} else {
			viewHolder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	static class ViewHolder extends DownloadViewHolder implements OnClickListener{
		
		TextView alpha;// 首字母提示
		TextView download;
		TextView name ,state ,progress;
		
		public ViewHolder(View view, Download downloadInfo) {
			super(view, downloadInfo);
			alpha = (TextView) view.findViewById(R.id.alpha);
			name = (TextView) view.findViewById(R.id.tv_download_name);
			progress = (TextView) view.findViewById(R.id.tv_download_plan);
			state = (TextView) view.findViewById(R.id.tv_download_state);
			download = (TextView) view.findViewById(R.id.tv_dl);
			download.setOnClickListener(this);
			refresh();
		}
		
		@Override
	    public void update(Download downloadInfo) {
	        super.update(downloadInfo);
	        refresh();
	    }

		@Override
		public void onWaiting() {
			refresh();
		}

		@Override
		public void onStarted() {
			refresh();
		}

		@Override
		public void onLoading(long total, long current) {
			refresh();
		}

		@Override
		public void onSuccess(File result) {
			refresh();
		}

		@Override
		public void onError(Throwable ex, boolean isOnCallback) {
			refresh();
		}

		@Override
		public void onCancelled(CancelledException cex) {
			refresh();
		}
		
		public void refresh() {
			name.setText(downloadInfo.getFileName());
            state.setText(downloadInfo.getState().toString());
            progress.setText(downloadInfo.getFileProgress()+"");
            download.setVisibility(View.VISIBLE);
            download.setText("停止");
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                	download.setText("停止");
                    break;
                case ERROR:
                case STOPPED:
                	download.setText("开始");
                    break;
                case FINISHED:
                	download.setVisibility(View.INVISIBLE);
                    break;
                default:
                	download.setText("开始");
                    break;
            }
        }

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_dl:
				toggleEvent();
				break;
			default:
				break;
			}
		}
		
		private void toggleEvent() {
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case ERROR:
                case STOPPED:
                    try {
                        downloadManager.startDownload(
                                downloadInfo.getDownLoadUrl(),
                                downloadInfo.getFileName(),
                                downloadInfo.getFileSavePath(),
                                downloadInfo.isAutoResume(),
                                downloadInfo.isAutoRename(),
                                this);
                    } catch (DbException ex) {
                        Toast.makeText(MyApplication.getInstance(), "添加下载失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case FINISHED:
                    Toast.makeText(MyApplication.getInstance(), "已经下载完成", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = data.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section){
				return i;
			}
		}
		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return data.get(position).getSortLetters().charAt(0);
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}
