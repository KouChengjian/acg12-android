package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import cn.bmob.im.task.BRequest;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.NearPeopleAdapter;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RefreshLayout;
import com.kcj.animationfriend.view.RefreshLayout.OnLoadListener;


/**
 * @ClassName: NearPeopleActivity
 * @Description: 附近的人列表
 * @author: 
 * @date: 
 */
public class NearPeopleActivity extends BaseActivity implements OnItemClickListener ,OnRefreshListener ,OnLoadListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.rl_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.list_near)
	protected ListView mListView;
	protected NearPeopleAdapter adapter;
	protected String from = "";
	protected Boolean refresh = true;
	protected List<User> nears = new ArrayList<User>();
	
	int curPage = 0;
	ProgressDialog progress ;
	private double QUERY_KILOMETERS = 10;//默认查询10公里范围内的人
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_near);
		setTitle(R.string.nearpeople);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initViews();
		initEvent();
		initDatas(false);
	}
	
	public void initViews() {
		adapter = new NearPeopleAdapter(this, nears);
		mListView.setAdapter(adapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	public void initEvent() {
		mListView.setOnItemClickListener(this);
		myRefreshListView.setOnRefreshListener(this);
    	myRefreshListView.setOnLoadListener(this);
	}

	public void initDatas(final boolean isUpdate) {
		if(!isUpdate){
			progress = new ProgressDialog(NearPeopleActivity.this);
			progress.setMessage("正在查询附近的人...");
			progress.setCanceledOnTouchOutside(true);
			progress.show();
		}
		
		if(!mApplication.getLatitude().equals("")&&!mApplication.getLongtitude().equals("")){
			double latitude = Double.parseDouble(mApplication.getLatitude());
			double longtitude = Double.parseDouble(mApplication.getLongtitude());
			//封装的查询方法，当进入此页面时 isUpdate为false，当下拉刷新的时候设置为true就行。
			//此方法默认每页查询10条数据,若想查询多于10条，可在查询之前设置BRequest.QUERY_LIMIT_COUNT，如：BRequest.QUERY_LIMIT_COUNT=20
			// 此方法是新增的查询指定10公里内的性别为女性的用户列表，默认包含好友列表
			//如果你不想查询性别为女的用户，可以将equalProperty设为null或者equalObj设为null即可
			userManager.queryKiloMetersListByPage(isUpdate,0,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",null,new FindListener<User>() {
			//此方法默认查询所有带地理位置信息的且性别为女的用户列表，如果你不想包含好友列表的话，将查询条件中的isShowFriends设置为false就行
//			userManager.queryNearByListByPage(isUpdate,0,"location", longtitude, latitude, true,"sex",false,new FindListener<User>() {

				@Override
				public void onSuccess(List<User> arg0) {
					if (CollectionUtils.isNotNull(arg0)) {
						if(isUpdate){
							nears.clear();
						}
						adapter.addAll(arg0);
						if(arg0.size()<BRequest.QUERY_LIMIT_COUNT){
							ShowToast("附近的人搜索完成!");
						}else{
						}
					}else{
						ShowToast("暂无附近的人!");
					}
					
					if(!isUpdate){
						progress.dismiss();
					}else{
						stopRefreshLoadMore();
					}
					stopRefreshLoadMore();
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					ShowToast("暂无附近的人!");
					if(!isUpdate){
						progress.dismiss();
					}else{
						stopRefreshLoadMore();
					}
					stopRefreshLoadMore();
				}

			});
		}else{
			ShowToast("暂无附近的人!");
			progress.dismiss();
			stopRefreshLoadMore();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onRefresh() {
		refresh = true;
		initDatas(true);
	}

	@Override
	public void onLoad() {
		refresh = false;
		double latitude = Double.parseDouble(mApplication.getLatitude());
		double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//这是查询10公里范围内的性别为女用户总数
		userManager.queryKiloMetersTotalCount(User.class, "location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",null,new CountListener() {
	    //这是查询附近的人且性别为女性的用户总数
//		userManager.queryNearTotalCount(User.class, "location", longtitude, latitude, true,"sex",false,new CountListener() {
			
			@Override
			public void onSuccess(int arg0) {
				if(arg0 >nears.size()){
					curPage++;
					queryMoreNearList(curPage);
				}else{
					ShowToast("数据加载完成");
					stopRefreshLoadMore();
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				stopRefreshLoadMore();
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		User user = (User) adapter.getItem(position);
		Intent intent =new Intent(this,PersonInfoActivity.class);
		intent.putExtra("from", "albumType");
		intent.putExtra("data", user);
		startAnimActivity(intent);	
	}

	/** 
	  * @Title: queryMoreNearList
	  * @Description: 查询更多
	  */
	private void queryMoreNearList(int page){
		double latitude = Double.parseDouble(mApplication.getLatitude());
		double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//查询10公里范围内的性别为女的用户列表
		userManager.queryKiloMetersListByPage(true,page,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",false,new FindListener<User>() {
		//查询全部地理位置信息且性别为女性的用户列表
//		userManager.queryNearByListByPage(true,page, "location", longtitude, latitude, true,"sex",false,new FindListener<User>() {

			@Override
			public void onSuccess(List<User> arg0) {
				if (CollectionUtils.isNotNull(arg0)) {
					adapter.addAll(arg0);
				}
				stopRefreshLoadMore();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				stopRefreshLoadMore();
			}

		});
	}

	public void stopRefreshLoadMore(){
		if(refresh ){
			myRefreshListView.setRefreshing(false);
		}else {
			myRefreshListView.setLoading(false);
		}
	}
}
