package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Collect;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.view.LoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: AlbumPvwCltActivity
 * @Description: 预览采集图片
 * @author: KCJ
 * @date:
 */
public class AlbumPvwCltActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.iv_collect_icon)
	protected ImageView albumPic;
	@InjectView(R.id.edt_collect_content)
	protected EditText content;
	@InjectView(R.id.rl_collect_palette)
	protected RelativeLayout choosePalette;
	@InjectView(R.id.tv_collect_palette)
	protected TextView tvChoosePalette;
	@InjectView(R.id.tv_collect_save)
	protected TextView tvCollectSave;

	protected Album curAlbum = null;
	private Palette palette = null;
	protected LoadingDialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect);
		setTitle(R.string.home_album_clt);
		setSupportActionBar(toolbar);
		curAlbum = (Album) getIntent().getSerializableExtra("data");
		initViews();
		initEvents();
		initDatas();
	}

	public void initViews(){
		dialog = new LoadingDialog(this);
	}
	
	public void initEvents(){
		tvCollectSave.setOnClickListener(this);
		choosePalette.setOnClickListener(this);
	}
	
	public void initDatas(){
		if(curAlbum != null){
			if(!curAlbum.getContent().isEmpty() && curAlbum.getContent() != null){
				content.setText(curAlbum.getContent());
			}
			ArrayList<BmobFile> proFileList = curAlbum.getProFileList();
			if(proFileList != null){
				BmobFile fileList = proFileList.get(0);
				if(fileList != null){
					ImageLoader.getInstance().displayImage(fileList.getFileUrl(mContext)==null?"":fileList.getFileUrl(mContext), albumPic, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
					});
				}
			}else {
				// 抓取图片显示
				ArrayList<String> urlList = curAlbum.getUrlList();
				if(urlList != null){
					String fileList = urlList.get(0);
					if(fileList != null){
						ImageLoader.getInstance().displayImage(fileList , albumPic, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
							@Override
							public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
						});
					}
				}
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case Constant.REQUEST_CODE_PALETTE:
				palette=(Palette)data.getSerializableExtra("data");
				tvChoosePalette.setText(palette.getName());
				break;
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_collect_save:
			if(palette ==null){
				ShowToast("请选择画板");
				return;
			}
			if(curAlbum != null){
				ArrayList<BmobFile> proFileList = curAlbum.getProFileList();
				if(proFileList != null && !proFileList.isEmpty()){
					dialog.show();
					saveCollectAlbum(curAlbum);
				}else{
					if(curAlbum.getUrlList() != null){
						dialog.show();
						BmobUserManager.getInstance(mContext).queryUser("13652390539", new FindListener<User>() {

							@Override
							public void onError(int arg0, String arg1) {
								Log.e("onError",arg1+"=====");
							}

							@Override
							public void onSuccess(List<User> arg0) {
								if (arg0 != null && arg0.size() > 0) {
									User user = arg0.get(0);
									saveAlbum(user);
								} 
							}
						});
					}
				}
			}
		    break;
		case R.id.rl_collect_palette:
			startActivityForResult(new Intent(this,UserPltMagActivity.class), Constant.REQUEST_CODE_PALETTE);
			break;
		}
	}
	
	public void saveAlbum(User user){
		final Palette adminpalette = new Palette();
		adminpalette.setObjectId("a5bf7ac301");
		adminpalette.setName("漫友");
		adminpalette.setUser(user);
		final Collect collect = new Collect();
		collect.setUser(user);
		collect.setPalette(adminpalette);
		collect.setContent(content.getText().toString());
		collect.setType(0);
		final Album album = new Album();
		album.setUser(user);
		album.setPalette(adminpalette);
		album.setContent(curAlbum.getContent());
		album.setResType("jpg");
		album.setResWidth(curAlbum.getResWidth());
		album.setResHight(curAlbum.getResHight());
		album.setLove(curAlbum.getLove());
		album.setFavorites(curAlbum.getFavorites());
		album.setUrlList(curAlbum.getUrlList());
		album.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				collect.setAlbum(album);
				collect.save(mContext, new SaveListener() {

					@Override
					public void onSuccess() {
						BmobRelation relation = new BmobRelation();
						relation.add(collect);
						adminpalette.setCollectBR(relation);
						adminpalette.update(mContext, new UpdateListener() {
							
							@Override
							public void onSuccess() {
								saveCollectAlbum(album);
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
						Log.e("onFailure", arg1);
						ShowToast("保存失败~"+arg1);
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("onFailure", arg1);
				ShowToast("保存失败~"+arg1);
			}
		});
		
	}
	
	public void saveCollectAlbum(final Album album){
		final User user = HttpProxy.getCurrentUser(mContext);
		if(user != null){
			final Collect collect = new Collect();
			collect.setUser(user);
			collect.setAlbum(album);
			collect.setPalette(palette);
			collect.setContent(content.getText().toString());
			collect.setType(1);
			collect.save(mContext ,new SaveListener() {
				
				@Override
				public void onSuccess() {
					BmobRelation relation = new BmobRelation();
					relation.add(collect);
					palette.setCollectBR(relation);
					palette.update(mContext, new UpdateListener() {

						@Override
						public void onSuccess() {
							album.increment("favorites",1);
							album.update(mContext);
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
					ShowToast("采集失败~"+arg1);
				}
			});
		}
		
	}
}
