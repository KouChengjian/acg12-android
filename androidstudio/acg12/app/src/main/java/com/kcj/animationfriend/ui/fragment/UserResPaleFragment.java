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
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.FindUserPaletteListener;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.UserResPvwActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;
import com.liteutil.util.Log;

/**
 * @ClassName: UserResPaletteFragment
 * @Description: 用户资源--画板
 * @author: KCJ
 * @date: 
 */
public class UserResPaleFragment extends BaseFragment implements FindUserPaletteListener ,OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener{

	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_res)
	protected RecyclerView recyclerView;
	protected HomePaletteAdapter paletteAdapter = null;
	protected List<Palette> paletteList = new ArrayList<Palette>(); 
	protected User user;
	protected int pageNum = 0;
	protected Boolean refresh = true;
	protected Boolean isRefreshLoad = true;
	
	public static BaseFragment newInstance(User user) {
    	BaseFragment fragment = new UserResPaleFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("user", user);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_res_palette, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user = (User)getArguments().getSerializable("user");
		initViews();
		initEvent();
		initDatas();
	}
	
	public void initViews(){
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		paletteAdapter = new HomePaletteAdapter(getActivity(), paletteList);
		paletteAdapter.setOnClickItem(this);
		recyclerView.setAdapter(paletteAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	public void initEvent(){
		HttpProxy.setOnFindUserPaletteListener(this);
		myRefreshListView.setOnRefreshListener(this);
    	recyclerView.addOnScrollListener(new RecyclerViewOnScroll(paletteAdapter, this));
	}
	
	public void initDatas(){
		if(user == null) 
			return;
		refresh();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		user = null;
		paletteList.clear();
	}
	
	@Override
	public void onItemClick(View view, int position) {
		Palette palette = paletteList.get(position);
		Intent intent = new Intent(mContext , UserResPvwActivity.class);
		intent.putExtra("data", palette);
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		pageNum = 0;
		refresh = true;
		refresh();
	}

	@Override
	public void LodeMore() {
		if(!isRefreshLoad)
			return;
		refresh = false;
		footView.setVisibility(View.VISIBLE);
		refresh();
	}

	
	public void refresh(){
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					fetchDatas();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fetchDatas() {
		HttpProxy.fetchUserPalette(mContext,pageNum++, user);
	}
	
	@Override
	public void onFindUserPaletteSuccess(List<Palette> list) {
		if (list.size() != 0 && list.get(list.size() - 1) != null) {
			if(pageNum == 1){ // 第一次获取数据时
				paletteList.clear();
			}
			if (list.size() < Constant.NUMBERS_PER_PAGE) {
				isRefreshLoad = false;
			}
			// 隐藏other或者漫友
			for(int i = 0 ; i < list.size() ;i++){
				Palette p = list.get(i);
				Log.i("ObjectId",p.getObjectId()+"-----a5bf7ac301");
				if(p.getObjectId().equals("a5bf7ac301")){
					list.remove(p);
				}
			}
			paletteList.addAll(list);
			paletteAdapter.notifyDataSetChanged();
		}else{
			isRefreshLoad = false;
		}
		stopRefreshOrLoad();
	}

	@Override
	public void onFindUserPaletteFailure(String msg) {
		ShowToast(msg);
		stopRefreshOrLoad();
	}
	
	public void stopRefreshOrLoad(){
		if(refresh ){
			myRefreshListView.setRefreshing(false);
		}else {
			footView.setVisibility(View.GONE);
		}
	}
}
