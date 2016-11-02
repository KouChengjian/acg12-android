package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.HomeAlbumAdapter;
import com.kcj.animationfriend.adapter.HomeAlbumAdapter.OnRecyclerViewItemClickListener;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.listener.HttpRequestListener;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.AlbumPvwActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;

/**
 * @ClassName: AlbumFragment
 * @Description: 图片
 * @author: KCJ
 * @date: 2015-11-20
 */
public class TabAlbumFragment extends BaseFragment implements OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener {
	
	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_home)
	protected RecyclerView recyclerView;
	protected HomeAlbumAdapter albumAdapter = null;
	protected List<Album> listAlbum = new ArrayList<Album>();
	protected int pageNum = 1;
	protected Boolean refresh = true;
	protected Boolean isLoad = true;
	protected Palette palette;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_homealbum, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		palette = (Palette)getArguments().getSerializable("palette"); 
		initViews();
		initEvent();
		initDatas();
	}
	
	public void initViews(){
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		albumAdapter = new HomeAlbumAdapter(getActivity(), listAlbum);
		albumAdapter.setOnClickItem(this);
		recyclerView.setAdapter(albumAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
    public void initEvent(){
    	recyclerView.addOnScrollListener(new RecyclerViewOnScroll(albumAdapter, this));
    	myRefreshListView.setOnRefreshListener(this);
	}
    
    public void initDatas(){
    	refresh("");
    }
    
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode == Activity.RESULT_OK){
    		switch (requestCode) {
			case Constant.STARE_COMMENT_FOR_RESULT:
				break;
    		}
    	}
    }
    
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onRefresh() {
		pageNum = 1;
		refresh = true;
		refresh("");	
	}

	@Override
	public void LodeMore() {
		if(!isLoad)
			return;
		pageNum++;
		refresh= false;
    	refresh(listAlbum.get(listAlbum.size()-1).getPinId());
    	footView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(View view, int position) {
		Album album =listAlbum.get(position);
		Intent intent = new Intent(getActivity(), AlbumPvwActivity.class);
		UserProxy.commentAlbum = listAlbum;
		intent.putExtra("data", album);
		intent.putExtra("position", position);
		startActivityForResult(intent, Constant.STARE_COMMENT_FOR_RESULT);
	}
	
	public void refresh(String max) {
		if(palette == null){
			HttpProxy.getHomeMoreAlbum(max ,new HttpRequestListener<Album>() {
				
				@Override
				public void onSuccess(List<Album> list) {
					onFindAlbumSuccess(list);
				}
				
				@Override
				public void onFailure(String msg) {
					pageNum--;
					ShowToast(msg);
					stopRefreshLoadMore();
				}
			});
		}else{
			HttpProxy.getHomeMorePaletteAlbum(max , palette.getBoardId(), new HttpRequestListener<Album>() {
				
				@Override
				public void onSuccess(List<Album> list) {
					onFindAlbumSuccess(list);
				}
				
				@Override
				public void onFailure(String msg) {
					pageNum--;
					ShowToast(msg);
					stopRefreshLoadMore();
				}
			});
		}
	}
	
	public void onFindAlbumSuccess(List<Album> list) {
		if (list.size() != 0 && list.get(list.size() - 1) != null) {
			if(pageNum == 1){ // 第一次获取数据时
				listAlbum.clear();
			}
			if (list.size() < Constant.NUMBERS_PER_PAGE) {
				isLoad  = false;
				// ShowToast("已加载完所有数据~");
			}
			for(Album album:list){
				album.setMyLove(false);
			}
			listAlbum.addAll(list);
			albumAdapter.notifyDataSetChanged();
		}else{
			isLoad  = false;
			// ShowToast("已加载完所有数据~");
		}
		stopRefreshLoadMore();
	}

	public void stopRefreshLoadMore(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		}else{
			footView.setVisibility(View.GONE);
		}
	}
}
