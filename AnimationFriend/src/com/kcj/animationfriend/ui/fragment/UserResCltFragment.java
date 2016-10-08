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
import com.kcj.animationfriend.adapter.HomeAlbumAdapter;
import com.kcj.animationfriend.adapter.HomeAlbumAdapter.OnRecyclerViewItemClickListener;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.listener.FindCollectListener;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.AlbumPvwActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;

/**
 * @ClassName: UserResCltFragment
 * @Description: 个人信息--采集
 * @author: KCJ
 * @date: 
 */
public class UserResCltFragment extends BaseFragment implements FindCollectListener ,OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener {

	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_res)
	protected RecyclerView recyclerView;
	protected HomeAlbumAdapter collectAdapter = null;
	protected List<Album> collectList = new ArrayList<Album>();
	protected User user;
	protected int pageNum = 0;
	protected Boolean refresh = true;
	protected Boolean isRefreshLoad = true;
	
	public static BaseFragment newInstance(User user) {
    	BaseFragment fragment = new UserResCltFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("user", user);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_res_clt, container, false);
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
		collectAdapter = new HomeAlbumAdapter(getActivity(), collectList);
		collectAdapter.setOnClickItem(this);
		recyclerView.setAdapter(collectAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	public void initEvent(){
		HttpProxy.setOnFindCollectListener(this);
    	myRefreshListView.setOnRefreshListener(this);
    	recyclerView.addOnScrollListener(new RecyclerViewOnScroll(collectAdapter, this));
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
		collectList.clear();
	}
	
	@Override
	public void onItemClick(View view, int position) {
		Album album = collectList.get(position);
		Intent intent = new Intent(getActivity(), AlbumPvwActivity.class);
		UserProxy.commentAlbum = collectList;
		intent.putExtra("data", album);
		intent.putExtra("position", position);
		startActivityForResult(intent, Constant.STARE_COMMENT_FOR_RESULT);
	}
	
	@Override
	public void onRefresh() {
		pageNum = 0;
		refresh = true;
		refresh();
	}

	@Override
	public void LodeMore() {
		if(isRefreshLoad){
			refresh = false;
			footView.setVisibility(View.VISIBLE);
			refresh();
		}
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
	
	public void fetchDatas() {
		HttpProxy.fetchCollectAlbum(mContext,pageNum++, user);
	}
	
	@Override
	public void onFindCollectSuccess(List<Album> list) {
		if (list.size() != 0 && list.get(list.size() - 1) != null) {
			if(pageNum == 1){ 
				collectList.clear();
			}
			if (list.size() < Constant.NUMBERS_PER_PAGE) {
				isRefreshLoad = false;
				// ShowToast("已加载完所有采集数据~");
			}
			collectList.addAll(list);
		}else{
			isRefreshLoad  = false;
		}
		collectAdapter.notifyDataSetChanged();
		stopRefreshOrLoad();
	}

	@Override
	public void onFindCollectFailure(String msg) {
		pageNum--;
		ShowToast(msg);
		stopRefreshOrLoad();
	}

	public void stopRefreshOrLoad(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		} else {
			footView.setVisibility(View.GONE);
		}
	}
}
