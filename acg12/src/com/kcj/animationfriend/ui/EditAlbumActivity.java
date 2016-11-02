package com.kcj.animationfriend.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Collect;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.util.CacheUtils;
import com.kcj.animationfriend.view.LoadingDialog;
import com.kcj.animationfriend.view.photo.AlbumActivity;
import com.kcj.animationfriend.view.photo.Bimp;
import com.kcj.animationfriend.view.photo.FileUtils;
import com.kcj.animationfriend.view.photo.GalleryActivity;
import com.kcj.animationfriend.view.photo.ImageItem;
import com.kcj.animationfriend.view.photo.PublicWay;
import com.kcj.animationfriend.view.photo.Res;

/**
 * @ClassName: EditAlbumActivity
 * @Description: 发图集
 * @author: KCJ
 * @date: 2014-11-15
 */
public class EditAlbumActivity extends BaseSwipeBackActivity implements OnClickListener ,OnItemClickListener ,Toolbar.OnMenuItemClickListener{
	
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.rl_choose_palette)
	protected RelativeLayout choosePalette;
	@InjectView(R.id.tv_choose_palette)
	protected TextView tvChoosePalette;
	@InjectView(R.id.ev_edit_posts_content)
	protected EditText content;
	@InjectView(R.id.img_edit_selectoropen)
	protected ImageView albumPic;
	@InjectView(R.id.img_edit_selectortake)
	protected ImageView takePic;
	@InjectView(R.id.ll_edit_selectoropen)
	protected LinearLayout openLayout;
	@InjectView(R.id.ll_edit_selectortake)
	protected LinearLayout takeLayout;
	@InjectView(R.id.rl_edit_selector)
	protected RelativeLayout rlSelector;
	@InjectView(R.id.gv_edit_pictrue)
	protected GridView pictureGridView;
	
	private Palette palette = null;
	private GridAdapter pictureAdapter = null;
	private ArrayList<String> urlList = new ArrayList<>();
	private ArrayList<BmobFile> bmobFileList = new ArrayList<>();
	public static Bitmap bimap ;
	protected String dateTime;  // 获取当前时间
	protected String commitContent;
	protected int allCount = 0; // 选择的图片数
	protected int currentCount = 0; // 当前图标
	protected LoadingDialog dialog;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE|
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); 
		setContentView(R.layout.activity_editalbum);
		setTitle(R.string.user_add_album);
		setSupportActionBar(toolbar);
		initViews();
		initEvents();
	}
	
	public void initViews(){
		// 初始化进度
		dialog = new LoadingDialog(this);
		// 初始化画廊
		Res.init(this);
		PublicWay.activityList.add(this);
		bimap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_addpic_unfocused);
		pictureGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		pictureAdapter = new GridAdapter(this);
		pictureAdapter.update();
		pictureGridView.setAdapter(pictureAdapter);
	}
	
	public void initEvents(){
		pictureGridView.setOnItemClickListener(this);
		choosePalette.setOnClickListener(this);
		openLayout.setOnClickListener(this);
		takeLayout.setOnClickListener(this);
		albumPic.setOnClickListener(this);
		takePic.setOnClickListener(this);
		toolbar.setOnMenuItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Bimp.tempSelectProgress.clear();
		if(pictureAdapter == null){
			pictureAdapter = new GridAdapter(this);
			pictureAdapter.update();
		}else{
			pictureAdapter.update();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_save, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Bimp.tempSelectBitmap.clear();
		Bimp.max = 0;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case Constant.REQUEST_CODE_CAMERA:
				String files = CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime;
				File file = new File(files);
				if(file.exists()){
					Bitmap bitmap = compressImageFromFile(files);
					ImageItem takePhoto = new ImageItem();
					takePhoto.setBitmap(bitmap);
					Bimp.tempSelectBitmap.add(takePhoto);
					pictureAdapter.update();
				}
				break;
			case Constant.TAKE_PICTURE:
				if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
					String fileName = String.valueOf(System.currentTimeMillis());
					Bitmap bm = (Bitmap) data.getExtras().get("data");
					FileUtils.saveBitmap(bm, fileName);
					ImageItem takePhoto = new ImageItem();
					takePhoto.setBitmap(bm);
					Bimp.tempSelectBitmap.add(takePhoto);
				}
				break;
			case Constant.REQUEST_CODE_PALETTE:
				palette=(Palette)data.getSerializableExtra("data");
				tvChoosePalette.setText(palette.getName());
				break;
			}
		}
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_album_save:
			hideSoftInputView();
			commitContent = content.getText().toString().trim();
			allCount = Bimp.tempSelectBitmap.size();
			if(palette == null){
				ShowToast("画板不能为空~");
				return true;
			}
			if(allCount == 0){
				ShowToast("请选择图片~");
				return true;
			}
			if(currentCount < allCount){
				sendAlbum(commitContent );
			}
			break;
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_edit_selectoropen:
			Intent intent = new Intent(EditAlbumActivity.this,AlbumActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_edit_selectortake:
			photo(); // 拍照
			break;
		case R.id.rl_choose_palette:
			startActivityForResult(new Intent(this,UserPltMagActivity.class), Constant.REQUEST_CODE_PALETTE);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == Bimp.tempSelectBitmap.size()) {
			Intent intent = new Intent(EditAlbumActivity.this,AlbumActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(EditAlbumActivity.this,GalleryActivity.class);
			intent.putExtra("position", "1");
			intent.putExtra("ID", arg2);
			startActivity(intent);
		}
	}
	
	/**
	 *  获取拍照图片
	 */
	public void photo() {
		File f = new File(CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(f);
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(camera, Constant.REQUEST_CODE_CAMERA);
	}

	/**
	 *  发帖子
	 */
    public void sendAlbum(String content){
    	dialog.show(); // 显示对话框
        String targetUrl = "";
        String suffixs = "png";
        int width = 0 ;
        int height = 0;
        dateTime = new Date(System.currentTimeMillis()).getTime() + "";// 获取当前时间
        ArrayList<ImageItem> imgList = Bimp.tempSelectBitmap;
        for(int i = 0 ; i< imgList.size() ; i++  ){
        	dateTime = dateTime + i+"";
        	ImageItem imageItem = imgList.get(i);
        	Bitmap bitmap = compressImageFromFile(imageItem.getImagePath());
        	if(i == 0){
        		width = mScreenWidth/2-30;
            	height = width*bitmap.getHeight()/bitmap.getWidth();
        	}
        	// 获取后缀名
        	String[] str = imageItem.getImagePath().split("[.]"); 
    		List list = Arrays.asList(str);
        	for (int j = 0 ; j< list.size() ; j++) { 
                if(j == list.size()-1){
                	suffixs = (String)list.get(j);
                }
            } 
        	targetUrl = saveToSdCard(bitmap ,suffixs);
        	urlList.add(targetUrl);
        }
        uploadFile(content ,currentCount ,width ,height ,suffixs);
    }
    
    /**
     *  从文件中压缩图片
     */
    private Bitmap compressImageFromFile(String srcPath) {
  		BitmapFactory.Options newOpts = new BitmapFactory.Options();
  		newOpts.inJustDecodeBounds = true;//只读边,不读内容
  		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
  		newOpts.inJustDecodeBounds = false;
  		int w = newOpts.outWidth;
  		int h = newOpts.outHeight;
  		float hh = 800f;//
  		float ww = 480f;//
  		int be = 1;
  		// 横图
  		if (w > h && w > ww) {
  			be = (int) (newOpts.outWidth / ww);
  		} 
  		// 竖图
  		else if (w < h && h > hh) {
  			be = (int) (newOpts.outHeight / hh);
  		}
  		if (be <= 0){
  			be = 1;
  		}
  		newOpts.inSampleSize = be;//设置采样率
  		newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
  		newOpts.inPurgeable = true;// 同时设置才会有效
  		newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
  		
  		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//  	return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩 ,其实是无效的,大家尽管尝试
  		return bitmap;
  	}
      
    /**
     * 缓存到内存中
     */
    public String saveToSdCard(Bitmap bitmap ,String suffix){
  		String files =CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime+"."+suffix;
  		File file=new File(files);
          try {
              FileOutputStream out=new FileOutputStream(file);
              if(bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)){
                  out.flush();
                  out.close();
              }
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return file.getAbsolutePath();
  	}  
    
    /**
     * 上传图片
     */
    private void uploadFile(final String content ,int num ,final int width ,final int height,final String suffixs){
    	String url = urlList.get(num);
    	final BmobFile bmobFile = new BmobFile(new File(url));
    	bmobFile.uploadblock(this, new UploadFileListener() {

    		@Override
			public void onSuccess() {
    			ShowToast("上传文件成功");
    			bmobFileList.add(bmobFile);
    			++currentCount;
				if(currentCount == allCount){
					uploadOther(content , bmobFileList ,width ,height ,suffixs);
				}else{
					uploadFile(content ,currentCount ,width ,height ,suffixs);
				}
			}
    		
			@Override
			public void onFailure(int arg0, String msg) {
				Log.i(TAG, msg);
				ShowToast("上传文件失败"+msg);
			}
    		
    	});
    }
    
    /**
     *  保存信息记录
     */
	private void uploadOther(final String content,final ArrayList<BmobFile> figureFile ,int width ,int height,final String suffixs) {
		final User user = HttpProxy.getCurrentUser(mContext);
		final Collect collect = new Collect();
		collect.setUser(user);
		collect.setPalette(palette);
		collect.setContent(content);
		collect.setType(0);
		final Album album = new Album();
		album.setUser(user);
		album.setPalette(palette);
		album.setContent(content);
		album.setResType(suffixs);
		album.setResWidth(width);
		album.setResHight(height);
		album.setLove(0);
		album.setFavorites(0);
		album.setProFileList(bmobFileList);
		album.setUrlList(null);
		album.save(this, new SaveListener() {
			@Override
			public void onSuccess() {
				collect.setAlbum(album);
				collect.save(mContext, new SaveListener() {

					@Override
					public void onSuccess() {
						BmobRelation relation = new BmobRelation();
						relation.add(collect);
						palette.setCollectBR(relation);
						palette.update(mContext, new UpdateListener() {
							
							@Override
							public void onSuccess() {
								ShowToast("发表成功！");
								dialog.dismiss();
								Bimp.tempSelectBitmap.clear();
								Bimp.max = 0;
								dialog.dismiss();
								finish();	
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								dialog.dismiss();
								ShowToast("关联失败！"+arg1);
								collect.delete(mContext);
							}
						});
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						dialog.dismiss();
						Log.e("onFailure", arg1);
						ShowToast("创建失败~"+arg1);
						album.delete(mContext);
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				dialog.dismiss();
				Log.e("onFailure", arg1);
				ShowToast("创建失败~"+arg1);
			}
		});
	}
    
	/**
	 * 画廊的适配类
	 */
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				holder.progress = (TextView) convertView.findViewById(R.id.tv_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}
			Bimp.tempSelectProgress.add(holder.progress);
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
			public TextView progress;
		}

		@SuppressLint("HandlerLeak")
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					pictureAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
}
