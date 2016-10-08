package com.kcj.animationfriend.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.view.photo.zoom.PhotoView;
import com.kcj.animationfriend.view.photo.zoom.ViewPagerFixed;
import com.liteutil.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: AlbumPvwOptActivity
 * @Description: 图片预览详细
 * @author: KCJ
 * @date:
 */
public class AlbumPvwOptActivity extends BaseSwipeBackActivity implements Toolbar.OnMenuItemClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.vp_image_perview)
	protected ViewPagerFixed pager;
	private MyPageAdapter adapter = null;
	protected Album album = null;
	protected int position = 0;
	private ArrayList<View> listViews = new ArrayList<View>();
	protected String chatUrl ;
//	private LiteHttp client;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		setTitle(R.string.home_album);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		album = (Album) getIntent().getSerializableExtra("data");
		position = getIntent().getExtras().getInt("position");
		chatUrl = getIntent().getExtras().getString("url");
		initViews();
		initDatas();
		initEvents();
	}

	public void initViews() {
//		client = HttpProxy.getInstance();
	}

	public void initDatas() {
		if(album != null){
			ArrayList<BmobFile> proFileList = album.getProFileList();
			if (proFileList != null) {
				for (BmobFile bmobFile : proFileList) {
					View imageLayout = LayoutInflater.from(this).inflate(R.layout.item_pager_image, null, false);
					final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.page_loading);
					final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.page_image);
					imageView.setBackgroundColor(0xffffffff);
					ImageLoader.getInstance().displayImage(bmobFile.getFileUrl(mContext),imageView,MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri,View view) {
							spinner.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri,View view, FailReason failReason) {
							spinner.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,	View view, Bitmap loadedImage) {
							spinner.setVisibility(View.GONE);
							imageView.setVisibility(View.VISIBLE);
						}
					});
				    listViews.add(imageLayout);
				}
			}else{
				ArrayList<String> urlList = album.getUrlList();
				if(urlList != null){
					for(String url:urlList){
						View imageLayout = LayoutInflater.from(this).inflate(R.layout.item_pager_image, null, false);
						final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.page_loading);
						final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.page_image);
						imageView.setBackgroundColor(0xffffffff);
						ImageLoader.getInstance().displayImage(url,imageView,MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener() {
							@Override
							public void onLoadingStarted(String imageUri,View view) {
								spinner.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingFailed(String imageUri,View view, FailReason failReason) {
								spinner.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingComplete(String imageUri,	View view, Bitmap loadedImage) {
								spinner.setVisibility(View.GONE);
								imageView.setVisibility(View.VISIBLE);
							}
						});
					    listViews.add(imageLayout);
					}
				}
			}
		}else {
			if(chatUrl != null){ // 聊天图片
				View imageLayout = LayoutInflater.from(this).inflate(R.layout.item_pager_image, null, false);
				final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.page_loading);
				final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.page_image);
				imageView.setBackgroundColor(0xffffffff);
				ImageLoader.getInstance().displayImage(chatUrl,imageView,MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri,View view) {
						spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri,View view, FailReason failReason) {
						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri,	View view, Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
						imageView.setVisibility(View.VISIBLE);
					}
				});
			    listViews.add(imageLayout);
			}
		}
	}

	public void initEvents() {
		toolbar.setOnMenuItemClickListener(this);
		// pager.setScanScroll(false);
		pager.addOnPageChangeListener(pageChangeListener);
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int) getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
		pager.setCurrentItem(position);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_save, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
	      case R.id.menu_album_save:
	    	  Log.e("TAG", "下载---------");
	    	  downLoadPic();
	        break;
	    }
		return true;
	}
	
	public void downLoadPic(){
		Runnable mRunnable = new Runnable(){
			@Override
			public void run() {
				if(album != null){
					if(album.getProFileList() != null && album.getProFileList().isEmpty()){
						makeLoadBitmapRequest(album.getProFileList().get(position).getFileUrl(mContext));
					}else{
						makeLoadBitmapRequest(album.getUrlList().get(0));
					}
				}else{
					if(chatUrl != null){
						makeLoadBitmapRequest(chatUrl);
					}
				}
			}
		};
		new Thread(mRunnable).start();
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		public void onPageSelected(int positions) {
			position = positions;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		public void onPageScrollStateChanged(int position) {}
	};

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return listViews.size();
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public Object instantiateItem(View view, int position) {
			try {
				((ViewPagerFixed) view).addView(listViews.get(position % size),
						0);

			} catch (Exception e) {
			}
			return listViews.get(position % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	/**
	 * 保存文件
	 */
	public void saveFile(Bitmap bm, String fileName) throws IOException {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String path = getSDPath() + "/FriendAnimal/";
			Log.e("TAG", path);
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			File myCaptureFile = new File(path + fileName + ".jpg");
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
			ShowToast(path + fileName + ".jpg");
		} else {
			ShowToast("无SD卡");
		}
	}

	

	/**
	 * 下载
	 */
	private void makeLoadBitmapRequest(String url) {
//		client.executeAsync( new BitmapRequest(url).setHttpListener(new HttpListener<Bitmap>(true, true, false) {
//            @Override
//            public void onLoading(AbstractRequest<Bitmap> request, long total, long len) {
//            }
//
//            @Override
//            public void onSuccess(Bitmap bitmap, Response<Bitmap> response) {
//            	saveToSdCard(bitmap, "jpg");
//            }
//
//            @Override
//            public void onFailure(HttpException e, Response<Bitmap> response) {
//            	ShowToast("加载图片失败");
//            }
//        }));
	}

	/**
	 * 缓存到内存中
	 */
	public void saveToSdCard(Bitmap bitmap, String suffix) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String dateTime = new Date(System.currentTimeMillis()).getTime()+ "";// 获取当前时间
			String files = getSDPath() + "/FriendAnimal/image/";
			File file = new File(files);
			if(!file.exists()){
				file.mkdirs();
			}
			try {
				FileOutputStream out = new FileOutputStream(file+ "/"+dateTime + "." + suffix);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ShowToast("保存成功"+file.getAbsolutePath());
		}else{
			ShowToast("无SD卡");
		}
	}
	
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}
}
