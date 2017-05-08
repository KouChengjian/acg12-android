package com.kcj.animationfriend.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.util.CacheUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * @ClassName: UserInfoActivity
 * @Description: 用户信息
 * @author: KCJ
 * @date: 
 */
public class UserInfoActivity extends BaseSwipeBackActivity implements OnClickListener ,OnCheckedChangeListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.user_icon)
	protected RelativeLayout iconLayout; // logo
	@InjectView(R.id.user_icon_image)
	protected ImageView userIcon;
	@InjectView(R.id.user_nick)
	protected RelativeLayout nickLayout; // 昵称
	@InjectView(R.id.user_nick_text)
	protected TextView nickName;
	@InjectView(R.id.user_account)
	protected RelativeLayout accountLayout; // 昵称
	@InjectView(R.id.user_account_text)
	protected TextView accountUser;
	@InjectView(R.id.sex_choice_switch)
	protected CheckBox sexSwitch; // 性别
	@InjectView(R.id.user_sign)
	protected RelativeLayout signLayout;// 签名
	@InjectView(R.id.user_sign_text)
	protected TextView signature;
	
	private String iconUrl;
	private String dateTime;
	private AlertDialog albumDialog;
	private static final int UPDATE_ICON = 12;
	private static final int UPDATE_SEX = 11;
	private static final int EDIT_SIGN = 15;
	private static final int UPDATE_SIGN = 14;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		initViews();
		initEvents();
		initDatas();
	}
	
	public void initViews(){
		setTitle(R.string.user_info);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void initEvents(){
		iconLayout.setOnClickListener(this);
		nickLayout.setOnClickListener(this);
		signLayout.setOnClickListener(this);
		sexSwitch.setOnCheckedChangeListener(this);
		
//		accountLayout.setOnClickListener(this);
	}
	
	public void initDatas(){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null){
			if(TextUtils.isEmpty(user.getNick())){
				nickName.setText(user.getUsername());
			}else{
				nickName.setText(user.getNick());
			}
			accountUser.setText(user.getUsername());
			signature.setText(user.getSignature());
			if(user.getSex().equals(Constant.SEX_FEMALE)){
				sexSwitch.setChecked(true);
			}else{
				sexSwitch.setChecked(false);
			}
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance().displayImage(avatarFile, userIcon, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
			}
		}else{
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case UPDATE_ICON:
				initDatas();
				break;
			case UPDATE_SIGN:
				initDatas();
				break;
			case EDIT_SIGN:
				initDatas();
				break;
			case 1:
				String files =CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime;
				File file = new File(files);
				if(file.exists()&&file.length() > 0){
					Uri uri = Uri.fromFile(file);
					startPhotoZoom(uri);
				}
				break;
			case 2:
				if (data == null) {
					return;
				}
				startPhotoZoom(data.getData());
				break;
			case 3:
				if (data != null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bitmap = extras.getParcelable("data");
						// 锟斤拷锟斤拷图片
						iconUrl = saveToSdCard(bitmap);
						userIcon.setImageBitmap(bitmap);
						Log.e("iconUrl",iconUrl+"====");
						updateIcon(iconUrl);
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.sex_choice_switch:
			if(isChecked){
				updateSex(0);
			}else{
				updateSex(1);
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_icon: // logo
			if(isLogined()){
				showAlbumDialog();
			}else{
				redictToLogin(UPDATE_ICON);
			}
			break;
		case R.id.user_nick: // 昵称
			if(isLogined()){
				Intent intent = new Intent();
				intent.setClass(mContext, EditNickActivity.class);
				startActivityForResult(intent, EDIT_SIGN);
			}else{
				redictToLogin(UPDATE_SIGN);
			}
			break;
		case R.id.user_sign: // 签名
			if(isLogined()){
				Intent intent = new Intent();
				intent.setClass(mContext, EditSignActivity.class);
				startActivityForResult(intent, EDIT_SIGN);
			}else{
				redictToLogin(UPDATE_SIGN);
			}
			break;
		}
	}
	
	/**
	 * 判断用户是否登录
	 * @return
	 */
	private boolean isLogined(){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null){
			return true;
		}
		return false;
	}
	
	/**
	 *  跳转到登入
	 */
	private void redictToLogin(int requestCode){
		Intent intent = new Intent();
		intent.setClass(this, RstAndLoginActivity.class);
		startActivityForResult(intent, requestCode);
		ShowToast("请先登录。");
	}
	
	/**
	 *  头像选择
	 */
	@SuppressLint("InflateParams")
	public void showAlbumDialog(){
		albumDialog = new AlertDialog.Builder(mContext).create();
		albumDialog.setCanceledOnTouchOutside(true);
		View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_usericon, null);
		albumDialog.show();
		albumDialog.setContentView(v);
		albumDialog.getWindow().setGravity(Gravity.CENTER);
		TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
		TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
		albumPic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				albumDialog.dismiss();
				Date date1 = new Date(System.currentTimeMillis());
				dateTime = date1.getTime() + "";
				getAvataFromAlbum();
			}
		});
		cameraPic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				albumDialog.dismiss();
				Date date = new Date(System.currentTimeMillis());
				dateTime = date.getTime() + "";
				getAvataFromCamera();
			}
		});
	}
	
	/**
	 *  更新性别
	 */
	private void updateSex(int sex){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user!=null){
			if(sex == 0){
				user.setSex(Constant.SEX_FEMALE);
			}else{
				user.setSex(Constant.SEX_MALE);
			}
			user.update(mContext, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					ShowToast("更新信息成功。");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					ShowToast("更新信息失败。请检查网络~"+arg1);
				}
			});
		}else{
			redictToLogin(UPDATE_SEX);
		}
	}
	
	private void getAvataFromAlbum(){
		Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
		intent2.setType("image/*");
		startActivityForResult(intent2, 2);
	}
	
	private void getAvataFromCamera(){
		File f = new File(CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(f);
		Log.e("uri", uri + "");
		
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(camera, 1);
	}
	
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */  
	public static boolean isDownloadsDocument(Uri uri) {  
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */  
	public static boolean isMediaDocument(Uri uri) {  
	    return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is Google Photos. 
	 */  
	public static boolean isGooglePhotosUri(Uri uri) {  
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
	}  
	
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */  
	public static boolean isExternalStorageDocument(Uri uri) {  
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}
	
	public static String getDataColumn(Context context, Uri uri, String selection,  
	        String[] selectionArgs) {  

	    Cursor cursor = null;  
	    final String column = "_data";  
	    final String[] projection = {  
	            column  
	    };  

	    try {  
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,  
	                null);  
	        if (cursor != null && cursor.moveToFirst()) {  
	            final int index = cursor.getColumnIndexOrThrow(column);  
	            return cursor.getString(index);  
	        }  
	    } finally {  
	        if (cursor != null)  
	            cursor.close();  
	    }  
	    return null;  
	}  
	
	//以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...  
	@SuppressLint("NewApi")  
	public static String getPath(final Context context, final Uri uri) {  

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  

	    // DocumentProvider  
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  
	        // ExternalStorageProvider  
	        if (isExternalStorageDocument(uri)) {  
	            final String docId = DocumentsContract.getDocumentId(uri);  
	            final String[] split = docId.split(":");  
	            final String type = split[0];  

	            if ("primary".equalsIgnoreCase(type)) {  
	                return Environment.getExternalStorageDirectory() + "/" + split[1];  
	            }  

	        }  
	        // DownloadsProvider  
	        else if (isDownloadsDocument(uri)) {  
	            final String id = DocumentsContract.getDocumentId(uri);  
	            final Uri contentUri = ContentUris.withAppendedId(  
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  

	            return getDataColumn(context, contentUri, null, null);  
	        }  
	        // MediaProvider  
	        else if (isMediaDocument(uri)) {  
	            final String docId = DocumentsContract.getDocumentId(uri);  
	            final String[] split = docId.split(":");  
	            final String type = split[0];  

	            Uri contentUri = null;  
	            if ("image".equals(type)) {  
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("video".equals(type)) {  
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("audio".equals(type)) {  
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
	            }  

	            final String selection = "_id=?";  
	            final String[] selectionArgs = new String[] {  
	                    split[1]  
	            };  

	            return getDataColumn(context, contentUri, selection, selectionArgs);  
	        }  
	    }  
	    // MediaStore (and general)  
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {  
	        // Return the remote address  
	        if (isGooglePhotosUri(uri))  
	            return uri.getLastPathSegment();  

	        return getDataColumn(context, uri, null, null);  
	    }  
	    // File  
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {  
	        return uri.getPath();  
	    }  

	    return null;  
	}  

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {  
	        String url=getPath(mContext,uri);  
	        intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");  
	    }else{  
	        intent.setDataAndType(uri, "image/*");  
	    }
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 120);
		intent.putExtra("outputY", 120);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	
	public String saveToSdCard(Bitmap bitmap){
		String files =CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime+".png";
		File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
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
	 *  更新头像
	 */
	private void updateIcon(String avataPath){
		if(avataPath!=null){
			final BmobFile file = new BmobFile(new File(avataPath));
			file.upload(mContext, new UploadFileListener() {
				@Override
				public void onSuccess() {
					User currentUser = HttpProxy.getCurrentUser(mContext);
					currentUser.setAvatar(file.getFileUrl(mContext));
					currentUser.update(mContext, new UpdateListener() {
						
						@Override
						public void onSuccess() {
							ShowToast("更改头像成功。");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							ShowToast("更新头像失败。请检查网络~");
						}
					});
				}

				@Override
				public void onProgress(Integer arg0) {
					
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					ShowToast("上传头像失败。请检查网络~"+arg1);
				}
			});
		}
	}
	
}
