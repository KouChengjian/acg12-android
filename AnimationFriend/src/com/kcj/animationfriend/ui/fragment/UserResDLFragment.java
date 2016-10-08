package com.kcj.animationfriend.ui.fragment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;






import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;

import com.kcj.animationfriend.DownloadService;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.DownLoadAdapter;
import com.kcj.animationfriend.bean.Download;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.DownloadManager;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.CharacterParser;
import com.kcj.animationfriend.util.PinyinComparatorDl;
import com.kcj.animationfriend.util.StringUtils;
import com.kcj.animationfriend.view.AlertDialogView;
import com.liteutil.async.AsyncTask;
import com.liteutil.exception.DbException;
import com.liteutil.util.Log;

/**
 * @ClassName: UserResDownloadFragment
 * @Description: 用户资源--下载
 * @author: KCJ
 * @date: 
 */
public class UserResDLFragment extends BaseFragment implements OnItemClickListener , OnItemLongClickListener{
	
	@InjectView(R.id.dialog)
	protected TextView dialog;
	@InjectView(R.id.list_friends)
	protected ListView downloadListView;
	protected DownLoadAdapter downLoadAdapter;
	protected List<Download> downLoadList = new ArrayList<Download>();
	private CharacterParser characterParser; // 汉字转换成拼音的类
	private PinyinComparatorDl pinyinComparator; // 根据拼音来排列ListView里面的数据类
	private boolean hidden;
	protected DownLoadAsyncTask downLoadAsyncTask;
	private DownloadManager downloadManager;
	
	public static BaseFragment newInstance(User user) {
    	BaseFragment fragment = new UserResDLFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("user", user);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_res_download, container, false);
		setContentView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		downloadManager = DownloadService.getDownloadManager();
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparatorDl();
		downLoadAdapter = new DownLoadAdapter(getActivity(), downLoadList);
		downloadListView.setAdapter(downLoadAdapter);
	}
	
	@Override
	public void initEvent() {
		downloadListView.setOnItemClickListener(this);
		downloadListView.setOnItemLongClickListener(this);
	}
	
	@Override
	public void initDatas() {
        if(downLoadAsyncTask != null)
        	downLoadAsyncTask.cancel(true);
        downLoadAsyncTask = new DownLoadAsyncTask();
        downLoadAsyncTask.execute("");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
		}
	}
	
	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryMyDownload();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Exception",e.toString()+"===");
		}
	}
	
	private void queryMyDownload() {
		ArrayList<Download> query = UserProxy.getLiteOrmInstance().query(Download.class);
		filledData(query);
		if (downLoadAdapter == null) {
			downLoadAdapter = new DownLoadAdapter(getActivity(), downLoadList);
			downloadListView.setAdapter(downLoadAdapter);
		} else {
			downLoadAdapter.notifyDataSetChanged();
		}
	}
	
	@SuppressLint("DefaultLocale")
	private void filledData(List<Download> datas) {
		downLoadList.clear();
		int total = datas.size();
		for (int i = 0; i < total; i++) {
			Download load = datas.get(i);
			load.setFileName(StringUtils.substringBetween(load.getFileName()));
//			Download sortDownload = new Download();
//			sortDownload.setId(load.getId());
//			sortDownload.setState(load.getState());
//			sortDownload.setFileName(StringUtils.substringBetween(load.getFileName()));
//			sortDownload.setDownLoadUrl(load.getDownLoadUrl());
//			sortDownload.setAutoRename(load.isAutoRename());
//			sortDownload.setAutoResume(load.isAutoResume());
//			sortDownload.setFileLength(load.getFileLength());
//			sortDownload.setFileProgress(load.getFileProgress());
//			sortDownload.setFileSavePath(load.getFileSavePath());
			String fileName = load.getFileName();
			if (fileName != null) {
				String pinyin = characterParser.getSelling(load.getFileName());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					load.setSortLetters(sortString.toUpperCase());
				} else {
					load.setSortLetters("#");
				}	
			} else {
				load.setSortLetters("#");
			}
			downLoadList.add(load);
		}
		// 根据a-z进行排序
		Collections.sort(downLoadList, pinyinComparator);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position,long arg3) {
		final AlertDialogView alertDialog = new AlertDialogView(getActivity(),"");
		alertDialog.setContent1("删除", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				alertDialog.cancel();
				Download load = downLoadList.get(position);
				try {
					downloadManager.removeDownload(load);
				} catch (DbException e) {
					e.printStackTrace();
				}
				UserProxy.getLiteOrmInstance().delete(load);
				downLoadList.remove(load);
				downLoadAdapter.notifyDataSetChanged();
			}
		});
		return false;
	}
	
	public class DownLoadAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected void onCancelled() {}

		@Override
		protected String doInBackground(String... params) {
			
//			HttpProxy.getInstance().executeAsync(new FileRequest("http://ws.acgvideo.com/7/4f/5170049-1.mp4?wsTime=1448370347&wsSecret2=96fad7de835a809c06b29e56a5ddaac1&oi=2067913073&internal=1", "sdcard/1111.mp4").setHttpListener(new HttpListener<File>(true, true, false){
//			    @Override
//			    public void onLoading(AbstractRequest<File> request,long total, long len) {
//				    super.onLoading(request, total, len);
//			    }
//		    }));
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
		
	}

}
