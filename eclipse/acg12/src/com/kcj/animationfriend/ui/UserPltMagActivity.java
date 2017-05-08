package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.PaletteManageAdapter;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.view.LoadingDialog;
import com.liteutil.util.Log;

/**
 * @ClassName: PaletteActivity
 * @Description: 画板管理
 * @author: KCJ
 * @date: 
 */
public class UserPltMagActivity extends BaseActivity implements OnClickListener ,OnItemClickListener,OnItemLongClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.lv_palette)
	protected ListView paletteListView;
	protected PaletteManageAdapter paletteAdapter;
	protected List<Palette> paletteList = new ArrayList<Palette>();
	protected EditText edt_palette_create;
	protected TextView tv_palette_ok;
	
	private int pageNum = 0;
	protected LoadingDialog dialog;
	Album curAlbum = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palette);
		setTitle(R.string.user_plt_mag);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curAlbum = (Album) getIntent().getSerializableExtra("data");
		initViews();
		initEvents();
	}
	
	public void initViews(){
		dialog = new LoadingDialog(this);
		RelativeLayout headView = (RelativeLayout) getLayoutInflater().inflate(R.layout.include_palette_create, null);
		edt_palette_create = (EditText)headView.findViewById(R.id.edt_palette_create);
		tv_palette_ok = (TextView)headView.findViewById(R.id.tv_palette_ok);
		paletteListView.addHeaderView(headView);
		paletteAdapter = new PaletteManageAdapter(this, paletteList);
		paletteListView.setAdapter(paletteAdapter);
		paletteListView.setOnItemClickListener(this);
		paletteListView.setOnItemLongClickListener(this);
	}
	
    public void initEvents(){
    	tv_palette_ok.setOnClickListener(this);
	}
    
    @Override
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	public void refresh(){
		try {
			this.runOnUiThread(new Runnable() {
				public void run() {
					fetchDatas();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fetchDatas() {
		User user = HttpProxy.getCurrentUser(mContext);
		BmobQuery<Palette> query = new BmobQuery<Palette>();
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.order("-createdAt");
		query.include("user");
		query.addWhereEqualTo("user", user);
		query.findObjects(mContext, new FindListener<Palette>() {

			@Override
			public void onSuccess(List<Palette> list) {
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					paletteAdapter.addAll(list);
					paletteAdapter.notifyDataSetChanged();
					//ShowToast("加载成功~");
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.e(TAG, arg1);
				ShowToast("加载失败~");
			}
			
		});
	}
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_palette_ok:
			if(edt_palette_create.getText().toString().isEmpty()){
				ShowToast("请输入画板名称");
				return;
			}
			dialog.show();
			savePalette(edt_palette_create.getText().toString());
			break;
		}
	}

	public void savePalette(String name){
		User user = HttpProxy.getCurrentUser(mContext);
		final Palette palette = new Palette();
		palette.setUser(user);
		palette.setName(name);
		palette.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				dialog.dismiss();
				ShowToast("保存成功~~");
				if(curAlbum == null){
					Intent intent = new Intent();
					intent.putExtra("data", palette);
					setResult(RESULT_OK, intent);
					finish();
				}else{
					paletteAdapter.add(palette);
					paletteAdapter.notifyDataSetChanged();
				}
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {	
				dialog.dismiss();
				ShowToast("保存失败~~"+arg1);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		final Palette palette = (Palette) paletteAdapter.getItem(position-1);
		if(curAlbum == null){
			UserProxy.currentPalette = palette;
			Intent intent = new Intent();
			intent.putExtra("data", palette);
			setResult(RESULT_OK, intent);
			finish();
		}else{
			User user = HttpProxy.getCurrentUser(mContext);
			final BmobRelation relation = new BmobRelation();
			relation.add(curAlbum);
//			user.setCollectBR(relation);
			user.update(mContext, new UpdateListener() {
				
				@Override
				public void onSuccess() {
//					palette.setAlbumBR(relation);
					palette.update(mContext, new UpdateListener() {

						@Override
						public void onSuccess() {
							ShowToast("采集成功");
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							ShowToast("采集失败~"+arg1);
						}
						
					});
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					ShowToast("采集失败1~"+arg1);
				}
			});
		}
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		return false;
	}
}
