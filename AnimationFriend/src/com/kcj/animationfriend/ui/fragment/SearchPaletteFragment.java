package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

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
import com.kcj.animationfriend.adapter.HomePaletteAdapter;
import com.kcj.animationfriend.adapter.HomePaletteAdapter.OnRecyclerViewItemClickListener;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.HttpRequestListener;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.HomeActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;

public class SearchPaletteFragment extends BaseFragment implements OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener{

	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_home)
	protected RecyclerView recyclerView;
	protected HomePaletteAdapter paletteAdapter;
	protected List<Palette> paletteList = new ArrayList<Palette>(); 
	protected boolean refresh = true;
	protected Boolean isLoad = true;
	protected int pageNum = 1;
	protected String title;
	
	public static BaseFragment newInstance(String title ) {
    	BaseFragment fragment = new SearchPaletteFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("title", title);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_palette, container,false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		title = getArguments().getString("title");
		initViews();
		initEvent();
		initDatas();
	}
	
	public void initViews() {
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		paletteAdapter = new HomePaletteAdapter(getActivity(), paletteList);
		recyclerView.setAdapter(paletteAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	public void initEvent() {
		paletteAdapter.setOnClickItem(this);
		recyclerView.addOnScrollListener(new RecyclerViewOnScroll(paletteAdapter, this));
    	myRefreshListView.setOnRefreshListener(this);
	}
	
	public void initDatas() {
		refresh(title,pageNum);	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onRefresh() {
		pageNum = 1;
		refresh = true;
		isLoad = true;
		refresh(title,pageNum);	
	}

	@Override
	public void LodeMore() {
		if(!isLoad)
			return;
		pageNum ++;
		refresh = false;
		refresh(title,pageNum);	
		footView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(View view, int position) {
		Palette palette = paletteList.get(position);
		Intent intent = new Intent(mContext , HomeActivity.class);
		intent.putExtra("data", palette);
		intent.putExtra("AreaType", 1);
		startActivity(intent);		
	}
	
	public void refresh(String key,int page) {
		HttpProxy.getSearchPalette(key, page, new HttpRequestListener<Palette>() {
			
			@Override
			public void onSuccess(List<Palette> list) {
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					if(pageNum == 1){ // 第一次获取数据时
						paletteList.clear();
					}
					if (list.size() < Constant.NUMBERS_PER_PAGE) {
						isLoad  = false;
					}
					paletteList.addAll(list);
					paletteAdapter.notifyDataSetChanged();
				}else{
					isLoad  = false;
				}
				stopRefreshLoadMore();
			}
			
			@Override
			public void onFailure(String msg) {
				pageNum--;
				ShowToast(msg);
				stopRefreshLoadMore();
			}
		});
	}
	
	public void stopRefreshLoadMore(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		}else{
			footView.setVisibility(View.GONE);
		}
	}

}
