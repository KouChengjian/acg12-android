package com.kcj.animationfriend.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.task.BRequest;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.AddFriendAdapter;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.view.RefreshLayout;
import com.kcj.animationfriend.view.RefreshLayout.OnLoadListener;


/** 
 * @ClassName: AddFriendActivity
 * @Description: 添加好友
 * @author: KCJ
 * @date: 2015-8-24
 */
public class AddFriendActivity extends BaseActivity implements OnClickListener ,OnItemClickListener ,OnLoadListener ,OnRefreshListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.et_find_name)
	protected EditText et_find_name;
	@InjectView(R.id.btn_search)
	protected Button btn_search;
	@InjectView(R.id.rl_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.list_search)
	protected ListView mListView;
	protected AddFriendAdapter adapter;
	protected List<BmobChatUser> users = new ArrayList<BmobChatUser>();
	protected String searchName ="";
	protected int curPage = 0;
	protected ProgressDialog progress ;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_addfriend);
		setTitle(R.string.addfriend);
		setSupportActionBar(toolbar);
		initViews();
		initEvent();
		
	}
	
	public void initViews(){
		adapter = new AddFriendAdapter(this, users);
		mListView.setAdapter(adapter);
		myRefreshListView.setEbLoading(false);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
	}
	
    public void initEvent(){
    	mListView.setOnItemClickListener(this);
    	btn_search.setOnClickListener(this);
    	myRefreshListView.setOnLoadListener(this);
    	myRefreshListView.setOnRefreshListener(this);
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
	public void onRefresh() {
    	users.clear();
		searchName = et_find_name.getText().toString();
		if(searchName!=null && !searchName.equals("")){
			searchList(false);
		}else{
			ShowToast("请输入用户名");
			myRefreshListView.setRefreshing(false);
		}
	}
    
    @Override
	public void onLoad() {
      userManager.querySearchTotalCount(searchName, new CountListener() {
			
			@Override
			public void onSuccess(int arg0) {
				if(arg0 >users.size()){
					curPage++;
					queryMoreSearchList(curPage);
				}else{
					ShowToast("数据加载完成");
					myRefreshListView.setLoading(false);
					myRefreshListView.setEbLoading(false);
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				myRefreshListView.setLoading(false);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search://搜索
			users.clear();
			searchName = et_find_name.getText().toString();
			if(searchName!=null && !searchName.equals("")){
				searchList(false);
			}else{
				ShowToast("请输入用户名");
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		BmobChatUser user = (BmobChatUser) adapter.getItem(position);
		searchUser(user.getUsername());
	}
	
	private void searchUser(String name) {
		progress = new ProgressDialog(AddFriendActivity.this);
		progress.setMessage("正在获取...");
		progress.setCanceledOnTouchOutside(true);
		progress.show();
		userManager.queryUser(name, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {
				progress.dismiss();
			}

			@Override
			public void onSuccess(List<User> arg0) {
				if (arg0 != null && arg0.size() > 0) {
					Intent intent =new Intent(AddFriendActivity.this,PersonInfoActivity.class);
					intent.putExtra("from", "albumType");
					intent.putExtra("data", arg0.get(0));
					startAnimActivity(intent);	
				} else {
//					ShowLog("onSuccess 查无此人");
				}
				progress.dismiss();
			}
		});
	}

	/** 
	 * @Title: searchList
	 * @Description: 查询
	 */
	private void searchList(final boolean isUpdate){
		if(!isUpdate){
			progress = new ProgressDialog(AddFriendActivity.this);
			progress.setMessage("正在搜索...");
			progress.setCanceledOnTouchOutside(true);
			progress.show();
		}
		userManager.queryUserByPage(isUpdate, 0, searchName, new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				BmobLog.i("查询错误:"+arg1);
				if(users!=null){
					users.clear();
				}
				ShowToast("用户不存在");
				//这样能保证每次查询都是从头开始
				curPage = 0;
				myRefreshListView.setRefreshing(false);
				myRefreshListView.setEbLoading(true);
			}

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				if (CollectionUtils.isNotNull(arg0)) {
					if(isUpdate){
						users.clear();
					}
					adapter.addAll(arg0);
					if(arg0.size()<BRequest.QUERY_LIMIT_COUNT){
//						mListView.setPullLoadEnable(false);
						ShowToast("用户搜索完成!");
					}else{
//						mListView.setPullLoadEnable(true);
					}
				}else{
					BmobLog.i("查询成功:无返回值");
					if(users!=null){
						users.clear();
					}
					ShowToast("用户不存在");
				}
				if(!isUpdate){
					progress.dismiss();
				}else{
//					refreshPull();
				}
				//这样能保证每次查询都是从头开始
				curPage = 0;
				myRefreshListView.setRefreshing(false);
				myRefreshListView.setEbLoading(true);
			}
		});
	}
	
	/** 
	 * @Title: queryMoreNearList
	 * @Description: 查询更多
	 */
	private void queryMoreSearchList(int page){
		userManager.queryUserByPage(true, page, searchName, new FindListener<BmobChatUser>() {

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				if (CollectionUtils.isNotNull(arg0)) {
					adapter.addAll(arg0);
				}
				myRefreshListView.setLoading(false);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				myRefreshListView.setLoading(false);
			}

		});
	}
}
