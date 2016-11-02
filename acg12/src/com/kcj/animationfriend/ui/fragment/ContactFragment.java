package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.UserFriendAdapter;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.AddFriendActivity;
import com.kcj.animationfriend.ui.BlackListActivity;
import com.kcj.animationfriend.ui.NearPeopleActivity;
import com.kcj.animationfriend.ui.NewFriendActivity;
import com.kcj.animationfriend.ui.PersonInfoActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.CharacterParser;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.util.PinyinComparator;
import com.kcj.animationfriend.view.AlertDialogView;
import com.kcj.animationfriend.view.ClearEditText;
import com.kcj.animationfriend.view.MyLetterView;
import com.kcj.animationfriend.view.MyLetterView.OnTouchingLetterChangedListener;

/**
 * @ClassName: ContactFragment
 * @Description: 联系人
 * @author: KCJ
 * @date: 2015-8-29 下午1:02:05
 */
public class ContactFragment extends BaseFragment implements OnItemClickListener, OnItemLongClickListener, OnTouchListener,
		TextWatcher ,OnClickListener{

	@InjectView(R.id.rl_contact_empty)
	protected RelativeLayout rl_contact_empty;
	@InjectView(R.id.layout_list)
	protected RelativeLayout layout_list;
	@InjectView(R.id.dialog)
	protected TextView dialog;
	@InjectView(R.id.list_friends)
	protected ListView list_friends;
	@InjectView(R.id.right_letter)
	protected MyLetterView right_letter;
	@InjectView(R.id.et_msg_search)
	protected ClearEditText mClearEditText;
	Button btn_new_friend ;
	Button btn_near_person ;
	Button btn_add_friend;
	Button btn_black_list;
	ImageView iv_new_friend;

	private UserFriendAdapter userAdapter;// 好友
	private List<User> friends = new ArrayList<User>();
	private InputMethodManager inputMethodManager;
	private CharacterParser characterParser; // 汉字转换成拼音的类
	private PinyinComparator pinyinComparator; // 根据拼音来排列ListView里面的数据类
	protected User user;
	private boolean hidden;

	public static BaseFragment newInstance() {
    	BaseFragment fragment = new ContactFragment();
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contact, container,false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		initViews();
		initEvent();
		initDatas();
	}

	public void initViews() {
		user = HttpProxy.getCurrentUser(mContext);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		// list
		RelativeLayout headView = (RelativeLayout) mInflater.inflate(R.layout.include_new_friend, null);
		btn_new_friend = (Button) headView.findViewById(R.id.btn_new_friend);
		btn_near_person = (Button) headView.findViewById(R.id.btn_near_person);
		btn_add_friend = (Button) headView.findViewById(R.id.btn_add_friend);
		btn_black_list = (Button) headView.findViewById(R.id.btn_black_list);
		iv_new_friend = (ImageView) headView.findViewById(R.id.iv_new_friend);
		
		list_friends.addHeaderView(headView);
		userAdapter = new UserFriendAdapter(getActivity(), friends);
		list_friends.setAdapter(userAdapter);
		// MyLetterView
		right_letter.setTextView(dialog);
	}

	public void initEvent() {
		list_friends.setOnItemClickListener(this);
		list_friends.setOnItemLongClickListener(this);
		list_friends.setOnTouchListener(this);
		mClearEditText.addTextChangedListener(this);
		right_letter.setOnTouchingLetterChangedListener(new LetterListViewListener());
		
		btn_new_friend.setOnClickListener(this);
		btn_near_person.setOnClickListener(this);
		btn_add_friend.setOnClickListener(this);
		btn_black_list.setOnClickListener(this);
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
//		if(user == null){
//			layout_list.setVisibility(View.GONE);
//			rl_contact_empty.setVisibility(View.VISIBLE);
//		}else{
//			layout_list.setVisibility(View.VISIBLE);
//			rl_contact_empty.setVisibility(View.GONE);
//		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
//			user = userProxy.getCurrentUser();
//			if(user == null){
//				if(rl_contact_empty.getVisibility() == View.GONE){
//					layout_list.setVisibility(View.GONE);
//					rl_contact_empty.setVisibility(View.VISIBLE);
//				}
//			}else{
//				if(layout_list.getVisibility() == View.GONE){
//					layout_list.setVisibility(View.VISIBLE);
//					rl_contact_empty.setVisibility(View.GONE);
//				}
//			}
			refresh();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_new_friend:
			Intent intent = new Intent(getActivity(), NewFriendActivity.class);
			intent.putExtra("from", "contact");
			startAnimActivity(intent);
			break;
        case R.id.btn_near_person:
        	startAnimActivity(NearPeopleActivity.class);
			break;
        case R.id.btn_add_friend:
	        startAnimActivity(AddFriendActivity.class);
	        break;
        case R.id.btn_black_list:
        	startAnimActivity(BlackListActivity.class);
        	break;
		default:
			break;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		User user = (User) userAdapter.getItem(position - 1);
		showDeleteDialog(user);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
		User user = (User) userAdapter.getItem(position - 1);
		// 先进入好友的详细资料页面
		Intent intent =new Intent(getActivity(),PersonInfoActivity.class);
		intent.putExtra("from", "contactType");
		intent.putExtra("data", user);
		startAnimActivity(intent);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// 隐藏软键盘
		if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getActivity().getCurrentFocus() != null)
				inputMethodManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}
		return false;
	}

	@Override
	public void afterTextChanged(Editable arg0) {}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
		filterData(s.toString());
	}

	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryMyfriends();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取好友列表 queryMyfriends
	 * 
	 * @return void
	 * @throws
	 */
	private void queryMyfriends() {
		// 是否有新的好友请求
		if (BmobDB.create(getActivity()).hasNewInvite()) {
			iv_new_friend.setVisibility(View.VISIBLE);
		} else {
			iv_new_friend.setVisibility(View.GONE);
		}
		// 在这里再做一次本地的好友数据库的检查，是为了本地好友数据库中已经添加了对方，但是界面却没有显示出来的问题
		// 重新设置下内存中保存的好友列表
		MyApplication.getInstance().setContactList(
				CollectionUtils.list2map(BmobDB.create(getActivity())
						.getContactList()));

		Map<String, BmobChatUser> users = MyApplication.getInstance()
				.getContactList();
		// 组装新的User
		filledData(CollectionUtils.map2list(users));
		if (userAdapter == null) {
			userAdapter = new UserFriendAdapter(getActivity(), friends);
			list_friends.setAdapter(userAdapter);
		} else {
			userAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private void filledData(List<BmobChatUser> datas) {
		friends.clear();
		int total = datas.size();
		for (int i = 0; i < total; i++) {
			BmobChatUser user = datas.get(i);
			User sortModel = new User();
			sortModel.setAvatar(user.getAvatar());
			sortModel.setNick(user.getNick());
			sortModel.setUsername(user.getUsername());
			sortModel.setObjectId(user.getObjectId());
			sortModel.setContacts(user.getContacts());
			// 汉字转换成拼音
			// String username = sortModel.getUsername();
			String usernick = sortModel.getNick();
			if (usernick == null) {
				sortModel.setNick(sortModel.getUsername());
			}
			// 若没有username
			if (usernick != null) {
				String pinyin = characterParser.getSelling(sortModel.getNick());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					sortModel.setSortLetters(sortString.toUpperCase());
				} else {
					sortModel.setSortLetters("#");
				}
			} else {
				sortModel.setSortLetters("#");
			}
			friends.add(sortModel);
		}
		// 根据a-z进行排序
		Collections.sort(friends, pinyinComparator);
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<User> filterDateList = new ArrayList<User>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = friends;
		} else {
			filterDateList.clear();
			for (User sortModel : friends) {
				String name = sortModel.getNick();
				if (name != null) {
					if (name.indexOf(filterStr.toString()) != -1
							|| characterParser.getSelling(name).startsWith(
									filterStr.toString())) {
						filterDateList.add(sortModel);
					}
				}
			}
		}
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		userAdapter.updateListView(filterDateList);
	}

	public void showDeleteDialog(final User user) {
		final AlertDialogView alertDialog = new AlertDialogView(getActivity(),"");
		alertDialog.setContent1("删除好友", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteContact(user);
				alertDialog.cancel();
			}
		});
		alertDialog.setContent2("拉入黑名单", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addUserBrackList(user.getUsername());
				alertDialog.cancel();
			}
		});
	}

	/**
	 * 删除联系人 deleteContact
	 * 
	 * @return void
	 * @throws
	 */
	private void deleteContact(final User user) {
		final ProgressDialog progress = new ProgressDialog(getActivity());
		progress.setMessage("正在删除...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		userManager.deleteContact(user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowToast("删除成功");
				// 删除内存
				MyApplication.getInstance().getContactList()
						.remove(user.getUsername());
				// 更新界面
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progress.dismiss();
						userAdapter.remove(user);
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("删除失败：" + arg1);
				progress.dismiss();
			}
		});
	}
	
	/**
	 * 拉入黑名单
	 * 
	 * @return void
	 * @throws
	 */
	private void addUserBrackList(final String username) {
		userManager.addBlack(username, new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowToast("黑名单添加成功!");
				MyApplication.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(mContext).getContactList()));
			    refresh();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("黑名单添加失败:" + arg1);
			}
		});
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(String s) {
			// 该字母首次出现的位置
			int position = userAdapter.getPositionForSection(s.charAt(0));
			if (position != -1) {
				list_friends.setSelection(position);
			}
		}
	}

}
