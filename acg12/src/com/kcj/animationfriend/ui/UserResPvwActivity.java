package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.HomeAlbumAdapter;
import com.kcj.animationfriend.adapter.HomeAlbumAdapter.OnRecyclerViewItemClickListener;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Collect;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;

/**
 * @ClassName: UserResPreviewPaletteActivity
 * @Description: 用户资源 - 预览图集
 * @author: KCJ
 * @date: 2015-2-10
 */
public class UserResPvwActivity extends BaseActivity implements OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener {

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_res)
	protected RecyclerView recyclerView;
	protected HomeAlbumAdapter albumAdapter = null;
	protected List<Album> albumCacheList = new ArrayList<Album>();
	protected Palette palette = null;
	protected int pageNum = 0;
	protected boolean refresh = true;
	protected Boolean isRefreshLoad = true;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_paletteshow);
		setTitle(R.string.home_album);
		setSupportActionBar(toolbar);
		palette = (Palette)getIntent().getSerializableExtra("data");
		initView();
		initEvents();
	}
	
	protected void initView(){
		if(palette == null)
			return;
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		albumAdapter = new HomeAlbumAdapter(this, albumCacheList);
		albumAdapter.setOnClickItem(this);
		recyclerView.setAdapter(albumAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	public void initEvents(){
    	myRefreshListView.setOnRefreshListener(this);
    	recyclerView.addOnScrollListener(new RecyclerViewOnScroll(albumAdapter, this));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode == Activity.RESULT_OK){
    		switch (requestCode) {
			case Constant.STARE_COMMENT_FOR_RESULT:
				// setListViewPos(data.getExtras().getInt("position"));
    		}
    	}
    }
	
	@Override
	public void onRefresh() {
		refresh = true;
		pageNum = 0;
		albumCacheList.clear();
		refresh();
	}

	@Override
	public void LodeMore() {
		if(!isRefreshLoad)
			return;
		refresh = false;
		refresh();
		footView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(View view, int position) {
		Album album = albumCacheList.get(position);
		Intent intent = new Intent(this, AlbumPvwActivity.class);
		UserProxy.commentAlbum = albumCacheList;
		intent.putExtra("data", album);
		intent.putExtra("position", position);
		startActivityForResult(intent, Constant.STARE_COMMENT_FOR_RESULT);
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
	
    protected void fetchDatas(){
    	BmobQuery<Collect> query = new BmobQuery<Collect>();
		query.addWhereRelatedTo("collectBR", new BmobPointer(palette));
		query.include("user,album,palette,album.user,album.palette");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.findObjects(this, new FindListener<Collect>() {
			@Override
			public void onSuccess(List<Collect> data) {
				if(data.size()!=0 && data.get(data.size()-1)!=null){
					List<Album> listCollect = new ArrayList<Album>();
					if(data.size()<Constant.NUMBERS_PER_PAGE){
						isRefreshLoad = false;
						// ShowToast("已加载完所有画集~");
					}
					for(Collect collect:data){
						Album album = collect.getAlbum();
						album.setUser(collect.getAlbum().getUser());
						album.setPalette(collect.getAlbum().getPalette());
						album.setContent(collect.getContent());
						album.setMyLove(false);
						listCollect.add(album);
					}
					albumCacheList.addAll(listCollect);
					albumAdapter.notifyDataSetChanged();
					
				}else{
					pageNum--;
				}
				stopRefreshOrLoad();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				ShowToast("获取个人画集~"+arg1);
				pageNum--;
				stopRefreshOrLoad();
			}
		});
	}

	
	public void stopRefreshOrLoad(){
		if(refresh ){
			myRefreshListView.setRefreshing(false);
		}else {
			footView.setVisibility(View.GONE);
		}
	}

	
}
